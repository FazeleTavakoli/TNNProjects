import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by fazeletavakoli on 10/30/17.
 */
public class Neuron {
    private int layers_num = 0; //number of layers
    private Random rand = new Random();
    private double networkinputArray[];
    private double weights[][];
    private List<double[][]> weights_list = new ArrayList<>();
    private double outputs[]; //the number of outputs array should be equal to (the number of layers - 1)
    private List<double[]> outputs_list = new ArrayList<>();
    private int generalLoop =0; //based on the part of the task we want to execute, we should loop through the program just one time
                                 //for running the Multi Layer Perzeptron part or 1000 times to run Backpropagation of Error part
    private boolean mustExecutedAgain = false; //determines which parts of the code should be executed again and which parts shouldn't
                                                //when we want to loop through the code for several times in Implementing Backpropagation of Error

    //fields just for backpropagation alg.
    private double[] correctOutput; //just used in implementation of backpropagation of error
    private double deltas[]; //the number of outputs array should be equal to (the number of layers - 1)
    private List<double[]> deltas_list = new ArrayList<>();
    private List<double[]> networkinputArray_list = new ArrayList<>(); //for storing input parts of patterns
    private List<double[]> correctOutput_list = new ArrayList<>(); //for storing output parts of patterns

    public void networkGenerator(int option) {

        //Generating random values for the number of layers, neurons per layer and weights.
        layers_num = rand.nextInt(2) + 3;
        int neurons_num_per_layer_array[][] = new int[layers_num][];

        if(option == 1){
            generalLoop = 1;
        }
        else{
            generalLoop = 350;
        }

        for (int z=0; z<generalLoop; z++) {
            outputs_list = new ArrayList<>();
            // weights_list = new ArrayList<>();
            //************Implement a Multi Layer Perzeptron***********
            if (!mustExecutedAgain) {
                if (option == 1) {
                    for (int i = 0; i < layers_num; i++) {
                        int neurons_num_per_layer = rand.nextInt(100) + 1; //maximum number of neurons per layer can be 100
                        neurons_num_per_layer_array[i] = new int[neurons_num_per_layer];
                    }
                    //generating random inputs of the network
                    inputGenerator(neurons_num_per_layer_array);
                }
                //************Implement Backpropagation of Error***********
                else {
                    neurons_num_per_layer_array[0] = new int[2];
                    for (int i = 1; i < layers_num - 1; i++) {
                        int neurons_num_per_layer = rand.nextInt(100) + 1; //maximum number of neurons per layer can be 100
                        neurons_num_per_layer_array[i] = new int[neurons_num_per_layer];
                    }
                    neurons_num_per_layer_array[layers_num - 1] = new int[1];

                    //generating random inputs of the network
                    inputReader();
                }
            }

            //generating random weights

            if (!mustExecutedAgain) {
                for (int i = 0; i < layers_num - 1; i++) {
                    weights = new double[neurons_num_per_layer_array[i].length][neurons_num_per_layer_array[i + 1].length];
                    for (int i1 = 0; i1 < neurons_num_per_layer_array[i].length; i1++) {
                        for (int i2 = 0; i2 < neurons_num_per_layer_array[i + 1].length; i2++) {
                            weights[i1][i2] = ((rand.nextDouble()) * 4) - 2; //producing random weights between -2 and +2
                        }
                    }
                    weights_list.add(weights);
                }
            }

            //computing output. we compute output of each neuron in each layer
            boolean outputProduced = false;
            for (int i = 0; i < weights_list.size(); i++) {
                boolean firstIteration = true;

                //initializing input array of each layer regarding the position of the layer,
                // ie. if it's the first layer, current input is the main network input, otherwise the current input
                //is the output of last layer
                double currentInputArray[];
                if (!outputProduced) {
                    if (option == 1) {
                        currentInputArray = networkinputArray.clone();
                    } else {
                        currentInputArray = networkinputArray_list.get(z).clone();
                    }
                } else {

                    int size = outputs_list.get(outputs_list.size() - 1).length;
                    currentInputArray = Arrays.copyOf(outputs_list.get(outputs_list.size() - 1), size);
                }

                //computing weighted sum
                double current_weights_array[][] = weights_list.get(i);
                for (int j = 0; j < current_weights_array.length; j++) {
                    for (int k = 0; k < current_weights_array[j].length; k++) {
                        if (firstIteration) {
                            firstIteration = false;
                            outputs = new double[current_weights_array[j].length];
                            for (int k1 = 0; k1 < outputs.length; k1++) {
                                double biasWeight = (rand.nextDouble() * 4) - 2;
                                outputs[k1] = biasWeight; //initializing outputs array of each layer with bias valuse
                            }
                        }
                        //generating outputs array for each layer by computing the weighted sum.
                        try {
                            outputs[k] += (currentInputArray[j]) * current_weights_array[j][k];
                        } catch (Exception e) {

                        }
                    }
                }
                outputProduced = true;
                //applying desired activation function (tanh or logistic or identity) on the output array of a specific layer.
                //here we choose one of the above activation functions randomly
                int active_randomeNumber = rand.nextInt(3);
                switch (active_randomeNumber) {
                    case 0:
                        for (int m = 0; m < outputs.length; m++) {
                            outputs[m] = Math.tanh(outputs[m]);
                        }
                        break;
                    case 1:
                        for (int m = 0; m < outputs.length; m++) {
                            outputs[m] = 1 / (1 + (Math.exp(-outputs[m])));
                        }
                        break;
                    case 2: {

                    }
                    break;
                }

                outputs_list.add(outputs);
            }
            System.out.println("The obtained output is: "+Arrays.toString(outputs_list.get(outputs_list.size() - 1)));

            //for backpropagation we backtrack and compute error
            if (option == 2) {
                double lastOutput[] = outputs_list.get(outputs_list.size() - 1);
                double difference[] = new double[1];
                difference[0] = Math.abs((lastOutput[0] - correctOutput[0]));

                boolean outputProduced_inverse = false;
                for (int i = weights_list.size() - 1; i > -1; i--) {
                    boolean firstIteration_inverse = true;

                    //initializing input array of each layer regarding the position of the layer,
                    // ie. if it's the first layer, current input is the main network input, otherwise the current input
                    //is the output of last layer
                    double currentInputArray[];
                    if (!outputProduced_inverse) {
                        currentInputArray = difference.clone();
                    } else {
                        int size = deltas_list.get(deltas_list.size() - 1).length;
                        currentInputArray = Arrays.copyOf(deltas_list.get(deltas_list.size() - 1), size);
                    }
                    //computing deltas
                    double current_weights_array[][] = weights_list.get(i);
                    for (int j = 0; j < current_weights_array.length; j++) {
                        for (int k = 0; k < current_weights_array[j].length; k++) {
                            if (firstIteration_inverse) {
                                firstIteration_inverse = false;
                                deltas = new double[current_weights_array[j].length];
                                //generating outputs array for each layer by computing the weighted sum.
                                deltas[k] += (currentInputArray[j]) * current_weights_array[j][k];
                            }
                        }
                        outputProduced_inverse = true;
                    }
                    deltas_list.add(deltas);
                }
                System.out.println("In the current learning step the error value is:"+ difference[0]);
            }
            mustExecutedAgain = true;

            if(option == 2) {
                //updating weights
                int deltaArrayIndex = layers_num - 2;
                for (int i = 0; i < layers_num - 1; i++) {
                    double currentDeltaArray[] = deltas_list.get(deltaArrayIndex);
                    weights = weights_list.get(i);
                    //weights = new double[neurons_num_per_layer_array[i].length][neurons_num_per_layer_array[i + 1].length];
                    for (int i1 = 0; i1 < neurons_num_per_layer_array[i].length; i1++) {
                        for (int i2 = 0; i2 < neurons_num_per_layer_array[i + 1].length; i2++) {
                            weights[i1][i2] += (0.01) * currentDeltaArray[deltaArrayIndex]; //producing random weights between -2 and +2
                        }
                    }
                    weights_list.set(i, weights);
                    deltaArrayIndex--;
                }
            }
        }


            for (int i = 0; i < weights_list.size(); i++) {
                System.out.println("The updated weights between layer " + (i + 1) + " and " + (i + 2) + " are: ");
                weights = weights_list.get(i);
                for (int i1 = 0; i1 < weights.length; i1++) {
                    for (int i2 = 0; i2 < weights[i1].length; i2++)
                        System.out.print(weights[i1][i2]+" " );
                }
                System.out.println();
            }


        /*if(option == 2) {
            for (int i = 0; i < weights_list.size(); i++) {
                System.out.println("The updated weights between layer " + (i + 1) + " and " + (i + 2) + " are: ");
                weights = weights_list.get(i);
                for (int i1 = 0; i1 < weights.length; i1++) {
                    for (int i2 = 0; i2 < weights[i1].length; i2++)
                        System.out.print(weights[i1][i2] + "\t");
                }
                System.out.println();
            }
        }*/

    }

