package jms.ru.innopolis.stc13;

import javax.jms.JMSException;

public class ClearQueues {
    public static void main(String[] args) {
        try {
            boolean cycleFlag = true;
            while (cycleFlag) {
                Receiver receiver = new Receiver("itemsDao.out");
                String request = receiver.receiveMessage();
                System.out.println(request);
                receiver.close();
                Thread.sleep(2000);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
