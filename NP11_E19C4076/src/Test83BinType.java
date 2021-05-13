// p.83 test Binary type Encoder and Decoder
// run as >java Test83BinType 

import java.io.*;

public class Test83BinType 
       implements ItemQuoteBinConst { 

  public static void main(String[] args) throws Exception, IOException {
    String inFields = "12345678L,ItemQuoteFramer,83,8486,true,true"; // default
    byte[] sendByte = new byte[MAX_WIRE_LENGTH];
    ItemQuoteEncoderBin testEnc = new ItemQuoteEncoderBin();
    ItemQuoteDecoderBin testDec = new ItemQuoteDecoderBin();

    if(args.length == 1) inFields = args[0]; // given as running parameter
    // print input ItemQuote
    ItemQuote sample = new ItemQuote(20201101L, 
                       "p83:sample Binary format", 66, 10800, true, true);
    System.out.println("in: given sample ItemQuote data::");
    String oneItem   = sample.toString();
    System.out.println(oneItem); // print sample as String

    // encode given ItemQuote and print result byte[]
    sendByte = testEnc.encode(sample); // encode sample into byte[]
    String sendString = new String(sendByte, "UTF-8"); // into String
    System.out.println("send Byte data::");
    System.out.println(sendString);

    // decode result byte[] into ItemQuote and print it as String & ItemQuote
    ByteArrayInputStream bais = new ByteArrayInputStream(sendByte); 
    ItemQuote recItem = testDec.decode(bais); // decode sendByte
    String recString  = recItem.toString();
    System.out.println("receive ItemQuote as Decoder result::");
    System.out.println(recString); // print sample as String

    System.out.println("end of Test83BinType ItemQuote > byte[] > ItemQuote");
  }
}