    //this method generates inputs randomly
    public void inputGenerator(int neuronsNumber_perLayer[][]){
        //generating random inputs of the network
        networkinputArray = new double[neuronsNumber_perLayer[0].length];
        for (int i = 0; i < networkinputArray.length; i++) {
            networkinputArray[i] = (double) rand.nextInt(2);
        }
    }

    //this method just reads the input from a specific file
    public void inputReader(){
        //reading training data from dat file
        String filePath = "/Users/fazeletavakoli/IdeaProjects/TNNProjects/TNNSecondProject_maven/training.txt";
        Spliter spliter = new Spliter();
        String splittedArray[];
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\uFEFF]", "");
                line = line.replaceAll("[\uFFFD]","");
                line = line.trim();
                if(!line.equals("")) {
                    if (line.charAt(0) != '#') {
                        networkinputArray = new double[2]; //our .dat file has a two dimentional inputs and a one dimentional output for each pattern
                        splittedArray = spliter.sentenceSplitter(line);
                        for (int i = 0; i < 3; i++) {
                            splittedArray[i] = splittedArray[i].trim();
                            if (i != 2) {
                                networkinputArray[i] = Double.parseDouble(splittedArray[i]);
                            } else {
                                correctOutput = new double[1];
                                correctOutput[0] = Double.parseDouble(splittedArray[i]);
                            }
                        }
                        networkinputArray_list.add(networkinputArray);
                        correctOutput_list.add(correctOutput);
                    }
                }
            }

        } catch (IOException e) {
        }
    }







}
