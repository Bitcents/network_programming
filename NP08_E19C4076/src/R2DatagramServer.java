// R2DatagramServer : on wonder71
import java.net.*;
import java.io.*;

public class R2DatagramServer {
    static  PrintStream   p = System.out;
    InputStreamReader stdin = new InputStreamReader(System.in);

    public static void main (String argv[]) {
        try {
            int  readLen = 128;
            byte recvData[] = new byte[readLen];
            byte sendData[] = new byte[readLen];
            InetAddress address;
            int         portNo;
            int         packetln;

            // (s1)
            DatagramSocket dataSocket = new DatagramSocket();
            // ....
            portNo = dataSocket.getLocalPort();

            p.println("1-p " + portNo);         // sout-1

            while(true) {
                // (s2)
                DatagramPacket getPacket = new DatagramPacket(recvData, readLen);
                // (s3)
                dataSocket.receive(getPacket);
                address = getPacket.getAddress();
                portNo  = getPacket.getPort();
                p.println("2-receive message from : " + address); // sout-2
                p.println("3-and its port number  : " + portNo);  // sout-3
                packetln = getPacket.getLength();
                String data = new String(getPacket.getData(), 0, packetln);
                // byte[] to String
                p.println("4-get message(" + packetln + ") " + data); // sout-4

                data     = data.toUpperCase();
                sendData = data.getBytes();   // String to byte[]
                // data.getBytes(0, data.length(), sendData, 0);
                p.println("5-a " + address);   // sout-5
                p.println("6-p " + portNo);    // sout-6
                p.println("7-m " + data);      // sout-7

                // (s4)
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, packetln, address, portNo);
                dataSocket.send(sendPacket);

                p.println("end of one receive and send responce");
            } // repeat forever

        } catch (SocketException se) {
            p.println("SocketException: " + se.getMessage());
        } catch (IOException ioe) {
            p.println("IOException: " + ioe.getMessage());
        }
    }
}
