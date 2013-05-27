package com.github.jnet.utils;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.Session;
import com.github.jnet.buffer.IoBuffer;

public final class IoUtils {
	private static Logger logger = LoggerFactory.getLogger(IoUtils.class);

	/**
	 * 网络读操作
	 * 
	 * @param socket
	 * @param buf
	 * @return
	 * @throws Exception
	 */
	public static int read(SocketChannel socket, IoBuffer buf)
			throws IOException {
		int len = socket.read(buf.getBuffer());
		if (len < 0) {
			throw new IOException("IO Error");
		}
		return len;
	}

	public static int read(Session session, SocketChannel socket, IoBuffer buf)
			throws IOException {
//		logger.debug("Session[" + session.getId() + "] is reading \n"
//				+ buf.toString());
		return read(socket, buf);
	}

	/**
	 * 网络写操作
	 * 
	 * @param socket
	 * @param buf
	 * @return
	 * @throws Exception
	 */
	public static int write(SocketChannel socket, IoBuffer buf)
			throws IOException {
		int len = socket.write(buf.getBuffer());
		if (len < 0) {
			throw new IOException("IO Error");
		}
		return len;
	}

	public static int write(Session session, SocketChannel socket, IoBuffer buf)
			throws IOException {
		logger.debug("Session[" + session.getId() + "] is writing \n"
				+ buf.toString());
		return write(socket, buf);
	}
}
