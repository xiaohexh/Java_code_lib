package com.xh.disruptor_demo.producer;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;
import com.xh.disruptor_demo.event.LongEvent;

public class LongEventProducer {

	private final RingBuffer<LongEvent> ringBuffer;
	
	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	
	public void onData(ByteBuffer b) {
		long sequence = ringBuffer.next();
		try {
			LongEvent event = ringBuffer.get(sequence);
			event.setValue(b.getLong(0));
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}
