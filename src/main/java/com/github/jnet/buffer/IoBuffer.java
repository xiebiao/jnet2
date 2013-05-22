package com.github.jnet.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.ReadOnlyBufferException;

import com.github.jnet.utils.StringUtils;

/**
 * IOBuffer.
 * 
 * @see http://tutorials.jenkov.com/java-nio/buffers.html
 * @author xiebiao
 * 
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

	/**
	 * Retrieves this buffer's byte order.
	 * 
	 * <p>
	 * The byte order is used when reading or writing multibyte values, and when
	 * creating buffers that are views of this byte buffer. The order of a
	 * newly-created byte buffer is always {@link ByteOrder#BIG_ENDIAN
	 * BIG_ENDIAN}.
	 * </p>
	 * 
	 * @return This buffer's byte order
	 */
	public final ByteOrder order() {
		return buf.order();
	}

	/**
	 * Modifies this buffer's byte order. </p>
	 * 
	 * @param bo
	 *            The new byte order, either {@link ByteOrder#BIG_ENDIAN
	 *            BIG_ENDIAN} or {@link ByteOrder#LITTLE_ENDIAN LITTLE_ENDIAN}
	 * 
	 * @return This buffer
	 */
	public void order(ByteOrder bo) {
		buf.order(bo);
	}

	/**
	 * Sets this buffer's limit. If the position is larger than the new limit
	 * then it is set to the new limit. If the mark is defined and larger than
	 * the new limit then it is reallocate and reset. </p>
	 * 
	 * @param newLimit
	 *            The new limit value; must be non-negative
	 * 
	 * @return This buffer
	 * 
	 * @throws IllegalArgumentException
	 *             If the preconditions on <tt>newLimit</tt> do not hold
	 */
	public void limit(int limit) {
		if (limit > buf.capacity()) {
			ByteBuffer newBuf = ByteBuffer.allocate(limit);
			System.arraycopy(buf.array(), 0, newBuf.array(), 0, buf.capacity());
			newBuf.position(buf.position());
			buf = newBuf;
		}
		buf.limit(limit);
	}

	/**
	 * Returns this buffer's limit. </p>
	 * 
	 * @return The limit of this buffer
	 */
	public int limit() {
		return buf.limit();
	}

	/**
	 * Returns this buffer's position. </p>
	 * 
	 * @return The position of this buffer
	 */
	public int position() {
		return buf.position();
	}

	/**
	 * Sets this buffer's position. If the mark is defined and larger than the
	 * new position then it is discarded. </p>
	 * 
	 * @param newPosition
	 *            The new position value; must be non-negative and no larger
	 *            than the current limit
	 * 
	 * @return This buffer
	 * 
	 * @throws IllegalArgumentException
	 *             If the preconditions on <tt>newPosition</tt> do not hold
	 */
	public void position(int newPosition) {
		buf.position(newPosition);
	}

	/**
	 * Returns this buffer's capacity. </p>
	 * 
	 * @return The capacity of this buffer
	 */
	public int capacity() {
		return buf.capacity();
	}

	/**
	 * Returns the number of elements between the current position and the
	 * limit. </p>
	 * 
	 * @return The number of elements remaining in this buffer
	 */
	public int remaining() {
		return buf.remaining();
	}

	/**
	 * Rewinds this buffer. The position is set to zero and the mark is
	 * discarded.
	 * 
	 * <p>
	 * Invoke this method before a sequence of channel-write or <i>get</i>
	 * operations, assuming that the limit has already been set appropriately.
	 * For example:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * out.write(buf); // Write remaining data
	 * buf.rewind(); // Rewind buffer
	 * buf.get(array); // Copy data into array
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @return This buffer
	 */
	public void rewind() {
		buf.rewind();
	}

	/**
	 * Resets this buffer's position to the previously-marked position.
	 * 
	 * <p>
	 * Invoking this method neither changes nor discards the mark's value.
	 * </p>
	 * 
	 * @return This buffer
	 * 
	 * @throws InvalidMarkException
	 *             If the mark has not been set
	 */
	public void reset() {
		buf.reset();
	}

	/**
	 * Flips this buffer. The limit is set to the current position and then the
	 * position is set to zero. If the mark is defined then it is discarded.
	 * 
	 * <p>
	 * After a sequence of channel-read or <i>put</i> operations, invoke this
	 * method to prepare for a sequence of channel-write or relative <i>get</i>
	 * operations. For example:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * buf.put(magic); // Prepend header
	 * in.read(buf); // Read data into rest of buffer
	 * buf.flip(); // Flip buffer
	 * out.write(buf); // Write header + data to channel
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * <p>
	 * This method is often used in conjunction with the
	 * {@link java.nio.ByteBuffer#compact compact} method when transferring data
	 * from one place to another.
	 * </p>
	 * 
	 * @return This buffer
	 */
	public void flip() {
		buf.flip();
	}

	/**
	 * Clears this buffer. The position is set to zero, the limit is set to the
	 * capacity, and the mark is discarded.
	 * 
	 * <p>
	 * Invoke this method before using a sequence of channel-read or <i>put</i>
	 * operations to fill this buffer. For example:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * buf.clear(); // Prepare buffer for reading
	 * in.read(buf); // Read data
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * <p>
	 * This method does not actually erase the data in the buffer, but it is
	 * named as if it did because it will most often be used in situations in
	 * which that might as well be the case.
	 * </p>
	 * 
	 * @return This buffer
	 */
	public void clear() {
		buf.clear();
	}

	/**
	 * Compacts this buffer&nbsp;&nbsp;<i>(optional operation)</i>.
	 * 
	 * <p>
	 * The bytes between the buffer's current position and its limit, if any,
	 * are copied to the beginning of the buffer. That is, the byte at index
	 * <i>p</i>&nbsp;=&nbsp;<tt>position()</tt> is copied to index zero, the
	 * byte at index <i>p</i>&nbsp;+&nbsp;1 is copied to index one, and so forth
	 * until the byte at index <tt>limit()</tt>&nbsp;-&nbsp;1 is copied to index
	 * <i>n</i>&nbsp;=&nbsp;<tt>limit()</tt>&nbsp;-&nbsp;<tt>1</tt>
	 * &nbsp;-&nbsp;<i>p</i>. The buffer's position is then set to <i>n+1</i>
	 * and its limit is set to its capacity. The mark, if defined, is discarded.
	 * 
	 * <p>
	 * The buffer's position is set to the number of bytes copied, rather than
	 * to zero, so that an invocation of this method can be followed immediately
	 * by an invocation of another relative <i>put</i> method.
	 * </p>
	 * 
	 * 
	 * 
	 * <p>
	 * Invoke this method after writing data from a buffer in case the write was
	 * incomplete. The following loop, for example, copies bytes from one
	 * channel to another via the buffer <tt>buf</tt>:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * buf.clear(); // Prepare buffer for use
	 * while (in.read(buf) &gt;= 0 || buf.position != 0) {
	 * 	buf.flip();
	 * 	out.write(buf);
	 * 	buf.compact(); // In case of partial write
	 * }
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * 
	 * 
	 * @return This buffer
	 * 
	 * @throws ReadOnlyBufferException
	 *             If this buffer is read-only
	 */
	public void compact() {
		buf.compact();
	}

	/**
	 * 读取一个byte
	 * 
	 * @param index
	 *            起始位置
	 * @return
	 */
	public byte readByte(int index) {
		buf.position(index);
		return buf.get();
	}

	/**
	 * 读取一个byte，起始位置=position
	 * 
	 * @return
	 */
	public byte readByte() {
		return readByte(buf.position());
	}

	/**
	 * 读取byte[]，起始位置=position
	 * 
	 * @param len
	 *            长度
	 * @return
	 */
	public byte[] readBytes(int len) {
		return readBytes(buf.position(), len);
	}

	/**
	 * 读取byte[]
	 * 
	 * @param index
	 *            起始位置
	 * @param len
	 *            长度
	 * @return
	 */
	public byte[] readBytes(int index, int len) {
		byte[] bytes = new byte[len];
		buf.position(index);
		buf.get(bytes);
		return bytes;
	}

	/**
	 * 读取一个byte，但不改变position
	 * 
	 * @param index
	 *            起始位置
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
	 * @param len
	 *            长度
	 * @return
	 */
	public byte[] getBytes(int len) {
		return getBytes(buf.position(), len);
	}

	/**
	 * 读取byte[]，但不改变position
	 * 
	 * @param index
	 *            起始位置
	 * @param len
	 *            长度
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
	 * @param b
	 *            数据
	 * @return
	 */
	public void writeByte(byte b) {
		buf.position(buf.position());
		buf.put(b);
	}

	/**
	 * 写入byte
	 * 
	 * @param index
	 *            起始位置
	 * @param b
	 *            数据
	 * @return
	 */
	public void writeByte(int index, byte b) {
		buf.position(index);
		buf.put(b);
	}

	/**
	 * 写入byte[]，起始位置=position
	 * 
	 * @param bytes
	 *            数据
	 * @return
	 */
	public void writeBytes(byte[] bytes) {
		writeBytes(buf.position(), bytes);
	}

	/**
	 * 写入byte[]
	 * 
	 * @param index
	 *            起始位置
	 * @param bytes
	 *            数据
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
	 * @param len
	 *            长度
	 * @param charset
	 *            编码
	 * @return
	 */
	public String readString(int len, String charset) {
		return readString(buf.position(), len, charset);
	}

	/**
	 * 读取字符串
	 * 
	 * @param index
	 *            起始位置
	 * @param len
	 *            长度
	 * @param charset
	 *            编码
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
	 * @param charset
	 *            编码
	 * @return
	 */
	public String getString(String charset) {
		return getString(0, buf.position(), charset);
	}

	/**
	 * 读取字符串，起始位置=0，但不改变position
	 * 
	 * @param len
	 *            长度
	 * @param charset
	 *            编码
	 * @return
	 */
	public String getString(int len, String charset) {
		return getString(buf.position(), len, charset);
	}

	/**
	 * 读取字符串，但不改变position
	 * 
	 * @param index
	 *            起始位置
	 * @param len
	 *            长度
	 * @param charset
	 *            编码
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
	 * @param str
	 *            字符串
	 * @param charset
	 *            编码
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
