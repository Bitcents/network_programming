import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.time.LocalDateTime;
import java.util.Date;

public class UDPEchoServerTime {

    private static final int ECHOMAX = 255; // Maximum size of echo datagram

    public static void main(String[] args) throws IOException {

        if (args.length != 1)  // Test for correct argument list
            throw new IllegalArgumentException("Parameter(s): <Port>");

        System.out.println("Datagram�� UTP �Ŏ󂯎��܂�");
        System.out.println("UTP: Unshielded Twist Pair �P�[�u��");

        int servPort = Integer.parseInt(args[0]);
        System.out.println("Listening on port: " + servPort
                            + "\nHost Address: " + InetAddress.getLocalHost() + "\n\n");  // for debugging purposes
        DatagramSocket socket = new DatagramSocket(servPort);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

        for (;;) {  // Run forever, receiving and echoing datagrams
            socket.receive(packet);	 // Receive packet from client

            String packetAddress = packet.getAddress().getHostAddress();
            int    packetPort    = packet.getPort();        // Storing these as variables for later use

            System.out.println("Handling client at " +      // Just some meta information
                    packetAddress + " on port " + packetPort);
            String message = new String(packet.getData()).trim();

            System.out.println("Message from remote Client: " + message);

            if(message.equals("getDate")){                 // Use String.equals to compare strings in java
                message = LocalDateTime.now().toString();
            }
            System.out.println("Generated response: " + message);
            byte[] bytesToSend = message.getBytes();       // Creating new packet to wrap the message in
            DatagramPacket responsePacket = new DatagramPacket(bytesToSend,  // Must use client address and port number
                    bytesToSend.length, InetAddress.getByName(packetAddress), packetPort);

            socket.send(responsePacket);	 // Send back the same message to client
            packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
        }
        /* NOT REACHED */
    }
}


/* 作成者：サリム・シディック・ハビブ
　　学籍番号：E19C4076
  　コメント等を母語の英語で書いています。日本語で書く必要があれば教えて頂ければ幸いです。
  　説明不足だとお考えでしたら一声を頂ければできる限り修正致します。
 */