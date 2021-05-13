// R1SocketServer.java
import java.net.*;
import java.io.*;

public class R1SocketServer {
    static  PrintStream   p = System.out;
    InputStreamReader stdin = new InputStreamReader(System.in);

    public static void main(String argv[]) {
        R1SocketServer body = new R1SocketServer();

        int             pNo = 4002;
        ServerSocket    server;
        Socket          client;
        PrintStream     toClient;

        if( argv.length > 0 ) pNo = Integer.parseInt(argv[0]);
        p.println("your port Number " + pNo);

        p.println("�Θb���J��Ԃ��܂� : quit �܂�");

        try {
            server = new ServerSocket(pNo);

            while (true) {
                client = server.accept();
                p.println("acceps    OK " + client);
                // (si1)
                InputStreamReader fromClient =
                        new InputStreamReader(client.getInputStream());
                // (so4)
                toClient = new PrintStream(
                        new BufferedOutputStream(client.getOutputStream()),true );
                p.println("IO-Stream OK " + fromClient + " & " + toClient);
                String getMsg = new String();
                String getRes = new String();

                p.println("++ when receive quit, then return QUIT ++");
                getRes = "init";
                int inner = 1;
                while ((getRes.indexOf("QUIT") == -1) && (inner == 1)) {
                    getMsg = body.readStringFrom(fromClient);
                    // (si1)
                    p.println("Get a Message from Client : "
                            + getMsg);   // (so2)
                    if (getMsg.equals("EndOfFile")) {
                        inner = 0;
                        continue;
                    }

                    // get Responce message and send to Client
                    getRes = body.readStringFrom(body.stdin);  // (si3)
                    toClient.println(getRes);                  // (so4)
                    if (getRes.equals("QUIT")) break;
                    if (getRes.equals("quit")) break;
                }
                client.close();
                //break;  // go out main loop
                continue; // loop for-ever as Server
            }
            //p.println("go out from try-loop with port " + pNo);
        }
        catch (IOException e) {
            p.println("IOException:  " + e.getMessage());
        }
        p.println("end of Server processing with port " + pNo);
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
                if(ch == '\0') break;   // for C-client
                // p.print(" " + ch + ",");
                tmp = tmp + ch;         // any other character
            }
            // p.println("...");
            // p.println("given String is  = " + tmp);
        }   // end of try
        catch(IOException e) { }

        return(tmp); // return current command or line
    }
}
