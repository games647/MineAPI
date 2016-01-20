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

public class PacketBuffer extends ByteBuf
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

    @Override
    public int refCnt()
    {
        return this.byteBuf.refCnt();
    }

    @Override
    public boolean release()
    {
        return this.byteBuf.release();
    }

    @Override
    public boolean release(int decrement)
    {
        return this.byteBuf.release(decrement);
    }

    @Override
    public int capacity()
    {
        return this.byteBuf.capacity();
    }

    @Override
    public ByteBuf capacity(int newCapacity)
    {
        return this.byteBuf.capacity(newCapacity);
    }

    @Override
    public int maxCapacity()
    {
        return this.byteBuf.maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc()
    {
        return this.byteBuf.alloc();
    }

    @Override
    public ByteOrder order()
    {
        return this.byteBuf.order();
    }

    @Override
    public ByteBuf order(ByteOrder endianness)
    {
        return this.byteBuf.order(endianness);
    }

    @Override
    public ByteBuf unwrap()
    {
        return this.byteBuf.unwrap();
    }

    @Override
    public boolean isDirect()
    {
        return this.byteBuf.isDirect();
    }

    @Override
    public int readerIndex()
    {
        return this.byteBuf.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int readerIndex)
    {
        return this.byteBuf.readerIndex(readerIndex);
    }

    @Override
    public int writerIndex()
    {
        return this.byteBuf.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int writerIndex)
    {
        return this.byteBuf.writerIndex(writerIndex);
    }

    @Override
    public ByteBuf setIndex(int readerIndex, int writerIndex)
    {
        return this.byteBuf.setIndex(readerIndex, writerIndex);
    }

    @Override
    public int readableBytes()
    {
        return this.byteBuf.readableBytes();
    }

    @Override
    public int writableBytes()
    {
        return this.byteBuf.writableBytes();
    }

    @Override
    public int maxWritableBytes()
    {
        return this.byteBuf.maxWritableBytes();
    }

    @Override
    public boolean isReadable()
    {
        return this.byteBuf.isReadable();
    }

    @Override
    public boolean isReadable(int size)
    {
        return this.byteBuf.isReadable(size);
    }

    @Override
    public boolean isWritable()
    {
        return this.byteBuf.isWritable();
    }

    @Override
    public boolean isWritable(int size)
    {
        return this.byteBuf.isWritable(size);
    }

    @Override
    public ByteBuf clear()
    {
        return this.byteBuf.clear();
    }

    @Override
    public ByteBuf markReaderIndex()
    {
        return this.byteBuf.markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex()
    {
        return this.byteBuf.resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex()
    {
        return this.byteBuf.markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex()
    {
        return this.byteBuf.resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes()
    {
        return this.byteBuf.discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes()
    {
        return this.byteBuf.discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(int minWritableBytes)
    {
        return this.byteBuf.ensureWritable(minWritableBytes);
    }

    @Override
    public int ensureWritable(int minWritableBytes, boolean force)
    {
        return this.byteBuf.ensureWritable(minWritableBytes, force);
    }

    @Override
    public boolean getBoolean(int index)
    {
        return this.byteBuf.getBoolean(index);
    }

    @Override
    public byte getByte(int index)
    {
        return this.byteBuf.getByte(index);
    }

    @Override
    public short getUnsignedByte(int index)
    {
        return this.byteBuf.getUnsignedByte(index);
    }

    @Override
    public short getShort(int index)
    {
        return this.byteBuf.getShort(index);
    }

    @Override
    public int getUnsignedShort(int index)
    {
        return this.byteBuf.getUnsignedShort(index);
    }

    @Override
    public int getMedium(int index)
    {
        return this.byteBuf.getMedium(index);
    }

    @Override
    public int getUnsignedMedium(int index)
    {
        return this.byteBuf.getUnsignedMedium(index);
    }

    @Override
    public int getInt(int index)
    {
        return this.byteBuf.getInt(index);
    }

    @Override
    public long getUnsignedInt(int index)
    {
        return this.byteBuf.getUnsignedInt(index);
    }

    @Override
    public long getLong(int index)
    {
        return this.byteBuf.getLong(index);
    }

    @Override
    public char getChar(int index)
    {
        return this.byteBuf.getChar(index);
    }

    @Override
    public float getFloat(int index)
    {
        return this.byteBuf.getFloat(index);
    }

    @Override
    public double getDouble(int index)
    {
        return this.byteBuf.getDouble(index);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst)
    {
        return this.byteBuf.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int length)
    {
        return this.byteBuf.getBytes(index, dst, length);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length)
    {
        return this.byteBuf.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst)
    {
        return this.byteBuf.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length)
    {
        return this.byteBuf.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuffer dst)
    {
        return this.byteBuf.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException
    {
        return this.byteBuf.getBytes(index, out, length);
    }

    @Override
    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException
    {
        return this.byteBuf.getBytes(index, out, length);
    }

    @Override
    public ByteBuf setBoolean(int index, boolean value)
    {
        return this.byteBuf.setBoolean(index, value);
    }

    @Override
    public ByteBuf setByte(int index, int value)
    {
        return this.byteBuf.setByte(index, value);
    }

    @Override
    public ByteBuf setShort(int index, int value)
    {
        return this.byteBuf.setShort(index, value);
    }

    @Override
    public ByteBuf setMedium(int index, int value)
    {
        return this.byteBuf.setMedium(index, value);
    }

    @Override
    public ByteBuf setInt(int index, int value)
    {
        return this.byteBuf.setInt(index, value);
    }

    @Override
    public ByteBuf setLong(int index, long value)
    {
        return this.byteBuf.setLong(index, value);
    }

    @Override
    public ByteBuf setChar(int index, int value)
    {
        return this.byteBuf.setChar(index, value);
    }

    @Override
    public ByteBuf setFloat(int index, float value)
    {
        return this.byteBuf.setFloat(index, value);
    }

    @Override
    public ByteBuf setDouble(int index, double value)
    {
        return this.byteBuf.setDouble(index, value);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src)
    {
        return this.byteBuf.setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int length)
    {
        return this.byteBuf.setBytes(index, src, length);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length)
    {
        return this.byteBuf.setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src)
    {
        return this.byteBuf.setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length)
    {
        return this.byteBuf.setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuffer src)
    {
        return this.byteBuf.setBytes(index, src);
    }

    @Override
    public int setBytes(int index, InputStream in, int length) throws IOException
    {
        return this.byteBuf.setBytes(index, in, length);
    }

    @Override
    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException
    {
        return this.byteBuf.setBytes(index, in, length);
    }

    @Override
    public ByteBuf setZero(int index, int length)
    {
        return this.byteBuf.setZero(index, length);
    }

    @Override
    public boolean readBoolean()
    {
        return this.byteBuf.readBoolean();
    }

    @Override
    public byte readByte()
    {
        return this.byteBuf.readByte();
    }

    @Override
    public short readUnsignedByte()
    {
        return this.byteBuf.readUnsignedByte();
    }

    @Override
    public short readShort()
    {
        return this.byteBuf.readShort();
    }

    @Override
    public int readUnsignedShort()
    {
        return this.byteBuf.readUnsignedShort();
    }

    @Override
    public int readMedium()
    {
        return this.byteBuf.readMedium();
    }

    @Override
    public int readUnsignedMedium()
    {
        return this.byteBuf.readUnsignedMedium();
    }

    @Override
    public int readInt()
    {
        return this.byteBuf.readInt();
    }

    @Override
    public long readUnsignedInt()
    {
        return this.byteBuf.readUnsignedInt();
    }

    @Override
    public long readLong()
    {
        return this.byteBuf.readLong();
    }

    @Override
    public char readChar()
    {
        return this.byteBuf.readChar();
    }

    @Override
    public float readFloat()
    {
        return this.byteBuf.readFloat();
    }

    @Override
    public double readDouble()
    {
        return this.byteBuf.readDouble();
    }

    @Override
    public ByteBuf readBytes(int length)
    {
        return this.byteBuf.readBytes(length);
    }

    @Override
    public ByteBuf readSlice(int length)
    {
        return this.byteBuf.readSlice(length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst)
    {
        return this.byteBuf.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int length)
    {
        return this.byteBuf.readBytes(dst, length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length)
    {
        return this.byteBuf.readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(byte[] dst)
    {
        return this.byteBuf.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(byte[] dst, int dstIndex, int length)
    {
        return this.byteBuf.readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer dst)
    {
        return this.byteBuf.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(OutputStream out, int length) throws IOException
    {
        return this.byteBuf.readBytes(out, length);
    }

    @Override
    public int readBytes(GatheringByteChannel out, int length) throws IOException
    {
        return this.byteBuf.readBytes(out, length);
    }

    @Override
    public ByteBuf skipBytes(int length)
    {
        return this.byteBuf.skipBytes(length);
    }

    @Override
    public ByteBuf writeBoolean(boolean value)
    {
        return this.byteBuf.writeBoolean(value);
    }

    @Override
    public ByteBuf writeByte(int value)
    {
        return this.byteBuf.writeByte(value);
    }

    @Override
    public ByteBuf writeShort(int value)
    {
        return this.byteBuf.writeShort(value);
    }

    @Override
    public ByteBuf writeMedium(int value)
    {
        return this.byteBuf.writeMedium(value);
    }

    @Override
    public ByteBuf writeInt(int value)
    {
        return this.byteBuf.writeInt(value);
    }

    @Override
    public ByteBuf writeLong(long value)
    {
        return this.byteBuf.writeLong(value);
    }

    @Override
    public ByteBuf writeChar(int value)
    {
        return this.byteBuf.writeChar(value);
    }

    @Override
    public ByteBuf writeFloat(float value)
    {
        return this.byteBuf.writeFloat(value);
    }

    @Override
    public ByteBuf writeDouble(double value)
    {
        return this.byteBuf.writeDouble(value);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src)
    {
        return this.byteBuf.writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int length)
    {
        return this.byteBuf.writeBytes(src, length);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length)
    {
        return this.byteBuf.writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(byte[] src)
    {
        return this.byteBuf.writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(byte[] src, int srcIndex, int length)
    {
        return this.byteBuf.writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer src)
    {
        return this.byteBuf.writeBytes(src);
    }

    @Override
    public int writeBytes(InputStream in, int length) throws IOException
    {
        return this.byteBuf.writeBytes(in, length);
    }

    @Override
    public int writeBytes(ScatteringByteChannel in, int length) throws IOException
    {
        return this.byteBuf.writeBytes(in, length);
    }

    @Override
    public ByteBuf writeZero(int length)
    {
        return this.byteBuf.writeZero(length);
    }

    @Override
    public int indexOf(int fromIndex, int toIndex, byte value)
    {
        return this.byteBuf.indexOf(fromIndex, toIndex, value);
    }

    @Override
    public int bytesBefore(byte value)
    {
        return this.byteBuf.bytesBefore(value);
    }

    @Override
    public int bytesBefore(int length, byte value)
    {
        return this.byteBuf.bytesBefore(length, value);
    }

    @Override
    public int bytesBefore(int index, int length, byte value)
    {
        return this.byteBuf.bytesBefore(index, length, value);
    }

    @Override
    public int forEachByte(ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByte(processor);
    }

    @Override
    public int forEachByte(int index, int length, ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByte(index, length, processor);
    }

    @Override
    public int forEachByteDesc(ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByteDesc(processor);
    }

    @Override
    public int forEachByteDesc(int index, int length, ByteBufProcessor processor)
    {
        return this.byteBuf.forEachByteDesc(index, length, processor);
    }

    @Override
    public ByteBuf copy()
    {
        return this.byteBuf.copy();
    }

    @Override
    public ByteBuf copy(int index, int length)
    {
        return this.byteBuf.copy(index, length);
    }

    @Override
    public ByteBuf slice()
    {
        return this.byteBuf.slice();
    }

    @Override
    public ByteBuf slice(int index, int length)
    {
        return this.byteBuf.slice(index, length);
    }

    @Override
    public ByteBuf duplicate()
    {
        return this.byteBuf.duplicate();
    }

    @Override
    public int nioBufferCount()
    {
        return this.byteBuf.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer()
    {
        return this.byteBuf.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int index, int length)
    {
        return this.byteBuf.nioBuffer(index, length);
    }

    @Override
    public ByteBuffer internalNioBuffer(int index, int length)
    {
        return this.byteBuf.internalNioBuffer(index, length);
    }

    @Override
    public ByteBuffer[] nioBuffers()
    {
        return this.byteBuf.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int index, int length)
    {
        return this.byteBuf.nioBuffers(index, length);
    }

    @Override
    public boolean hasArray()
    {
        return this.byteBuf.hasArray();
    }

    @Override
    public byte[] array()
    {
        return this.byteBuf.array();
    }

    @Override
    public int arrayOffset()
    {
        return this.byteBuf.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress()
    {
        return this.byteBuf.hasMemoryAddress();
    }

    @Override
    public long memoryAddress()
    {
        return this.byteBuf.memoryAddress();
    }

    @Override
    public String toString(Charset charset)
    {
        return this.byteBuf.toString(charset);
    }

    @Override
    public String toString(int index, int length, Charset charset)
    {
        return this.byteBuf.toString(index, length, charset);
    }

    @Override
    public int hashCode()
    {
        return this.byteBuf.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.byteBuf.equals(obj);
    }

    @Override
    public int compareTo(ByteBuf buffer)
    {
        return this.byteBuf.compareTo(buffer);
    }

    @Override
    public String toString()
    {
        return this.byteBuf.toString();
    }

    @Override
    public ByteBuf retain(int increment)
    {
        return this.byteBuf.retain(increment);
    }

    @Override
    public ByteBuf retain()
    {
        return this.byteBuf.retain();
    }

}
