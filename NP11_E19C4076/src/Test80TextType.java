// p.80 test Text type Encoder and Decoder
// run as >java Test80TextType 20201026,ItemQuoteFramer,101,8080,true,true

import java.io.*;

public class Test80TextType implements ItemQuoteTextConst { 
//extends ItemQuote
//implements ItemQuote,  Framer, ItemQuoteTextConst, 
//ItemQuoteEncoderText,  ItemQuoteDecoderText {


  public static void main(String[] args) throws Exception, IOException {
    String inFields = "20201026,ItemQuoteFramer,101,8080,true,true"; // default
    byte[] sendByte = new byte[MAX_WIRE_LENGTH];
    ItemQuoteEncoderText testEnc = new ItemQuoteEncoderText();
    ItemQuoteDecoderText testDec = new ItemQuoteDecoderText();

    if(args.length == 1) inFields = args[0]; // given as running parameter
 // ItemQuote sample = new ItemQuote(inFields); // make sample ItemQuote
    ItemQuote sample = new ItemQuote(20201026, 
                           "p79:sample item format", 101, 8080, true, true);
    System.out.println("in: given sample ItemQuote data::");
    String oneItem = sample.toString();
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

    System.out.println("end of Test80TextType ItemQuote > byte[] > ItemQuote");
  }
}

