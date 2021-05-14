// java Program2.3 : Concurrent Program : Thread
import java.io.*;

public class prog23Thread extends Thread
{
    static PrintStream p = System.out;

    public static void main(String[] args)
    {
        int mc;

	prog23Thread p23 = new prog23Thread();
        Thread      th23 = new Thread(p23);
        th23.start();       // Thread running
        for(mc = 0; mc < 5; mc++) {
           try {
              p.println("2.3 - In prog23Thread   main : " + mc);
              Thread.sleep(1);
	   }
           catch(InterruptedException e) {
              p.println("2.3 - In catch of prog23Thread main()");
           }
        }
	p.println("2.3 - End of prog23Thread main()!");
    }

    public void run()
    {
        int tc;

        for(tc = 0; tc < 5; tc++) {
           try {
              p.println("2.3 - in run of prog23Thread : " + tc);
              Thread.sleep(1);
	   }
           catch(InterruptedException e) {
              p.println("2.3 - in catch of prog23Thread run()");
           }
        }
    }
}
