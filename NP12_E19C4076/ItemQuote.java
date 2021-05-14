public class ItemQuote {

  public long itemNumber;        // Item identification number
  public String itemDescription; // String description of item
  public int quantity;           // Number of items in quote (always >= 1)
  public int unitPrice;          // Price (in cents) per item
  public boolean discounted;     // Price reflect a discount?
  public boolean inStock;        // Item(s) ready to ship?

  public ItemQuote(long itemNumber, String itemDescription,
      int quantity, int unitPrice, boolean discounted, boolean inStock)  {
    this.itemNumber = itemNumber;
    this.itemDescription = itemDescription;
    this.quantity   = quantity;
    this.unitPrice  = unitPrice;
    this.discounted = discounted;
    this.inStock = inStock;
  }

  public String toString() {
    final String EOLN = java.lang.System.getProperty("line.separator");
    String value = "Item #    = " + itemNumber + EOLN +
                   "Item Desc = " + itemDescription + EOLN +
                   "Quantity  = " + quantity + EOLN +
                   "Price (each) = " + unitPrice + EOLN +
                   "Total Price  = " + (quantity * unitPrice);
    if (discounted)
      value += " (discounted)";
    if (inStock)
      value += EOLN + "In Stock ストックあり"     + EOLN;
    else
      value += EOLN + "Out of Stock ストックなし" + EOLN;
    return value;
  }
}
