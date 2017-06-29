package com.xh.disruptor_demo.consumer;

import com.lmax.disruptor.EventHandler;
import com.xh.disruptor_demo.event.LongEvent;

public class AnotherLongEventHandler implements EventHandler<LongEvent> {

	public void onEvent(LongEvent event, long l, boolean b)
			throws Exception {
		System.out.println("AnotherLongEventHandler:" + event.getValue());
	}

}