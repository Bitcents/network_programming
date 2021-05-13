// ------------------------------------------------------ //
//   R2DatagramClient
// ------------------------------------------------------ //
import java.net.*;
import java.io.*;

public class R2DatagramClient {
    static  PrintStream   p = System.out;
    InputStreamReader stdin = new InputStreamReader(System.in);

    public static void main (String argv[]) {
        R2DatagramClient body = new R2DatagramClient();
        String hostName = "localhost";   // default
        int    portNo   = 61813;          // default
        InetAddress     address;
        String inMes = "send message";

        if( argv.length > 0 ) hostName = argv[0];
        if( argv.length > 1 ) portNo   = Integer.parseInt(argv[1]);
        p.println("your Server : " + hostName +
                ", port Number " + portNo);

        try {
            int  readLen   = 128;
            byte sendMsg[] = new byte[readLen];
            byte getMsg[]  = new byte[readLen];

            // (i1)
            address = InetAddress.getByName(hostName);
            p.println(" Given address = " + address);

            // (i2)
            p.println(" Given portNo = " + portNo);

            // (i3)
            while(inMes.indexOf("quit") == -1) {
                p.println("Send Message ?");
                inMes   = body.readStringFrom(body.stdin);
                p.println(" Given inMessage = " + inMes);
                sendMsg = inMes.getBytes();
                p.write(sendMsg, 0, sendMsg.length);
                // p.println(".");

                // (c1)
                DatagramSocket dataSocket =  new DatagramSocket();
                p.println("Ok1 : make new DatagramSocket");

                // (c2)
                DatagramPacket sendPacket =
                        new DatagramPacket(sendMsg, sendMsg.length, address, portNo);
                p.println("Ok2 : make new DatagramPacket");

                dataSocket.send(sendPacket);

                // (c4)
                DatagramPacket getPacket =  new DatagramPacket(getMsg, readLen);
                dataSocket.receive(getPacket);

                // output
                int packetln = getPacket.getLength();
                String data = new String(getPacket.getData(), 0, packetln);
                // byte[] to String
                p.println("from Server(" + packetln + ") " + data);
            }
            p.println("end of communication at quit");

        }
        catch (UnknownHostException uhe) {
            p.println("UnknownHostException: " + uhe.getMessage());
        }
        catch (IOException ioe) {
            p.println("IOException: " + ioe.getMessage()) ;
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
                tmp = tmp + ch;         // any other character
            }
            p.println("....");
            p.println("given String is  = " + tmp);
        }   // end of try
        catch(IOException e) { }

        return(tmp); // return current command or line
    }
}
