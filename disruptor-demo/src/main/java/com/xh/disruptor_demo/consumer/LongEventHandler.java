package com.xh.disruptor_demo.consumer;

import com.lmax.disruptor.EventHandler;
import com.xh.disruptor_demo.event.LongEvent;

public class LongEventHandler implements EventHandler<LongEvent> {
	
	private long threadId; 
	
	public LongEventHandler() {
		threadId = Thread.currentThread().getId();
	}

	public void onEvent(LongEvent event, long l, boolean b)
			throws Exception {
		System.out.println("Thread:" + threadId + " LongEventHandler:" + event.getValue());
	}

}
