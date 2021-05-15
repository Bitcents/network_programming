// HelloIntf
import java.rmi.*;

public interface HelloIntf extends Remote {

    public String sayHello(String RecvMess)
            throws java.rmi.RemoteException;

}
