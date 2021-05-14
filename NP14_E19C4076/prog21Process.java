// java Program2.1 : Procedural Program
import java.io.*;

public class prog21Process
{
    static PrintStream p = System.out;

    public static void main(String[] args)
    {
	prog21Process p21 = new prog21Process();
        p21.run(); // call function
	p.println("2.1 - End of prog21Process in main()!");
    }
    public void run()
    {
        p.println("2.1 - In run of prog21Process : こんにちは! プロセスです。");
    }
}
