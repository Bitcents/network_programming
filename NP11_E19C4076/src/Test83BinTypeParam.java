// p.83 test Binary type Encoder and Decoder
// run as >java Test83BinTypeParam 88 Binary_Item_Quote_Name 89 90 true true

import java.io.*;
import java.lang.String;

public class Test83BinTypeParam 
       implements ItemQuoteBinConst { 

  public static void main(String[] args) throws Exception, IOException {
    byte[] sendByte = new byte[MAX_WIRE_LENGTH];
    ItemQuoteEncoderBin testEnc = new ItemQuoteEncoderBin();
    ItemQuoteDecoderBin testDec = new ItemQuoteDecoderBin();
    ItemQuote sample; 

    if(args.length == 6) {
       sample = new ItemQuote(Long.parseLong(args[0]), args[1], 
                          Integer.parseInt(args[2]), Integer.parseInt(args[3]), 
                          Boolean.valueOf(args[4]), Boolean.valueOf(args[5]) );
       // print input ItemQuote
       System.out.println("in: given sample ItemQuote data::");
       String oneItem = sample.toString();
       System.out.println(oneItem); // print sample as String
     }
    else { 
       System.out.println("give me parameters as follows");
       System.out.println("itemNubmer description unitPrice Quantity discount stock");
       return;
    } 

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

    System.out.println("end of Test83BinType ItemQuote > Binary[] > ItemQuote");
  }
}

