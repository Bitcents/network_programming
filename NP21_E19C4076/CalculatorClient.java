
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {
    public static void main(String args[]) {

        if(args.length != 4){
           throw new IllegalArgumentException(
                   "Must provide the following arguments: " +
                   "hostname(String), operation(String), " +
                   "operand1(Number), operand2(Number)"
           );
        }

        String op = args[1];
        double a = Double.parseDouble(args[2]);
        double b = Double.parseDouble(args[3]);

        System.out.println("Start Client to " + args[0]);
        // System.setSecurityManager(new RMISecurityManager());
        try {
            CalculatorInterface h = (CalculatorInterface) Naming.lookup( "arithmetic");
            System.out.println("link to server " + args[0]);
            double result = h.Arithmetic(op, a, b);
            System.out.println("CalculatorClient receive : " + result);
        }
        catch (Exception e) {
            System.out.println("Exception in main: " + e);
        }
    }
}
