
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;


public class CalculatorServer extends UnicastRemoteObject implements CalculatorInterface {

    public CalculatorServer() throws RemoteException {}

    @Override
    public double Arithmetic(String op, double a, double b){
        double result = 0;
        if(op.equals("add")) {
            result = a+b;
        } else if (op.equals("sub")){
            result = a - b;
        } else if (op.equals("mul")){
            result = a * b;
        } else if (op.equals("div")){
            result = a / b;
        }
        return result;
    }
    public static void main(String args[]) {

            try {
                CalculatorServer obj = new CalculatorServer();
                System.out.println("created server");

                Naming.rebind("arithmetic", obj);
                System.out.println("exported object");

                System.err.println("Server ready");
            }
            catch (RemoteException re) {
                System.out.println("Exception in Server: " + re);
            } catch (Exception e){
                e.printStackTrace();
            }

    }
}
