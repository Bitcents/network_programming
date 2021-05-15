import java.net.*;      // for Socket, ServerSocket, and InetAddress
import java.io.*;       // for IOException and Input/OutputStream
import java.util.zip.*; // for GZIPOutputStream

public class CompressServerRev { // +modified version

    public static final int BUFSIZE = 1024; // Size of read buffer

    public static void main(String[] args) throws IOException {

        if (args.length != 1) // Test for correct # of args as port number
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]); // Server port

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);

        byte[] buffer = new byte[BUFSIZE]; // Allocate read/write buffer
        int bytesRead;                     // Number of bytes read
        for (;;) { // Run forever, accepting and servicing connections
            // Wait for client to connect, then create a new Socket
            Socket clntSock = servSock.accept();

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " + clntSock.getPort());

            // Get the input and output streams from socket
            InputStream in = clntSock.getInputStream();
            System.out.println("get : " + in); // InputStream number

            String instring = inputStreamToString(in); // +get data from client
            System.out.println("String form : " );
            System.out.println( instring);  // +String form OK

            // convert into compressed data form
            GZIPOutputStream out  = new GZIPOutputStream(clntSock.getOutputStream());
            FileOutputStream fOut = new FileOutputStream("tmp.txt.gz");
            // ++for store Server side

            // Receive until client closes connection, indicated by -1 return
            while ((bytesRead = in.read(buffer)) != -1) { // check input connection
                out.write(buffer, 0, bytesRead);
                System.out.println("in::read buffer ::" + buffer);
                // +Reading progress indicator
                System.out.println("in::bytes length::" + bytesRead);
                // +Reading progress indicator
                fOut.write(buffer, 0, bytesRead); // ++store Server side
            }
            // String to byte[]
            buffer = instring.getBytes();  // string to byte[]
            bytesRead = instring.length(); // length of input
            System.out.println("out:read buffer byte[] ::" + buffer);
            // +Reading progress indicator
            System.out.println("out:bytes length ::" + bytesRead);
            // +Reading progress indicator
            out.write(buffer, 0, bytesRead); // send to client as compressed stream
            fOut.write(buffer, 0, bytesRead); // ++store server side

            out.close();      // Flush bytes from GZIPOutputStream
            clntSock.close(); // Close socket streams
        }
        /* NOT REACHED */
    }

    // ++ for String form data
    public static String inputStreamToString(InputStream in)
            throws IOException{
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(in, "UTF-8"/* •¶ŽšƒR[ƒhŽw’è */));
        StringBuffer buf = new StringBuffer();
        String str;
        while ((str = reader.readLine()) != null) {
            buf.append(str);
            buf.append("\n");
        }
        return buf.toString();
    }

}
