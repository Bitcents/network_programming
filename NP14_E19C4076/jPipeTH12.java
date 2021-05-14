import java.io.*;
/*
    プログラムのコメントや説明等をいつも通り英語で書いています。
    前も述べたと思いますが、英語ではなく日本語で説明する必要がありましたら、
    是非教えてください。
 */
// create 2=threads and combine pipe between them
class jPipeTH12 extends Thread { 
        static jPipeTH12Receiver rTH1;   // name of Thread 1 
        static jPipeTH12Receiver rTH2;   // name of Thread 2
    public static void main ( String[] args ) {
        rTH1 = new jPipeTH12Receiver(1); // create Thread 1
        rTH2 = new jPipeTH12Receiver(2); // create Thread 2

        rTH1.start(); // execute run() 1
        rTH2.start(); // execute run() 2

    }
}

class jPipeTH12Receiver extends jPipeTH12  { 
    static  PrintStream   p = System.out;
    PipedInputStream    pin = new PipedInputStream();
    int id;  // Thread id number

    jPipeTH12Receiver(int ti) { // constructor
        id = ti;                // set id number
        p.println( "(r-1) constructed  jPipeTH12Receiver " + id);
    }

    /*
        The getStream method took an integer ti as an argument, but this was never used inside the method.
        As it was not clear what the purpose of the argument, I tried removing and ran the program again.
        As expected there was no change in the result, so I got rid of the integer argument.
        There were no requirements in the assignment that needed the use of ti.
     */
    PrintStream getStream(jPipeTH12Receiver rTh2 )
                                     throws IOException {
        p.println( "(r-2) getStream of jPipeTH12Receiver " + id);
	 return new PrintStream( new PipedOutputStream(rTh2.pin) ); // pout 1
    }
    public void run() {
        InputStreamReader din = new InputStreamReader(pin);
        int eflag = 0; // message end flag
        p.println( "(r-3) start     of jPipeTH12Receiver in run() " + id);


        /*
            The requirement of the assignment was to enable two-way communication between the two threads.
            There are several implementations that I could think of:
                1) Use a request, response model
                2) Usage of interrupts, and the thread wait method
                3) What I have actually used here, where the thread communicate in a given order
         */


        if(id == 1) {  // case of Thread 1
          try {  // write into pipe by Thread 1
            PrintStream pout = getStream( rTH2);
            pout.println( "send from id = " + id + " 2-messages and ! end");
            pout.println( "(send-1) jPipeTH12 started with pipeoutput...");
            pout.println( "(send-2) send message to output stream .... !");
            pout.println( "(send-end)");
            pout.println( "#" );  // end mark of message

              sendMessage(din, eflag);


          }
          catch ( IOException e ) {
            p.println( "(r-e) IOException in jPipeTH12Receiver " + id);
          }
	 }

        else {  // case of Thread 2

          try {
              sendMessage(din, eflag);

              PrintStream pout = getStream( rTH1);
              pout.println( "send from id = " + id + " 2-messages and ! end");
              pout.println( "(send-1) jPipeTH12 started with pipeoutput...");
              pout.println( "(send-2) send message to output stream .... !");
              pout.println( "(send-end)");
              pout.println( "#" );
          }
          catch ( IOException e ) {
            p.println( "(r-e) IOException in jPipeTH12Receiver " + id);
          }
        }

        p.println( "(r-4) end of readLine in jPipeTH12Receiver " + id);
    }

    private void sendMessage(InputStreamReader din, int eflag) throws EOFException {
        String s;
        while (eflag == 0){
            s = readStringFrom(din);   // get one line from din
            if(s.equals("")) continue; // for empty case
            p.println(">(" + id + ") " + s); // id 2
            if(s.equals("(send-end)")) eflag = 1;
            if(s.equals("EndOfFile"))  eflag = 2;
        }
    }


    public String readStringFrom(InputStreamReader ir) {
        StringBuilder tmp = new StringBuilder();
        char   ch;
        int    ic;

        /*
            Using the StringBuilder class allowed for a much cleaner implementation
            of the readString method. Functionality has not changed.
            There was no reason to throw an IOException as none were involved.
            Still, since we are working with threads and pipes, Exception was used instead,
            as a sort of catch-all. But that may be deemed unnecessary though.
         */

        try {
            while(true) {
                ic = ir.read();         // next int-code
                if(ic == (int)('#') ) { // end mark 
                // if(ic == -1) {       // EOF
                // p.println("(e) -1 as end of file ");
                   return("EndOfFile"); // at EOF : -1
                }
                ch = (char) ic;         // int to char
                if(ch == '\n') break;   // for new line
                // p.print(" " + ch + ",");
                tmp.append(ch);         // any other character
            }


        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return(tmp.toString());                    // return current command or line
    }
}
