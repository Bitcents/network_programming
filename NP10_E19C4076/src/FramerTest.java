/*
 * Written by: Siddik Habib Salim
 * Student id: E19C4076
 * Class: E2P1
 * The explanations are written in English, but can be provided in Japanese as well if required.
 * Please contanct me at siddik21salim@gmail.com if any corrections are required
 * */


public class FramerTest {
    public static void main(String[] args){
        // Check for argument list. Must provide two arguments: the input string and the delimiter string
        if (args.length < 1){
            throw new IllegalArgumentException("Must provide: 1) string data to be tokenized " +
                                               "2) the delimiter character (optional)");
        }

        // Getting the arguments and putting them into variables
        String inputData = args[0];
        // By default the delimiter character is set to ","
        // The program will use a user-defined character if provided
        String delimiterChar = (args.length == 2)? args[1] : ",";

        // This is where we tokenize the data. The input String is split into separate bits and stored in an array
        String[] tokenizedData = inputData.split(delimiterChar);

        // And then we just print the elements of the array. Very simple.
        for(String data : tokenizedData){
            System.out.println("Got new token ("+ data.length() + ") " + data);
        }
    }
}
