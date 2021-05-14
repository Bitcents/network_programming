import java.util.*; // for Properties
  
class ListProperties {

  public static void main(String args[]) {
    System.getProperties().list(System.out);  // Print all System properties
    System.out.println("\nFavorite Color: " + 
      System.getProperty("user.favoritecolor", "None"));
					      // Print favorite color property
  }
}
