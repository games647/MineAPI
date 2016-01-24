package com.w67clement.mineapi.system.messaging;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.UUID;

public class PacketBuffer
{

    private ByteBuf byteBuf;

    public PacketBuffer(ByteBuf byteBuf)
    {
        this.byteBuf = byteBuf;
    }

    public void writeUuid(UUID uuid)
    {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUuid()
    {
        return new UUID(this.readLong(), this.readLong());
    }

    public void writeEnumValue(Enum<?> value)
    {
        this.writeVarIntToBuffer(value.ordinal());
    }

    public Enum<?> readEnumValue(Class<?> enumClass)
    {
        return ((Enum[]) enumClass.getEnumConstants())[this.readVarIntFromBuffer()];
    }

    public void writeVarIntToBuffer(int input)
    {
        while ((input & -128) != 0)
        {
            this.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        this.writeByte(input);
    }

    public int readVarIntFromBuffer()
    {
        int var1 = 0;
        int var2 = 0;
        byte var3;

        do
        {
            var3 = this.readByte();
            var1 |= (var3 & 127) << var2++ * 7;

            if (var2 > 5)
            {
                throw new RuntimeException("VarInt too big");
            }
        } while ((var3 & 128) == 128);

        return var1;
    }

    public String readStringFromBuffer(int maxLength)
    {
        int var2 = this.readVarIntFromBuffer();

        if (var2 > maxLength * 4)
        {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + var2 + " > " + maxLength * 4 + ")");
        }
        else if (var2 < 0)
        {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        else
        {
            String var3 = new String(this.readBytes(var2).array(), Charsets.UTF_8);

            if (var3.length() > maxLength)
            {
                throw new DecoderException("The received string length is longer than maximum allowed (" + var2 + " > " + maxLength + ")");
            }
            else
            {
                return var3;
            }
        }
    }

    public PacketBuffer writeString(String string)
    {
        byte[] var2 = string.getBytes(Charsets.UTF_8);

        if (var2.length > 32767)
        {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
        }
        else
        {
            this.writeVarIntToBuffer(var2.length);
            this.writeBytes(var2);
            return this;
        }
    }

    public int refCnt()
    {
        return this.byteBuf.refCnt();
    }

    public boolean release()
    {
        return this.byteBuf.release();
    }

    public boolean release(int decrement)
    {
        return this.byteBuf.release(decrement);
    }

    public int capacity()
    {
        return this.byteBuf.capacity();
    }

    public PacketBuffer capacity(int newCapacity)
    {
        this.byteBuf.capacity(newCapacity);
        return this;
    }

    public int maxCapacity()
    {
        return this.byteBuf.maxCapacity();
    }

    public ByteBufAllocator alloc()
    {
        return this.byteBuf.alloc();
    }

    public ByteOrder order()
    {
        return this.byteBuf.order();
    }

    public PacketBuffer order(ByteOrder endianness)
    {
        this.byteBuf.order(endianness);
        return this;
    }

    public PacketBuffer unwrap()
    {
        this.byteBuf.unwrap();
        return this;
    }

    public boolean isDirect()
    {
        return this.byteBuf.isDirect();
    }

    public int readerIndex()
    {
        return this.byteBuf.readerIndex();
    }

    public PacketBuffer readerIndex(int readerIndex)
    {
        this.byteBuf.readerIndex(readerIndex);
        return this;
    }

    public int writerIndex()
    {
        return this.byteBuf.writerIndex();
    }

    public PacketBuffer writerIndex(int writerIndex)
    {
        this.byteBuf.writerIndex(writerIndex);
        return this;
    }

    public PacketBuffer setIndex(int readerIndex, int writerIndex)
    {
        this.byteBuf.setIndex(readerIndex, writerIndex);
        return this;
    }

    public int readableBytes()
    {
        return this.byteBuf.readableBytes();
    }

    public int writableBytes()
    {
        return this.byteBuf.writableBytes();
    }

    public int maxWritableBytes()
    {
        return this.byteBuf.maxWritableBytes();
    }

    public boolean isReadable()
    {
        return this.byteBuf.isReadable();
    }

    public boolean isReadable(int size)
    {
        return this.byteBuf.isReadable(size);
    }

    public boolean isWritable()
    {
        return this.byteBuf.isWritable();
    }

    public boolean isWritable(int size)
    {
        return this.byteBuf.isWritable(size);
    }

    public void clear()
    {
        this.byteBuf = this.byteBuf.clear();
    }

    public PacketBuffer markReaderIndex()
    {
        this.byteBuf.markReaderIndex();
        return this;
    }

    public PacketBuffer resetReaderIndex()
    {
        this.byteBuf.resetReaderIndex();
        return this;
    }

    public PacketBuffer markWriterIndex()
    {
        this.byteBuf.markWriterIndex();
        return this;
    }

    public PacketBuffer resetWriterIndex()
    {
        this.byteBuf.resetWriterIndex();
        return this;
    }

    public PacketBuffer discardReadBytes()
    {
        this.byteBuf.discardReadBytes();
        return this;
    }

    public PacketBuffer discardSomeReadBytes()
    {
        this.byteBuf.discardSomeReadBytes();
        return this;
    }

    public PacketBuffer ensureWritable(int minWritableBytes)
    {
        this.byteBuf.ensureWritable(minWritableBytes);
        return this;
    }

    public int ensureWritable(int minWritableBytes, boolean force)
    {
        return this.byteBuf.ensureWritable(minWritableBytes, force);
    }

    public boolean getBoolean(int index)
    {
        return this.byteBuf.getBoolean(index);
    }

    public byte getByte(int index)
    {
        return this.byteBuf.getByte(index);
    }

    public short getUnsignedByte(int index)
    {
        return this.byteBuf.getUnsignedByte(index);
    }

    public short getShort(int index)
    {
        return this.byteBuf.getShort(index);
    }

    public int getUnsignedShort(int index)
    {
        return this.byteBuf.getUnsignedShort(index);
    }

    public int getMedium(int index)
    {
        return this.byteBuf.getMedium(index);
    }

    public int getUnsignedMedium(int index)
    {
        return this.byteBuf.getUnsignedMedium(index);
    }

    public int getInt(int index)
    {
        return this.byteBuf.getInt(index);
    }

    public long getUnsignedInt(int index)
    {
        return this.byteBuf.getUnsignedInt(index);
    }

    public long getLong(int index)
    {
        return this.byteBuf.getLong(index);
    }

    public char getChar(int index)
    {
        return this.byteBuf.getChar(index);
    }

    public float getFloat(int index)
    {
        return this.byteBuf.getFloat(index);
    }

    public double getDouble(int index)
    {
        return this.byteBuf.getDouble(index);
    }

    public PacketBuffer getBytes(int index, ByteBuf dst)
    {
        return new PacketBuffer(this.byteBuf.getBytes(index, dst));
    }

    public PacketBuffer getBytes(int index, ByteBuf dst, int length)
    {
        return new PacketBuffer(this.byteBuf.getBytes(index, dst, length));
    }

    public PacketBuffer getBytes(int index, ByteBuf dst, int dstIndex, int length)
    {
        return new PacketBuffer(this.byteBuf.getBytes(index, dst, dstIndex, length));
    }

    public PacketBuffer getBytes(int index, byte[] dst)
    {
        return new PacketBuffer(this.byteBuf.getBytes(index, dst));
    }

    public PacketBuffer getBytes(int index, byte[] dst, int dstIndex, int length)
    {
        return new PacketBuffer(this.byteBuf.getBytes(index, dst, dstIndex, length));
    }

    public PacketBuffer getBytes(int index, ByteBuffer dst)
    {
        return new PacketBuffer(this.byteBuf.getBytes(index, dst));
    }

    public PacketBuffer getBytes(int index, OutputStream out, int length) throws IOException
    {
        return new PacketBuffer(this.byteBuf.getBytes(index, out, length));
    }

    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException
    {
        return this.byteBuf.getBytes(index, out, length);
    }

    public PacketBuffer setBoolean(int index, boolean value)
    {
        this.byteBuf.setBoolean(index, value);
        return this;
    }

    public PacketBuffer setByte(int index, int value)
    {
        this.byteBuf.setByte(index, value);
        return this;
    }

    public PacketBuffer setShort(int index, int value)
    {
        this.byteBuf.setShort(index, value);
        return this;
    }

    public PacketBuffer setMedium(int index, int value)
    {
        this.byteBuf.setMedium(index, value);
        return this;
    }

    public PacketBuffer setInt(int index, int value)
    {
        this.byteBuf.setInt(index, value);
        return this;
    }

    public PacketBuffer setLong(int index, long value)
    {
        this.byteBuf.setLong(index, value);
        return this;
    }

    public PacketBuffer setChar(int index, int value)
    {
        this.byteBuf.setChar(index, value);
        return this;
    }

    public PacketBuffer setFloat(int index, float value)
    {
        this.byteBuf.setFloat(index, value);
        return this;
    }

    public PacketBuffer setDouble(int index, double value)
    {
        this.byteBuf.setDouble(index, value);
        return this;
    }

    public PacketBuffer setBytes(int index, ByteBuf src)
    {
        this.byteBuf.setBytes(index, src);
        return this;
    }

    public PacketBuffer setBytes(int index, ByteBuf src, int length)
    {
        this.byteBuf.setBytes(index, src, length);
        return this;
    }

    public PacketBuffer setBytes(int index, ByteBuf src, int srcIndex, int length)
    {
        this.byteBuf.setBytes(index, src, srcIndex, length);
        return this;
    }

    public PacketBuffer setBytes(int index, byte[] src)
    {
        this.byteBuf.setBytes(index, src);
        return this;
    }

    public PacketBuffer setBytes(int index, byte[] src, int srcIndex, int length)
    {
        this.byteBuf.setBytes(index, src, srcIndex, length);
        return this;
    }

    public PacketBuffer setBytes(int index, ByteBuffer src)
    {
        this.byteBuf.setBytes(index, src);
        return this;
    }

    public int setBytes(int index, InputStream in, int length) throws IOException
    {
        return this.byteBuf.setBytes(index, in, length);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException
    {
        return this.byteBuf.setBytes(index, in, length);
    }

    public PacketBuffer setZero(int index, int length)
    {
        this.byteBuf.setZero(index, length);
        return this;
    }

    public boolean readBoolean()
    {
        return this.byteBuf.readBoolean();
    }

    public byte readByte()
    {
        return this.byteBuf.readByte();
    }

    public short readUnsignedByte()
    {
        return this.byteBuf.readUnsignedByte();
    }

    public short readShort()
    {
        return this.byteBuf.readShort();
    }

    public int readUnsignedShort()
    {
        return this.byteBuf.readUnsignedShort();
    }

    public int readMedium()
    {
        return this.byteBuf.readMedium();
    }

    public int readUnsignedMedium()
    {
        return this.byteBuf.readUnsignedMedium();
    }

    public int readInt()
    {
        return this.byteBuf.readInt();
    }

    public long readUnsignedInt()
    {
        return this.byteBuf.readUnsignedInt();
    }

    public long readLong()
    {
        return this.byteBuf.readLong();
    }

    public char readChar()
    {
        return this.byteBuf.readChar();
    }

    public float readFloat()
    {
        return this.byteBuf.readFloat();
    }

    public double readDouble()
    {
        return this.byteBuf.readDouble();
    }

    public PacketBuffer readBytes(int length)
    {
        return new PacketBuffer(this.byteBuf.readBytes(length));
    }

    public PacketBuffer readSlice(int length)
    {
        return new PacketBuffer(byteBuf.readSlice(length));
    }

    public PacketBuffer readBytes(ByteBuf dst)
    {
        this.byteBuf.readBytes(dst);
        return this;
    }

    public PacketBuffer readBytes(ByteBuf dst, int length)
    {
        this.byteBuf.readBytes(dst, length);
        return this;
    }

    public PacketBuffer readBytes(ByteBuf dst, int dstIndex, int length)
    {
        this.byteBuf.readBytes(dst, dstIndex, length);
        return this;
    }

    public PacketBuffer readBytes(byte[] dst)
    {
        this.byteBuf.readBytes(dst);
        return this;
    }

    public PacketBuffer readBytes(byte[] dst, int dstIndex, int length)
    {
        this.byteBuf.readBytes(dst, dstIndex, length);
        return this;
    }

    public PacketBuffer readBytes(ByteBuffer dst)
    {
        this.byteBuf.readBytes(dst);
        return this;
    }

    public PacketBuffer readBytes(OutputStream out, int length) throws IOException
    {
        this.byteBuf.readBytes(out, length);
        return this;
    }

    public int readBytes(GatheringByteChannel out, int length) throws IOException
    {
        return this.byteBuf.readBytes(out, length);
    }

    public PacketBuffer skipBytes(int length)
    {
        this.byteBuf.skipBytes(length);
        return this;
    }

    public PacketBuffer writeBoolean(boolean value)
    {
        this.byteBuf.writeBoolean(value);
        return this;
    }

    public PacketBuffer writeByte(int value)
    {
        this.byteBuf.writeByte(value);
        return this;
    }

    public PacketBuffer writeShort(int value)
    {
        this.byteBuf.writeShort(value);
        return this;
    }

    public PacketBuffer writeMedium(int value)
    {
        this.byteBuf.writeMedium(value);
        return this;
    }

    public PacketBuffer writeInt(int value)
    {
        this.byteBuf.writeInt(value);
        return this;
    }

    public PacketBuffer writeLong(long value)
    {
        this.byteBuf.writeLong(value);
        return this;
    }

    public PacketBuffer writeChar(int value)
    {
        this.byteBuf.writeChar(value);
        return this;
    }

    public PacketBuffer writeFloat(float value)
    {
        this.byteBuf.writeFloat(value);
        return this;
    }

    public PacketBuffer writeDouble(double value)
    {
        this.byteBuf.writeDouble(value);
        return this;
    }

    public PacketBuffer writeBytes(ByteBuf src)
    {
        this.byteBuf.writeBytes(src);
        return this;
    }

    public PacketBuffer writeBytes(ByteBuf src, int length)
    {
        this.byteBuf.writeBytes(src, length);
        return this;
    }

    public PacketBuffer writeBytes(ByteBuf src, int srcIndex, int length)
    {
        this.byteBuf.writeBytes(src, srcIndex, length);
        return this;
    }

    public PacketBuffer writeBytes(byte[] src)
    {
        this.byteBuf.writeBytes(src);
        return this;
    }

    public PacketBuffer writeBytes(byte[] src, int srcIndex, int length)
    {
        this.byteBuf.writeBytes(src, srcIndex, length);
        return this;
    }

    public PacketBuffer writeBytes(ByteBuffer src)
    {
        this.byteBuf.writeBytes(src);
        return this;
    }

    public int writeBytes(InputStream in, int length) throws IOException
    {
        return this.byteBuf.writeBytes(in, length);
    }

    public int writeBytes(ScatteringByteChannel in, int length) throws IOException
    {
        return this.byteBuf.writeBytes(in, length);
    }

    public PacketBuffer writeZero(int length)
    {
        this.byteBuf.writeZero(length);
        return this;
    }

    public int indexOf(int fromIndex, int toIndex, byte value)
    {
        return this.byteBuf.indexOf(fromIndex, toIndex, value);
    }

    public int bytesBefore(byte value)
    {
        return this.byteBuf.bytesBefore(value);
    }

    public int bytesBefore(int length, byte value)
    {
        return this.byteBuf.bytesBefore(length, value);
    }

    public int bytesBefore(int index, int length, byte value)
    {
        return this.byteBuf.bytesBefore(index, length, value);
    }

    public int forEachByte(ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByte(processor);
    }

    public int forEachByte(int index, int length, ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByte(index, length, processor);
    }

    public int forEachByteDesc(ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByteDesc(processor);
    }

    public int forEachByteDesc(int index, int length, ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByteDesc(index, length, processor);
    }

    public PacketBuffer copy()
    {
        return new PacketBuffer(this.byteBuf.copy());
    }

    public PacketBuffer copy(int index, int length)
    {
        return new PacketBuffer(this.byteBuf.copy(index, length));
    }

    public PacketBuffer slice()
    {
        return new PacketBuffer(this.byteBuf.slice());
    }

    public PacketBuffer slice(int index, int length)
    {
        return new PacketBuffer(this.byteBuf.slice(index, length));
    }

    public PacketBuffer duplicate()
    {
        return new PacketBuffer(this.byteBuf.duplicate());
    }

    public int nioBufferCount()
    {
        return this.byteBuf.nioBufferCount();
    }

    public ByteBuffer nioBuffer()
    {
        return this.byteBuf.nioBuffer();
    }

    public ByteBuffer nioBuffer(int index, int length)
    {
        return this.byteBuf.nioBuffer(index, length);
    }

    public ByteBuffer internalNioBuffer(int index, int length)
    {
        return this.byteBuf.internalNioBuffer(index, length);
    }

    public ByteBuffer[] nioBuffers()
    {
        return this.byteBuf.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int index, int length)
    {
        return this.byteBuf.nioBuffers(index, length);
    }

    public boolean hasArray()
    {
        return this.byteBuf.hasArray();
    }

    public byte[] array()
    {
        return this.byteBuf.array();
    }

    public int arrayOffset()
    {
        return this.byteBuf.arrayOffset();
    }

    public boolean hasMemoryAddress()
    {
        return this.byteBuf.hasMemoryAddress();
    }

    public long memoryAddress()
    {
        return this.byteBuf.memoryAddress();
    }

    public String toString(Charset charset)
    {
        return this.byteBuf.toString(charset);
    }

    public String toString(int index, int length, Charset charset)
    {
        return this.byteBuf.toString(index, length, charset);
    }

    @Override
    public int hashCode()
    {
        return this.byteBuf.hashCode();
    }

    public boolean equals(Object obj)
    {
        return this.byteBuf.equals(obj);
    }

    public int compareTo(ByteBuf buffer)
    {
        return this.byteBuf.compareTo(buffer);
    }

    @Override
    public String toString()
    {
        return this.byteBuf.toString();
    }

    public PacketBuffer retain(int increment)
    {
        this.byteBuf.retain(increment);
        return this;
    }

    public PacketBuffer retain()
    {
        this.byteBuf.retain();
        return this;
    }

}
