// HelloServer :
import java.rmi.*;
import java.rmi.server.*;
import java.net.*;

public class HelloServer extends UnicastRemoteObject implements HelloIntf {

    public HelloServer() throws RemoteException { super(); }

    public String sayHello(String RecvMess) throws RemoteException {
        System.out.println("Receive Message :  " + RecvMess);
        return ("for Your Message = " + RecvMess );
    }
    public static void main(String args[]) {

        try {
            InetAddress address = InetAddress.getLocalHost();

            HelloServer h = new HelloServer();
            Naming.rebind("hello", h); // bind keyword
            System.out.println("Fine ! Hello Server is ready on      " +
                    address.getHostName());
            System.out.println("Please call me by : java HelloClient " +
                    address.getHostName());
        }
        catch (RemoteException re) {
            System.out.println("Exception in HelloServer.main: " + re);
        }
        catch (MalformedURLException e) {
            System.out.println("MalformedURLException in HelloServer.main: " + e);
        }
        catch (java.net.UnknownHostException e) {
            System.out.println("Unknown Host Name in HelloServer.main: " + e);
        }
    }
}
