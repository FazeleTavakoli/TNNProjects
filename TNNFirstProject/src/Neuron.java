/**
 * Created by fazeletavakoli on 10/21/17.
 */
import java.util.Arrays;
import java.util.Random;

public class Neuron {
    private Random rand = new Random();
    private  double bias = 0;
    private int inputArray[][];
    private double weights [];
    private int predictedOutputArray[];
    private int correctOutputArray[]; //outputs that are provided by teacher
    static int maxIteration = 100;
    static double learningRate = 0.1;

    public void inputGenerator(int m, int n){
        inputArray = new int[n][m];
        weights = new double[m];
        correctOutputArray = new int[n];
        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                int inputValue = rand.nextInt(2);
                inputArray[i][j] = inputValue;
            }
        }
        //generating random weights and a bias
        bias = rand.nextDouble()-0.5;
        for (int i=0; i<m; i++){
            double weight = rand.nextDouble() -0.5;
            weights[i] = weight;
        }
        //generating correct outputs
        for (int i=0; i<n; i++){
            correctOutputArray[i] = rand.nextInt(2);
        }
    }

    //this function computes predicted outputs
    public void calculateOutput(int m, int n){
        predictedOutputArray = new int[n];
        for (int i=0; i<n; i++){
            double weightedSum =0.0;
            for (int j=0; j<m; j++) {
                weightedSum += inputArray[i][j]*weights[j];
            }
            weightedSum += bias;
            if (weightedSum <= 0)
                predictedOutputArray[i] = 0;
            else
                predictedOutputArray[i] = 1;
        }
    }

    //this function computes errors and try to modify weights and the bias
    public void trainer(int m,int n) {
        int counter = 0; //the number of times the following loop is done
        double error = 0;
        do {
            error = 0;
            for (int i = 0; i < n; i++) {
                error = Math.pow((correctOutputArray[i] - predictedOutputArray[i]), 2);
                for (int j = 0; j < m; j++) {
                    weights[j] += learningRate * error * inputArray[i][j];
                }
                bias += learningRate * error * 1;
            }
            counter++;
        } while (error != 0 && counter < maxIteration);

    }

    public void resultPrinter(){
        System.out.println(Arrays.toString(weights));
        System.out.println(bias);

    }




}
