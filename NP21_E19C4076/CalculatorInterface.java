import java.rmi.Remote;

public interface CalculatorInterface extends Remote {
    public double Arithmetic(String op, double a, double b) throws java.rmi.RemoteException;
}
