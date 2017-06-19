package com.xh.disruptor_demo.event;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory {

	public Object newInstance() {
		return new LongEvent();
	}

}
