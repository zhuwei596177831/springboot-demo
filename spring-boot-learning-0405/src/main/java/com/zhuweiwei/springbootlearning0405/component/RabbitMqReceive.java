package com.zhuweiwei.springbootlearning0405.component;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

/**
 * @author zww
 * @date 2020-04-25 17:56
 * @description
 **/
@Component
public class RabbitMqReceive {

//    @RabbitListener(queues = {"zhuwewei.news"})
//    public void receiveTest(Test test) {
//        System.out.println("收到消息：" + JSON.toJSONString(test));
//    }

//    @RabbitListener(queues = {"zhuwewei.news"})
    public void receiveTestMessage(Message message) throws Exception {
        /**
         * message配置信息：MessageProperties
         * [headers={__TypeId__=com.zhuweiwei.springbootlearning0405.bean.Test}, contentType=application/json,
         * contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false,
         * receivedExchange=exchange.direct, receivedRoutingKey=zhuwewei.news, deliveryTag=1,
         * consumerTag=amq.ctag-rOGwL9pcsMYYc73HvtLgPQ, consumerQueue=zhuwewei.news]
         */
        System.out.println("message配置信息：" + message.getMessageProperties());
        MessageProperties messageProperties = message.getMessageProperties();
        String typeId__ = messageProperties.getHeader("__TypeId__");
        Class<?> aClass = Class.forName(typeId__);
//        Object o = aClass.newInstance();
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBody());
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        o = objectInputStream.readObject();
//        byteArrayInputStream.close();
//        objectInputStream.close();
        System.out.println("messsage消息内容" + new String(message.getBody()));
    }

}
