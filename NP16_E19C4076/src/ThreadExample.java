public class ThreadExample implements Runnable {
  
  private String greeting; // Message to print to console

  public ThreadExample(String greeting) {
    this.greeting = greeting;
  }

  public void run() { // run of 4 Threads
    for (int i = 0; i < 3; i++) {
      System.out.println(Thread.currentThread().getName() + ":  " + greeting);
      try {
        Thread.sleep((long)(Math.random() * 100)); // Sleep 0 to 100 milliseconds
      } catch (InterruptedException e) {}  // Will not happen
    }
  }
  
  public static void main(String[] args) {
    new Thread(new ThreadExample("Hello")).start();
    new Thread(new ThreadExample("Aloha")).start();
    new Thread(new ThreadExample("Ciao")).start();
    new Thread(new ThreadExample("‚±‚ñ‚É‚¿‚Í! ‚¨Œ³‹C‚Å‚·‚©")).start();

    System.out.println("End of parent after 4-threads create!!");
  }
}