<<<<<<< HEAD
import sun.nio.ch.Net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by fazeletavakoli on 12/10/17.
 */
public class Neuron {

    private Random rand = new Random();
    private double networkinputArray[] = new double[20];
    private int g1 = 1;  //controlSignal
    private double Sn[] = new double [20];
    private double Wnm[][] = new double[20][5]; //Real valued weights. go forward
    private double Vmn [][] = new double [5][20]; //Binary weights. go backward
    private double Rm [] = new double[5];
    private double Yj [] = new double[5]; //output
    private double Netn[] = new double[20];


    public void networkGenerator(){

        //generating random input
        inputGenerator();

        //initializing Wnm
        System.out.println("forward weight matrix W before training is:\n");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 5; j++) {
                Wnm[i][j] = rand.nextDouble() - 0.5; //producing random weights between -0.5 and +0.5
            }
            System.out.println(Arrays.toString(Wnm[i]));
        }


        //initializing Vmn
        System.out.println("\n backward weight matrix V before training is:\n");
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 20; i++) {
                Vmn[j][i] = rand.nextInt(2); //producing binary random weights
            }
            System.out.println(Arrays.toString(Vmn[j]));
        }

        double etha = 0.01; // learning rate
        boolean firstIteration = true;


        //main loop
        for (int k=0; k<5; k++) {
            for (int i = 0; i < 20; i++) {
                    if (firstIteration) {
                        Sn[i] = networkinputArray[i];
                    } else {
                        // 2/3 rule
                        if((networkinputArray[i]*Netn[i] == 1) || (networkinputArray[i] * g1 == 1) || (Netn[i]*g1 == 1)){
                            Sn[i] = 1;
                        }
                        else{
                            Sn[i] = 0;
                        }
                    }
            }
            firstIteration = false;
            //updating "Vmn"s by delta rule
            if(!firstIteration) {
                for (int j = 0; j < 5; j++) {
                    for (int i = 0; i < 20; i++) {
                        Vmn[j][i] = Sn[i];  //The weights directly are set to the desired values of S.
                    }
                }
            }

            //computing Rm
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 20; i++) {
                    Rm[j] += Wnm[i][j] * Sn[i];
                }
            }

            //computing the maximum value of all "Rm"s
            double maxRm = Rm[0];
            int maxIndex = 0;
            for (int j = 1; j < 5; j++) {
                if (Rm[j] > maxRm) {
                    maxRm = Rm[j];
                    maxIndex = j;
                }
            }

            //computing Yj for all recognition layer neurons
            for (int j = 0; j < 5; j++) {
                if (maxRm == Rm[j]) {
                    Yj[j] = 1;
                } else {
                    Yj[j] = 0;
                }
            }

            //updating "Wnm"s by delta rule
            for (int i=0; i<20; i++){
                for (int j=0; j<5; j++){
                    Wnm[i][j] += etha * (1-0) *(Sn[i]);
                }
            }

            for(int i=0; i<20; i++){
                Netn[i] = Vmn[maxIndex][i];
            }

        }


        System.out.println("\n forward weight matrix W after training is:\n");
        for (int i = 0; i < 20; i++) {
            System.out.println(Arrays.toString(Wnm[i]));
        }

        System.out.println("\n backward weight matrix V after training is:\n");
        for (int j = 0; j < 5; j++) {
            System.out.println(Arrays.toString(Vmn[j]));
        }

    }

    //this method generates inputs randomly
    public void inputGenerator(){
        //generating random inputs of the network
        int randomNumber = rand.nextInt(4);
        switch (randomNumber){
            case 0:
            {
                for(int i=0; i<20; i++){
                    if (i%2 == 0)
                        networkinputArray[i] = 1;
                    else
                        networkinputArray[i] = 0;
                }
            }break;

            case 1:
            {
                for(int i=0; i<20; i++){
                    if (i%3 == 0)
                        networkinputArray[i] = 0;
                    else
                        networkinputArray[i] = 1;

                }
            } break;

            case 2:
            {
                for(int i=0; i<20; i++){
                    if (i%4 == 0)
                        networkinputArray[i] = 0;
                    else
                        networkinputArray[i] = 1;

                }
            } break;

            case 3:
            {
                for(int i=0; i<20; i++){
                    if (i%5 == 0)
                        networkinputArray[i] = 0;
                    else
                        networkinputArray[i] = 1;

                }
            } break;

        }


=======
/**
 * Created by fazeletavakoli on 11/26/17.
 */
import java.io.*;
import java.util.*;

public class Neuron {
    private Random rand = new Random();
    private double networkinputArray[];
    private List<double[]> networkinputArray_list = new ArrayList<>(); //for storing input parts of patterns
    private List <double[]> centersArray_list = new ArrayList<>();
    private Map<Integer,Double> centers_Responses_map = new HashMap<>();
    private HashMap<Integer,Double> centersResponses_map_sorted = new LinkedHashMap<>();
    private double widths = 0;
    private File file = new File("/Users/fazeletavakoli/IdeaProjects/TNNProjects/TNNFourthProject_maven/PA-D.net");



    public void centersGenerator(int startInputIndex){
        //generating centers
        int centersNum = 10;  //"number of centers" we want to have in RBF layer
        int selectedPattern = 0;
        List<Integer> selectedPatterns_list = new ArrayList<>();
        for (int i = 0; i < centersNum; i++) {
            while(selectedPatterns_list.contains(selectedPattern)) {
                selectedPattern = rand.nextInt(250);
            }
            selectedPatterns_list.add(selectedPattern);
        }

        //note that selectedPatterns_list.size() equals to centersNum
        centersArray_list = new ArrayList<>();
        for (int i = 0; i < selectedPatterns_list.size(); i++) {
            centersArray_list.add(networkinputArray_list.get(selectedPatterns_list.get(i) + startInputIndex));
        }

        /*System.out.println("centers before being updated are: ");
        for (int i=0; i< getCentersArray_list().size(); i++){
            System.out.println(Arrays.toString(getCentersArray_list().get(i)));
        }*/
        for (int j=0; j< centersArray_list.size(); j++){
            writeInFile(Arrays.toString(centersArray_list.get(j)));
        }

        //computing widths (Sk)
        double centersDistance_Max = 0;
        for (int i = 0; i < centersArray_list.size()-1; i++) {
            double currentCenter_first[] = centersArray_list.get(i).clone();
            for (int j = i + 1; j < centersArray_list.size(); j++) {
                double currentCenter_second[] = centersArray_list.get(j).clone();
                double centersDistance = Math.sqrt(Math.pow((currentCenter_first[0] - currentCenter_second[0]), 2) +
                        Math.pow((currentCenter_first[1] - currentCenter_second[1]), 2));
                if (centersDistance > centersDistance_Max) {
                    centersDistance_Max = centersDistance;
                }
            }
        }
        widths = centersDistance_Max / Math.sqrt(2 * centersNum);
    }

    public double  winnerDeterminer(List <double[]> centers_list , double[] singleInput_array){
        double eta = 0.1; //learning rate
        centers_Responses_map = new HashMap<>();
        double currentInput[] = singleInput_array.clone();
        double response[] = new double[10]; //There are 10 centers
        double currentCenter[];
        //computing distance
        for (int j = 0; j < centersArray_list.size(); j++) {
            currentCenter = centers_list.get(j).clone();
            response[j] = Math.sqrt(Math.pow((currentInput[0] - currentCenter[0]), 2) +
                    Math.pow((currentInput[1] - currentCenter[1]), 2));
            centers_Responses_map.put(j, response[j]);
        }

        //sorting centers_responses map
        centersResponses_map_sorted = new LinkedHashMap();
        centersResponses_map_sorted = new LinkedHashMap(sortByValues(centers_Responses_map));

        //finding winner neuron of the current Neural Gas network
        double firstDistance = 0;
        boolean isTrue = false;
        for(int center: centersResponses_map_sorted.keySet()){
            firstDistance = centersResponses_map_sorted.get(center);
            isTrue = true;
            if(isTrue)
                break;
        }

        return firstDistance;
    }

    public List<double[]> centerUpdater(List<double[]> centerArray_list,Map <Integer,Double> center_response_map , double[] singleInput_array, double MN_Widths) {

        double eta = 0.1; //learning rate
        double currentCenter[];
        double distance = 0; //distance between two indices in sorted list of responses
        int counter = 0;
        double delta_C[];
        for (Integer centerIndex : center_response_map.keySet()) {
            //computing delata C (learning rule)
            distance = counter;
            //computing the valuse of neighborhood function
            //we considered Gaussian function as the neighborhood function
            double neighborhoodFunction_value = Math.exp(-(Math.pow(distance, 2) / (2 * Math.pow(MN_Widths, 2))));
            currentCenter = centerArray_list.get(centerIndex).clone();


            //updating step
            counter++;
            delta_C = new double[currentCenter.length];
            for (int j = 0; j < currentCenter.length; j++) {
                delta_C[j] = (neighborhoodFunction_value * eta) * (singleInput_array[j] - currentCenter[j]);
                currentCenter[j] += delta_C[j];
            }
            centerArray_list.set(centerIndex, currentCenter);
        }
        return centerArray_list;
    }

    //this method just reads the input from a specific file
    public void inputReader(){
        //reading training data from dat file
        String filePath = "/Users/fazeletavakoli/IdeaProjects/TNNProjects/TNNFourthProject_maven/MNGas_Input.txt";
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
                        for (int i = 0; i < 2; i++) {
                            splittedArray[i] = splittedArray[i].trim();
                            networkinputArray[i] = Double.parseDouble(splittedArray[i]);
                        }
                        networkinputArray_list.add(networkinputArray);
                    }
                }
            }
        } catch (IOException e) {
        }
    }


    public HashMap sortByValues(Map<Integer, ? extends Object> map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });


        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public List<double[]> getCentersArray_list(){
        return centersArray_list;
    }

    public Map<Integer,Double> getCentersResponses_map(){
        return centers_Responses_map;
    }

    public Map<Integer,Double> getCentersResponses_map_sorted(){
        return centersResponses_map_sorted;
    }


    public List<double[]> getNetworkinputArray_list(){
        return networkinputArray_list;
    }

    public double getWidths(){
        return widths;
    }

    public void writeInFile(String fileInput) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(fileInput);
            bw.write("\r\n");
            System.out.println("File written Successfully");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }

        }
    }

    public void fileCleaner(){
        // empty the current content
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
>>>>>>> ca492ba96f6a9f164f4637da36eadd64d5eb2989
    }

}
