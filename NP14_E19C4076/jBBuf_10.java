// ------------------------------------------------------ //
// Multi Threads :   jBBuf_10.java (BoundedBuffer)
// compile :  javac  jBBuf_10.java  --> jBBuf_10.class
//                                  --> jBBuf_10Consume.class
// running :  java   jBBuf_10
// ------------------------------------------------------ //
public class jBBuf_10 {

    int a[] = new int[4];
    int ipt = 0;  // input  counter(B 0, 1, 2, 3, 0...
    int opt = 0;  // output counter(B 0, 1, 2, 3, 0...

    public synchronized void put(int x) {
        while ((ipt+1)%4 == opt) 
            try { this.wait(); } catch(InterruptedException ign) { }
        a[ipt] = x; ipt = (ipt+1)%4; 
        this.notifyAll();
    }
    public synchronized int get() {
        while (opt == ipt)
            try { this.wait(); } catch(InterruptedException ign) { }
        int x = a[opt]; opt = (opt+1)%4; 
        this.notifyAll(); 
        return x;
    }
    public static void main(String args[]) {
        jBBuf_10 b = new jBBuf_10();
        Thread   t = new Thread(new jBBuf_10Consume(b));

        t.start();        // thread t : jBBuf_10Consume    

        for (int i = 0; i < 10; ++i) {
            b.put(i*10);     // put into buffer
            System.out.println("put: " + i);     
        }

        try { t.join(); }  // wait end of thread
        catch(InterruptedException ign) { }
        System.out.println("end of both threads.");     
    }
}

class jBBuf_10Consume implements Runnable {
                              // Runnable child thread
    jBBuf_10  buf;

    jBBuf_10Consume(jBBuf_10 b) {
        buf = b; // rename of b
    }
    public void run() {    // jBBuf_10 from main() & t.start()
        int y;

        for (int i = 0; i < 10; i++) {
            y = buf.get(); // get from buffer
            System.out.println("     - - - get: " + y);
        }
    }
}

