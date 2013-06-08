package com.github.jnet;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class JnetThreadFactory implements ThreadFactory {
	private static final AtomicInteger threadNumber = new AtomicInteger(0);

	public JnetThreadFactory() {
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, "Jnet-Worker-Thread-" + threadNumber.getAndIncrement());
	}

}
