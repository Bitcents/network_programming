// ------------------------------------------------------ //
//  javac  jProducer.java  --> jProducer.class
//                         --> jConsumer.class
//  java   jConsumer
// ------------------------------------------------------ //
import java.util.Vector;

class jProducer extends Thread {
    private Vector messages = new Vector(); // vector data
    static  int    MAXQUEUE = 5;            // vector side 5
    int     k;
    int     pstime;
 
    jProducer(int kk, int t) {
         k = kk; pstime = t;
         System.out.println("Producer created       k = " + k + 
                            " sleep time = " + pstime);
    }
    public void run() {
        try {
            while ( k > 0 ) {
                putMessage();  k--; // count of send messages
                sleep( pstime );
            }
        } catch( InterruptedException e ) { }
    }
    private synchronized void putMessage() throws InterruptedException {
        while ( messages.size() == MAXQUEUE ) wait();
        messages.addElement( new java.util.Date().toString() );
        notifyAll(); // signal to consumer 
    }
    public synchronized String getMessage() throws InterruptedException {
        notifyAll();
        while ( messages.size() == 0 )        wait();
        String message = (String)messages.firstElement();
        messages.removeElement( message );
        return message;
    }
}

class jConsumer extends Thread {
    jProducer myproducer;
    int       id;
    int       ck;
    int       cstime;
    
    jConsumer(jProducer p, int idno, int kk, int t) {
         myproducer = p;    id     = idno;
         ck         = kk;   cstime = t;
         System.out.println("Consumer( " + id + " ) created ck = " + ck + 
                            " sleep time = " + cstime);
    }
    public void run() {
        try {
            while ( ck > 0 ) {     // count of want messages
              String message = myproducer.getMessage();  ck--;
              System.out.println("Got message: (" + id + ") " + message +
                       " at " + (new java.util.Date().toString()));
              sleep( cstime );
            }
        } catch( InterruptedException e ) { }
    }

    public static void main(String args[]) { // start point
        jProducer baseproducer = new jProducer(14, 800);
                                       // make producer
        baseproducer.start();
        new jConsumer( baseproducer, 1, 8, 1200).start();
                                       // 1-st consumer
        new jConsumer( baseproducer, 2, 6, 1800).start();
                                       // 2-nd consumer
    }
}
