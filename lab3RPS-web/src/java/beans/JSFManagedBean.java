/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Анюта
 */
@Named(value = "jSFManagedBean")
@RequestScoped
public class JSFManagedBean {

//    @Resource(mappedName = "amqjms")
//    private Queue amqjms;
//
//    @Inject
//    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a new instance of JSFManagedBean
     */
    public JSFManagedBean() {

    }

    public String sendMess() {
        //sendJMSMessageToAmqjms(this.getMessage());
        //return "sendMessageSuccess";
         try {

            Connection connection = null;
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("amqjms");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage tmessage = session.createTextMessage(this.message);
            producer.send(tmessage);
            connection.close();            
            return "sendMessageSuccess";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "sendMessageFail";
        }

    }

//    private void sendJMSMessageToAmqjms(String messageData) {
//        context.createProducer().send(amqjms, messageData);
//    }

}
