import java.util.*; // for Collection

public interface Logger {
  public void writeEntry(Collection entry); // Write list of lines
  public void writeEntry(String entry);     // Write single line
}
