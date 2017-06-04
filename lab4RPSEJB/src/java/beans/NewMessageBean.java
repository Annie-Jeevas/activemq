/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

/**
 *
 * @author Анюта
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "amqjms")
})
public class NewMessageBean implements MessageListener {

    @Resource(mappedName = "amqjms")
    private Queue amqjms;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    public NewMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        sendJMSMessageToAmqjms("Test");
    }

    private void sendJMSMessageToAmqjms(String messageData) {
        context.createProducer().send(amqjms, messageData);
    }
    
}
