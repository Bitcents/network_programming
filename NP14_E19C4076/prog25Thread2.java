// java Program2.5 : Concurrent Program 2 with sleep(1+d)
import java.io.*;
public class prog25Thread2 extends Thread
{
    static PrintStream p = System.out;
    private int myNumber;

    public static void main(String[] args)
    {
        int mc;
	prog25Thread2 pa = new prog25Thread2(2020);
	prog25Thread2 pb = new prog25Thread2(2022);
	Thread        ta = new Thread(pa);
	Thread        tb = new Thread(pb);
        ta.start();   // Thread ta run() by start
        tb.start();   // Thread tb run() by start
        for(mc = 0; mc < 5; mc++) {
           try {
              p.println("2.5 - In prog25Thread2 main   : " + mc);
              Thread.sleep(1 + (int)(Math.random()*10));
	   }
           catch(InterruptedException e) {
              p.println("2.1 - In catch of prog25Thread2 main()");
           }
        }
	p.println("2.5 - End of prog25Thread2 main()!");
    }
    public prog25Thread2(int muNum)  // Constructer
    {
        this.myNumber = muNum;
    }
    public void run()
    {
        int rc;
        for(rc = 0; rc < 5; rc++) {
           try {
              p.println("2.5 - in run of prog25Thread2 : " + rc 
                      + " of myNumber " + myNumber);
              Thread.sleep(1 + (int)(Math.random()*10));
              // Thread.sleep(1);
	   }
           catch(InterruptedException e) {
              p.println("2.5 - in catch of prog25Thread2 run()");
           }
	}
    }
}
