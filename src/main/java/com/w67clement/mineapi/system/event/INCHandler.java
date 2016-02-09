package com.w67clement.mineapi.system.event;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.wrappers.MC_PacketWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.NmsPacket;
import com.w67clement.mineapi.utils.NmsPacketReader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class INCHandler implements IHandler
{

    private static MineAPI mineapi;

    private static Class<?> entityPlayer = ReflectionAPI.getNmsClass("EntityPlayer");
    private static Class<?> playerConnection = ReflectionAPI.getNmsClass("PlayerConnection");

    private static Field connection = ReflectionAPI.getField(entityPlayer, "playerConnection", true);
    private static Field network = ReflectionAPI.getField(playerConnection, "networkManager", true);
    private static NmsPacketReader reader;

    private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10, new ThreadFactoryBuilder().setNameFormat("MineAPI INCHandler #%d").build());

    public INCHandler(MineAPI mineapi)
    {
        INCHandler.mineapi = mineapi;
        reader = new NmsPacketReader();
    }

    private static Channel getChannel(Object networkManager)
    {
        return (Channel) ReflectionAPI.getValue(networkManager, ReflectionAPI.getFirstFieldOfType(networkManager.getClass(), Channel.class));
    }

    @Override
    public void addChannel(final Player player)
    {
        final Object handle = ReflectionAPI.NmsClass.getEntityPlayerByPlayer(player);
        try
        {
            final Object connection = INCHandler.connection.get(handle);
            final Channel channel = getChannel(network.get(connection));
            this.threadPool.execute(() -> {
                try
                {
                    ChannelHandler handler = new ChannelHandler(player);
                    channel.pipeline().addBefore("packet_handler", "MineAPI", handler);
                    MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + handler.getClass().getName() + " added to " + player.getName() + ".", true);
                }
                catch (Exception ignored)
                {
                }
            });
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void removeChannel(Player player)
    {
        final Object handle = ReflectionAPI.NmsClass.getEntityPlayerByPlayer(player);
        try
        {
            final Object connection = INCHandler.connection.get(handle);
            final Channel channel = getChannel(network.get(connection));
            this.threadPool.execute(() -> {
                try
                {
                    try
                    {
                        io.netty.channel.ChannelHandler handler = channel.pipeline().get("MineAPI");
                        channel.pipeline().remove("MineAPI");
                        MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + handler.getClass().getName() + " removed to " + player.getName() + ".", true);
                    }
                    catch (Exception ignored)
                    {
                    }
                }
                catch (Exception ignored)
                {
                }
            });
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addServerConnectionChannel()
    {
        Server server = Bukkit.getServer();
        Object mc_Server = ReflectionAPI.invokeMethod(server, ReflectionAPI.getMethod(server, "getServer"));
        Class<?> serverConnectionClass = ReflectionAPI.getNmsClass("ServerConnection");
        Object serverConnection = ReflectionAPI.getValue(mc_Server, ReflectionAPI.getFirstFieldOfType(ReflectionAPI.getNmsClass("MinecraftServer"), serverConnectionClass));
        Field field = ReflectionAPI.getLastFieldOfType(serverConnectionClass, List.class);
        List<?> currentList = (List<?>) ReflectionAPI.getValue(serverConnection, field);
        Field field1 = null;
        if (currentList != null)
        {
            field1 = ReflectionAPI.getField(currentList.getClass().getSuperclass(), "list", true);
        }
        Object obj = ReflectionAPI.getValue(currentList, field1);
        if (obj != null && obj.getClass().equals(PacketPingListenerList.class))
            return;
        List newList = Collections.synchronizedList(new PacketPingListenerList());
        if (currentList != null)
        {
            newList.addAll(currentList.stream().collect(Collectors.toList()));
        }
        ReflectionAPI.setValue(serverConnection, field, newList);
    }

    private void removeServerConnectionChannel()
    {
        Server server = Bukkit.getServer();
        Object mc_Server = ReflectionAPI.invokeMethod(server, ReflectionAPI.getMethod(server, "getServer"));
        Class<?> serverConnectionClass = ReflectionAPI.getNmsClass("ServerConnection");
        Object serverConnection = ReflectionAPI.getValue(mc_Server, ReflectionAPI.getFirstFieldOfType(ReflectionAPI.getNmsClass("MinecraftServer"), serverConnectionClass));
        Field field = ReflectionAPI.getLastFieldOfType(serverConnectionClass, List.class);
        List<?> currentList = (List<?>) ReflectionAPI.getValue(serverConnection, field);
        Field field1 = null;
        if (currentList != null)
        {
            field1 = ReflectionAPI.getField(currentList.getClass().getSuperclass(), "list", true);
        }
        Object obj = ReflectionAPI.getValue(currentList, field1);
        if (obj != null && obj.getClass().equals(PacketPingListenerList.class))
        {
            List newList = Collections.synchronizedList(new ArrayList<>());
            if (currentList != null)
            {
                newList.addAll(currentList.stream().collect(Collectors.toList()));
            }
            ReflectionAPI.setValue(serverConnection, field, newList);
        }
    }

    @Override
    public void disable()
    {
        this.removeServerConnectionChannel();
    }

    private class PacketPingListenerList<E> extends ArrayList<E>
    {
        private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(15, new ThreadFactoryBuilder().setNameFormat("MineAPI Ping Listener #%d").build());

        @Override
        public boolean add(E paramE)
        {
            final E a = paramE;
            this.threadPool.execute(() -> {
                try
                {
                    Channel channel = null;
                    while (channel == null)
                    {
                        channel = getChannel(a);
                    }
                    channel.pipeline().addBefore("packet_handler", "MineAPI_Ping", new INCChannelHandler());
                }
                catch (Exception ignored)
                {
                }
            });
            return super.add(paramE);
        }

        @Override
        public boolean remove(Object paramE)
        {
            final Object a = paramE;
            this.threadPool.execute(() -> {
                try
                {
                    Channel channel = null;
                    while (channel == null)
                    {
                        channel = getChannel(a);
                    }
                    channel.pipeline().remove("MineAPI_Ping");
                }
                catch (Exception ignored)
                {
                }
            });
            return super.remove(paramE);
        }

    }

    public class ChannelHandler extends ChannelDuplexHandler
    {
        private Player player;

        public ChannelHandler(Player p)
        {
            this.player = p;
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
        {
            PacketCancellable cancel = new PacketCancellable();
            NmsPacket mineapi_packet = null;
            try
            {
                mineapi_packet = reader.readPacket(msg, PacketList.getPacketByName(msg.getClass().getSimpleName()).getMineAPIPacket());
            }
            catch (NullPointerException ignored)
            {
            }
            catch (RuntimeException e)
            {
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Error detected in the INCHandler: " + e.getClass().getSimpleName(), true);
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Message: " + e.getMessage(), true);
                if (MineAPI.debug)
                    e.printStackTrace();
            }
            catch (Exception ignored)
            {
            }
            MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(mineapi_packet, msg);
            mineapi.packetSend(packetWrapper, cancel, player);
            if (cancel.isCancelled())
            {
                return;
            }
            if (mineapi_packet == null)
            {
                if (!cancel.hasForceChanges())
                {
                    super.write(ctx, msg, promise);
                    return;
                }
            }
            super.write(ctx, packetWrapper.getNmsPacket(), promise);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
        {
            PacketCancellable cancel = new PacketCancellable();
            NmsPacket mineapi_packet = null;
            try
            {
                mineapi_packet = reader.readPacket(msg, PacketList.getPacketByName(msg.getClass().getSimpleName()).getMineAPIPacket());
            }
            catch (NullPointerException ignored)
            {
            }
            catch (RuntimeException e)
            {
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Error detected in the INCHandler: " + e.getClass().getSimpleName(), true);
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Message: " + e.getMessage(), true);
                if (MineAPI.debug)
                    e.printStackTrace();
            }
            catch (Exception ignored)
            {
            }
            MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(mineapi_packet, msg);
            mineapi.packetReceive(packetWrapper, cancel, player);
            if (cancel.isCancelled())
            {
                return;
            }
            if (mineapi_packet == null)
            {
                if (!cancel.hasForceChanges())
                {
                    super.channelRead(ctx, msg);
                    return;
                }
            }
            super.channelRead(ctx, packetWrapper.getNmsPacket());
        }

        @Override
        public String toString()
        {
            return "ChannelHandler (" + this.player + ")";
        }
    }

    public class INCChannelHandler extends ChannelDuplexHandler
    {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
        {
            if (!msg.getClass().getSimpleName().startsWith("PacketStatus"))
            {
                super.write(ctx, msg, promise);
                return;
            }
            PacketCancellable cancel = new PacketCancellable();
            NmsPacket mineapi_packet = null;
            try
            {
                mineapi_packet = reader.readPacket(msg, PacketList.getPacketByName(msg.getClass().getSimpleName()).getMineAPIPacket());
            }
            catch (NullPointerException ignored)
            {
            }
            catch (RuntimeException e)
            {
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Error detected in the INCHandler: " + e.getClass().getSimpleName(), true);
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Message: " + e.getMessage(), true);
                if (MineAPI.debug)
                    e.printStackTrace();
            }
            catch (Exception ignored)
            {
            }
            MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(mineapi_packet, msg);
            mineapi.pingPacketSend(packetWrapper, cancel, ctx.channel().remoteAddress().toString());
            if (cancel.isCancelled())
            {
                return;
            }
            if (mineapi_packet == null)
            {
                if (!cancel.hasForceChanges())
                {
                    super.write(ctx, msg, promise);
                    return;
                }
            }
            super.write(ctx, packetWrapper.getNmsPacket(), promise);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
        {
            if (msg.getClass().getSimpleName().equals("PacketHandshakingInSetProtocol") && MineAPI.hasEnableDebugConnection())
            {
                MineAPI.sendMessageToConsole(MineAPI.CONNECTION_PREFIX + "[" + ctx.channel().remoteAddress().toString() + "] " + this.getClass().getSimpleName() + " has connected.");
            }
            if ((!msg.getClass().getSimpleName().startsWith("PacketStatus")) || (!msg.getClass().getSimpleName().equals("PacketHandshakingInSetProtocol")))
            {
                super.channelRead(ctx, msg);
                return;
            }
            PacketCancellable cancel = new PacketCancellable();
            NmsPacket mineapi_packet = null;
            try
            {
                mineapi_packet = reader.readPacket(msg, PacketList.getPacketByName(msg.getClass().getSimpleName()).getMineAPIPacket());
            }
            catch (NullPointerException ignored)
            {
            }
            catch (RuntimeException e)
            {
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Error detected in the INCHandler: " + e.getClass().getSimpleName(), true);
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED + " Message: " + e.getMessage(), true);
                if (MineAPI.debug)
                    e.printStackTrace();
            }
            catch (Exception ignored)
            {
            }
            MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(mineapi_packet, msg);
            mineapi.pingPacketReceive(packetWrapper, cancel, ctx.channel().remoteAddress().toString());
            if (cancel.isCancelled())
            {
                return;
            }
            if (mineapi_packet == null)
            {
                if (!cancel.hasForceChanges())
                {
                    super.channelRead(ctx, msg);
                    return;
                }
            }
            super.channelRead(ctx, packetWrapper.getNmsPacket());
        }
    }
}
