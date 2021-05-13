// R1SocketClient.java -----
import java.net.*;
import java.io.*;

public class R1SocketClient {
    static  PrintStream   p = System.out;
    InputStreamReader stdin = new InputStreamReader(System.in);
    // (ci1)
    public static void main (String argv[]) {
        R1SocketClient body = new R1SocketClient();

        String hostName = "localhost";   // default
        int    portNo   = 4002;          // default
        Socket client;

        if( argv.length > 0 ) hostName = argv[0];
        if( argv.length > 1 ) portNo   = Integer.parseInt(argv[1]);
        p.println("your Server : " + hostName +
                ", port Number " + portNo);

        p.println("���b�Z�[�W���J��Ԃ��đ���܂� quit�܂�");

        try {
            client = new Socket(hostName, portNo);
            // (co2)
            PrintStream toServer =
                    new PrintStream(
                            new BufferedOutputStream(client.getOutputStream()),true);
            // with flush()
            // (ci3)
            InputStreamReader fromServer =
                    new InputStreamReader(client.getInputStream());

            String sendMsg  = new String();
            sendMsg = "init";

            p.println(
                    "Keyin send message to Server: (enter \"quit\" to end up)");

            while (sendMsg.indexOf("quit") == -1) {
                sendMsg  = body.readStringFrom(body.stdin);
                // (ci1)from stdin
                toServer.println(sendMsg);   // (co2)send to gnServer
                p.println(body.readStringFrom(fromServer));
                // (ci3) and (co4)
                if (sendMsg.equals("quit")) break;
            }
            client.close();
        }
        catch (UnknownHostException e) {
            p.println("UnknownHostException:  " + e.getMessage());
        }
        catch (IOException io) {
            p.println("IOException:  " + io.getMessage());
        }
    }

    public String readStringFrom(InputStreamReader ir) throws EOFException {
        // given InputStreamReader ir
        String tmp = "";
        char   ch;
        int    ic;

        try {
            while(true) {
                ic = ir.read();         // next int-code
                if(ic == -1) { // EOF
                    p.println("(e) -1 as end of file ");
                    return("EndOfFile"); // at EOF : -1
                }
                ch = (char) ic;         // int to char
                if(ch == '\n') break;   // for new line
                if(ch == '\0') break;   // for new line
                //p.print(" " + ch + ",");
                tmp = tmp + ch;         // any other character
            }
            // p.println("...");
            p.println("given String is  = " + tmp);
        }   // end of try
        catch(IOException e) { }

        return(tmp); // return current command or line
    }
}
