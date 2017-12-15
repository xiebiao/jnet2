package com.github.jnet.buffer;

import com.github.jnet.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * IOBuffer.
 *
 * @author xiebiao
 * @see <a href="http://tutorials.jenkov.com/java-nio/buffers.html">buffers</a>
 */
public class IoBuffer {

    protected ByteBuffer buf;

    public ByteBuffer getBuffer() {
        return buf;
    }

    public IoBuffer(int initSize) {
        buf = ByteBuffer.allocate(initSize);
    }

    public IoBuffer() {
        buf = ByteBuffer.allocate(0);
    }

    public final ByteOrder order() {
        return buf.order();
    }

    public void order(ByteOrder bo) {
        buf.order(bo);
    }

    public void limit(int limit) {
        if (limit > buf.capacity()) {
            ByteBuffer newBuf = ByteBuffer.allocate(limit);
            System.arraycopy(buf.array(), 0, newBuf.array(), 0, buf.capacity());
            newBuf.position(buf.position());
            buf = newBuf;
        }
        buf.limit(limit);
    }

    public int limit() {
        return buf.limit();
    }

    public int position() {
        return buf.position();
    }

    public void position(int newPosition) {
        buf.position(newPosition);
    }

    public int capacity() {
        return buf.capacity();
    }

    public int remaining() {
        return buf.remaining();
    }

    public void rewind() {
        buf.rewind();
    }

    public void reset() {
        buf.reset();
    }

    public void flip() {
        buf.flip();
    }

    public void clear() {
        buf.clear();
    }

    public void compact() {
        buf.compact();
    }

    public byte readByte(int index) {
        buf.position(index);
        return buf.get();
    }

    public byte readByte() {
        return readByte(buf.position());
    }

    public byte[] readBytes(int len) {
        return readBytes(buf.position(), len);
    }

    public byte[] readBytes(int index, int len) {
        byte[] bytes = new byte[len];
        buf.position(index);
        buf.get(bytes);
        return bytes;
    }

    /**
     * 读取一个byte，但不改变position
     *
     * @param index 起始位置
     * @return
     */
    public byte getByte(int index) {
        int pos = buf.position();
        buf.position(index);
        byte b = buf.get();
        buf.position(pos);
        return b;
    }

    /**
     * 读取一个byte，起始位置=position，但不改变position
     *
     * @return
     */
    public byte getByte() {
        return getByte(buf.position());
    }

    /**
     * 读取byte[]，起始位置=position，但不改变position
     *
     * @param len 长度
     * @return
     */
    public byte[] getBytes(int len) {
        return getBytes(buf.position(), len);
    }

    /**
     * 读取byte[]，但不改变position
     *
     * @param index 起始位置
     * @param len   长度
     * @return
     */
    public byte[] getBytes(int index, int len) {
        int pos = buf.position();
        byte[] bytes = new byte[len];
        buf.position(index);
        buf.get(bytes);
        buf.position(pos);
        return bytes;
    }

    /**
     * 写入byte，起始位置=position
     *
     * @param b 数据
     * @return
     */
    public void writeByte(byte b) {
        buf.position(buf.position());
        buf.put(b);
    }

    /**
     * 写入byte
     *
     * @param index 起始位置
     * @param b     数据
     * @return
     */
    public void writeByte(int index, byte b) {
        buf.position(index);
        buf.put(b);
    }

    /**
     * 写入byte[]，起始位置=position
     *
     * @param bytes 数据
     * @return
     */
    public void writeBytes(byte[] bytes) {
        writeBytes(buf.position(), bytes);
    }

    /**
     * 写入byte[]
     *
     * @param index 起始位置
     * @param bytes 数据
     * @return
     */
    public void writeBytes(int index, byte[] bytes) {
        buf.position(index);
        limit(index + bytes.length);
        buf.put(bytes);
    }

    /**
     * 读取字符串，起始位置=position
     *
     * @param len     长度
     * @param charset 编码
     * @return
     */
    public String readString(int len, String charset) {
        return readString(buf.position(), len, charset);
    }

    /**
     * 读取字符串
     *
     * @param index   起始位置
     * @param len     长度
     * @param charset 编码
     * @return
     */
    public String readString(int index, int len, String charset) {
        buf.position(index);
        byte[] bytes = readBytes(len);
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 读取字符串，起始位置=0,长度=position，但不改变position
     *
     * @param charset 编码
     * @return
     */
    public String getString(String charset) {
        return getString(0, buf.position(), charset);
    }

    /**
     * 读取字符串，起始位置=0，但不改变position
     *
     * @param len     长度
     * @param charset 编码
     * @return
     */
    public String getString(int len, String charset) {
        return getString(buf.position(), len, charset);
    }

    /**
     * 读取字符串，但不改变position
     *
     * @param index   起始位置
     * @param len     长度
     * @param charset 编码
     * @return
     */
    public String getString(int index, int len, String charset) {
        int pos = buf.position();
        buf.position(index);
        byte[] bytes = readBytes(len);
        buf.position(pos);
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 写入字符串，起始位置=position
     *
     * @param str     字符串
     * @param charset 编码
     */
    public void writeString(String str, String charset) {
        byte[] bytes;
        try {
            bytes = str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            return;
        }
        limit(buf.position() + bytes.length);
        buf.put(bytes);
    }

    public String toString() {
        return StringUtils.dumpAsHex(getBytes(0, limit()), limit());
    }
}
