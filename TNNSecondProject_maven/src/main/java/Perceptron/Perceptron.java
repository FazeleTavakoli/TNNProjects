import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by fazeletavakoli on 10/30/17.
 */
public class Perceptron {
    public static void main(String args[]){

        Random rand = new Random();
        Neuron neuron = new Neuron();
        System.out.println("which part of the task you want to execute? " +
                "(pls enter 1 for Implement a Multi Layer Perzeptron " +
                "or 2 for Implement Backpropagation of Error)");
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        neuron.networkGenerator(i);




    }
}
