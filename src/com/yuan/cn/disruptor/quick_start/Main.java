package com.yuan.cn.disruptor.quick_start;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        /* 步骤：
         *   1 建立一个工厂Event类，用于创建Event类的实例对象
         *   2 需要有一个监听事件类，用于处理数据（Event类）
         *   3 实例化Disruptor实例，配置一系列参数，编写Disruptor核心组件
         *   4 编写生产者组件，向Disruptor容器中去投递数据
         *
         */
        // 1、实例化disruptor对象
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        /*
         * 1 orderEventFactory 消息（event）工厂
         * 2 ringBufferSize 容器的长度
         * 3 executor 线程池（建议使用自定义线程池） RejectedExecutionHandler
         * 4 ProductType 单生产者 还是多生产者
         * 5 waitStrategy 等待策略
         */
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(orderEventFactory,1024*1024,
                executorService,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());
        // 2 添加消费者的监听(构建 disruptor 与消费者的一个关联关系)
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3 启动disruptor
        disruptor.start();

        // 4 获取实际存储数据的内容：RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        ByteBuffer buffer = ByteBuffer.allocate(8);
        for(long i = 0; i< 100; i ++){
            buffer.putLong(0, i);
            producer.sendData(buffer);
        }
        disruptor.shutdown();
        executorService.shutdown();

    }
}
