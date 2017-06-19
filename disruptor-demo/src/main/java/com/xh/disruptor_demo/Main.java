package com.xh.disruptor_demo;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.xh.disruptor_demo.consumer.AnotherLongEventHandler;
import com.xh.disruptor_demo.consumer.LongEventHandler;
import com.xh.disruptor_demo.event.LongEvent;
import com.xh.disruptor_demo.event.LongEventFactory;
import com.xh.disruptor_demo.producer.LongEventProducer;

public class Main {
	public static void main( String[] args ) {
    	
    	ExecutorService executor = Executors.newFixedThreadPool(4);
    	
    	LongEventFactory factory = new LongEventFactory();
    	
    	int bufferSize = 1024 * 1024;
    	
    	Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, executor);
    	
    	disruptor.handleEventsWith(new LongEventHandler());
//    	disruptor.handleEventsWith(new AnotherLongEventHandler());
    	
    	disruptor.start();
    	
    	RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
    	
    	LongEventProducer producer = new LongEventProducer(ringBuffer);
    	
    	ByteBuffer b = ByteBuffer.allocate(8);
    	for (long l = 0; true; l++) {
    		
    		b.putLong(0, l);
    		producer.onData(b);
    		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
