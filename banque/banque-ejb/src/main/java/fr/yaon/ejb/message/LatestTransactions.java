package fr.yaon.ejb.message;

import fr.yaon.ejb.entity.Transaction;
import fr.yaon.ejb.persist.LocalPersist;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.util.Collection;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/getLatestTransactions")
        }
)
public class LatestTransactions implements MessageListener {
    @Resource
    private ConnectionFactory connectionFactory;

    @EJB
    private LocalPersist localPersist;

    @Override
    public void onMessage(Message message) {
        try {
            Destination destination = message.getJMSReplyTo();

            LatestTransactionMessage reply = new LatestTransactionMessage();

            StringBuilder result = new StringBuilder();
            Collection<Transaction> transactions = localPersist.listLastTransactions(100);

            result.append("[");
            for (Transaction transaction : transactions) {
                result.append("{");
                result.append("source:");
                result.append(transaction.getSource().getId());
                result.append(",");
                result.append("destination:");
                result.append(transaction.getDestination().getId());
                result.append(",");
                result.append("balance:");
                result.append(transaction.getBalance());
                result.append(",");
                result.append("date:");
                result.append(transaction.getDate().getTime());
                result.append(",");
                result.append("},");
            }
            result.append("]");

            reply.setText(result.toString());

            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);

            producer.send(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
