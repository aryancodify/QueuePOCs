package com.kafka.util;

import java.util.concurrent.atomic.AtomicInteger;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class RoundRobinPartitioner implements Partitioner{

	    final AtomicInteger counter = new AtomicInteger(0);

	    public RoundRobinPartitioner(VerifiableProperties props) {
	     
	    }
	    /**
	     * Take key as value and return the partition number
	     */
	    public int partition(Object key, int partitions) {

	    int partitionId = counter.incrementAndGet() % partitions;
	if (counter.get() > 65536) {
	           counter.set(0);
	}
	return partitionId; 
	    }
	
}
