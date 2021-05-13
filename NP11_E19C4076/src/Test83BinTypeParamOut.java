// p.83 test Binary type Encoder and Decoder 
// run as >java Test83BinTypeParamOut 8888 Binary_Item_Quote_Name 101 2020 true true
// and print encoded binary data as String
import java.io.*;
import java.lang.String;

public class Test83BinTypeParamOut 
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
       String oneItem  = sample.toString();
       System.out.println(oneItem); // print sample as String
    }
    else { 
       System.out.println("give me ParamOuteters as follows");
       System.out.println("itemNubmer description unitPrice Quantity discount stock");
       return;
    } 

    // encode given ItemQuote and print result byte[]
    sendByte = testEnc.encode(sample); // encode sample into byte[]
    ByteArrayInputStream basrc = new ByteArrayInputStream(sendByte); // ++ 
    DataInputStream src = new DataInputStream(basrc); // ++

    String itemNumber = String.valueOf(src.readLong());
    String quantity   = String.valueOf(src.readInt());
    String unitPrice  = String.valueOf(src.readInt());
    String flags = String.valueOf(src.readByte());
    int    intLength = src.read();
    String strLength = String.valueOf(intLength);
    byte stringBuf[] = new byte[intLength];
    src.readFully(stringBuf); // changed
    //String itemDesc = new String(stringBuf, encoding);
    String itemDesc   = new String(stringBuf); // changed
    String sendString = new String(itemNumber+" "+quantity+" "+unitPrice+" "+
                                   flags+" "+intLength+" "+itemDesc);
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

