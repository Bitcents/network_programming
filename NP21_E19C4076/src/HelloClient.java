import java.rmi.*;

public class HelloClient {

    public static void main(String args[]) {
        System.out.println("Start Hellow Client to " + args[0]);
        // System.setSecurityManager(new RMISecurityManager());
        try {
            //HelloIntf h = (HelloIntf) Naming.lookup("hello");
            HelloIntf h = (HelloIntf) Naming.lookup("//" + args[0] + "/hello");
            System.out.println("link to server " + args[0]);
            String message = h.sayHello("How are You " + args[0] +"!");
            // remote method
            System.out.println("HelloClient receive : " + message);
        }
        catch (Exception e) {
            System.out.println("Exception in main: " + e);
        }
    }
}
