import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class ItemQuoteDecoderBin implements ItemQuoteDecoder, ItemQuoteBinConst {

  private String encoding;  // Character encoding

  public ItemQuoteDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public ItemQuoteDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  //public ItemQuote decode(InputStream wire) throws IOException {
  public ItemQuote decode(ByteArrayInputStream wire) throws IOException {
    boolean discounted, inStock;
    DataInputStream src = new DataInputStream(wire);
    long itemNumber = src.readLong();
    int quantity  = src.readInt();
    int unitPrice = src.readInt();
    byte flags    = src.readByte();
    int stringLength = src.read(); // Returns an unsigned byte as an int
    if (stringLength == -1)
      throw new EOFException();
    byte[] stringBuf = new byte[stringLength];
    src.readFully(stringBuf);
    String itemDesc = new String(stringBuf, encoding);
    return new ItemQuote(itemNumber,itemDesc, quantity, unitPrice,
      ((flags & DISCOUNT_FLAG) == DISCOUNT_FLAG),
      ((flags & IN_STOCK_FLAG) == IN_STOCK_FLAG));
  }

  public ItemQuote decode(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
