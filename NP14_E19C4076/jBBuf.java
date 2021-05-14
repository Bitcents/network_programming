// ------------------------------------------------------ //
// Multi Threads :   jBBuf.java (BoundedBuffer)
// compile :  javac  jBBuf.java  --> jBBuf.class
//                               --> jBBufConsume.class
// running :  java   jBBuf
// ------------------------------------------------------ //
public class jBBuf {

    int a[] = new int[4];
    int ipt = 0;  // input  counter(B 0, 1, 2, 3, 0, 1, ...
    int opt = 0;  // output counter(B 0, 1, 2, 3, 0, 1, ...

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
        jBBuf  b = new jBBuf();
        Thread t = new Thread(new jBBufConsume(b));

        t.start();        // thread t : jBBufConsume    

        for (int i = 0; i < 10; ++i) {
            b.put(i);     // put into buffer
            System.out.println("put: " + i);     
        }

        try { t.join(); }  // wait end of thread
        catch(InterruptedException ign) { }
        System.out.println("end of both threads.");     
    }
}

class jBBufConsume implements Runnable {
                           // Runnable child thread
    jBBuf  buf;

    jBBufConsume(jBBuf b) {
        buf = b; // rename of b
    }
    public void run() {    // jBBuf from main() & t.start()
        int y;

        for (int i = 0; i < 10; i++) {
            y = buf.get(); // get from buffer
            System.out.println("     - - - get: " + y);
        }
    }
}

