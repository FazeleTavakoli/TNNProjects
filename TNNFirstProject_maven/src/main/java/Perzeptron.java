import java.util.Random;
import java.util.Scanner;

/**
 * Created by fazeletavakoli on 10/21/17.
 */
public class Perzeptron {
    public static void main(String args[]){
        Random rand = new Random();
        Neuron neuron = new Neuron();
        

        //generating random input and output dimensions
        int inputDimension = rand.nextInt(101) + 1;
        int outputDimension = rand.nextInt(30) + 1; //number of instances
        neuron.inputGenerator(inputDimension,outputDimension);
        neuron.calculateOutput(inputDimension,outputDimension);

        //training posibility
        int p = rand.nextInt(200) + 1; //p is the number of training patterns
        int m = rand.nextInt(101) + 1; //m is the input dimension

        //generating a set of p training-patterns and write them in a file
        neuron.inputGenerator(m,p);
        neuron.calculateOutput(m,p);
        System.out.println("The weights and the bias before training the perzeptron are:");
        neuron.resultPrinter();
        neuron.trainer(m,p);
        System.out.println("The weights and the bias after training the perzeptron are:");
        neuron.resultPrinter();


    }
}
