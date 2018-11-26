import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by fazeletavakoli on 11/13/17.
 */
public class Neuron {
    private Random rand = new Random();
    private double networkinputArray[];
    private double weights[];
    private List<double[]> weights_list = new ArrayList<>();
    private double outputs[]; //the number of outputs array should be equal to (the number of layers - 1)
    private List<double[]> outputs_list = new ArrayList<>();
    private List<double[]> networkinputArray_list = new ArrayList<>(); //for storing input parts of patterns
    private double[] correctOutput; //just used in implementation of backpropagation of error
    private List<double[]> correctOutput_list = new ArrayList<>(); //for storing output parts of patterns
    private List <double[]> centersArray_list = new ArrayList<>();
    private List <double[]> plotPoints = new ArrayList<>();



    public void networkGenerator() {
        int neurons_num_per_layer_array[] = new int[3];

        neurons_num_per_layer_array[0] = 2;
        neurons_num_per_layer_array[1] = rand.nextInt(5) + 1; //maximum number of neurons per layer can be 100
        neurons_num_per_layer_array[2] = 1;

        //generating random inputs of the network
        inputReader();


        //generating centers
        int centersNum = 10;  //"number of centers" we want to have in RBF layer
        int selectedPattern = 0;
        List<Integer> selectedPatterns_list = new ArrayList<>();
        for (int i = 0; i < centersNum; i++) {
            while(selectedPatterns_list.contains(selectedPattern)) {
                selectedPattern = rand.nextInt(350) + 1;
            }
                selectedPatterns_list.add(selectedPattern);

        }

        //note that selectedPatterns_list.size() equals to centersNum
        //centers are constant in all patterns
        for (int i = 0; i < selectedPatterns_list.size(); i++) {
            centersArray_list.add(networkinputArray_list.get(selectedPatterns_list.get(i)));
        }

        //initializing weights
        weights = new double[centersNum];
        for (int i = 0; i < centersNum; i++) {
            weights[i] = -rand.nextDouble() + 0.5;
        }


        //computing RBF-widths (Sk)
        double centersDistance_Max = 0;
        for (int i = 0; i < centersArray_list.size()-1; i++) {
            double currentCenter_first[] = centersArray_list.get(i).clone();
            for (int j = i + 1; j < centersArray_list.size(); j++) {
                double currentCenter_second[] = centersArray_list.get(j).clone();
                double centersDistance = Math.sqrt(Math.pow((currentCenter_first[0] - currentCenter_second[0]), 2) +
                        Math.pow((currentCenter_first[1] - currentCenter_first[1]), 2));
                if (centersDistance > centersDistance_Max) {
                    centersDistance_Max = centersDistance;
                }
            }
        }
        double RBF_widths = centersDistance_Max / Math.sqrt(2 * centersNum);


        //main loop. this loop goes through all patterns and computes "rk"s and final output for each pattern.
        double coordinates [];
        double eta = 0.1;
        for (int i = 0; i < networkinputArray_list.size(); i++) {
            coordinates = new double[2];
            double currentInput[] = networkinputArray_list.get(i).clone();
            double distances[] = new double[centersNum];
            outputs = new double[centersNum];
            //computing distance
            for (int j = 0; j < centersArray_list.size(); j++) {
                double currentCenter[] = centersArray_list.get(j).clone();
                distances[j] = Math.sqrt(Math.pow((currentInput[0] - currentCenter[0]), 2) +
                        Math.pow((currentInput[1] - currentCenter[1]), 2));
                outputs[j] = Math.exp(-(Math.pow(distances[j], 2) / (2 * Math.pow(RBF_widths, 2)))); // "outputs" values are the very same "rk"s
            }

            double finalOutput = 0;
            for (int j = 0; j < centersNum; j++) {
                finalOutput += outputs[j] * weights[j];
            }

            //computing error
            double error = Math.abs(correctOutput_list.get(i)[0] - finalOutput);

            //updating weights
            for (int j = 0; j < centersNum; j++) {
                weights[j] += eta * error * outputs[j];
            }

            //adding achieved points to the "plotPoints" list, for drawing the learning curve at last
            coordinates[0] = i;
            coordinates[1] = error;
            plotPoints.add(coordinates);
        }



        //printing weights after training

        System.out.println("The updated weights after training are: "+Arrays.toString(weights));

    }

    //this method just reads the input from a specific file
    public void inputReader(){
        //reading training data from dat file
        String filePath = "/Users/fazeletavakoli/IdeaProjects/TNNProjects/ThirdTNNProjectـmaven/training.txt";
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

    public List <double[]> getPlotPoints(){
        return plotPoints;
    }

}
