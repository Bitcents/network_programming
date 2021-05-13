import java.io.*;  // for InputStream and IOException
import java.net.*; // for DatagramPacket

public interface ItemQuoteDecoder {
// ItemQuote decode(InputStream source) throws IOException;
   ItemQuote decode(ByteArrayInputStream source) throws IOException;
   ItemQuote decode(DatagramPacket packet)       throws IOException;
}
