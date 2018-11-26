import java.util.*;

/**
 * Created by fazeletavakoli on 11/26/17.
 */
public class M_Neural_Gas {

    public static void main(String args[]) {
        Neuron neuron = new Neuron();
        neuron.inputReader();
        neuron.fileCleaner();
        List<double[]> inputArray_list = new ArrayList<>(neuron.getNetworkinputArray_list());


        neuron.writeInFile("Centers before being updated are :");
        neuron.centersGenerator(0);
        List<double[]> firstCentersArray_list = new ArrayList<>(neuron.getCentersArray_list());
        double firstWidth = neuron.getWidths();

        neuron.centersGenerator(250);
        List<double[]> secondCentersArray_list = new ArrayList<>(neuron.getCentersArray_list());
        double secondWidth = neuron.getWidths();

        neuron.centersGenerator(500);
        List<double[]> thirdCentersArray_list = new ArrayList<>(neuron.getCentersArray_list());
        double thirdWidth = neuron.getWidths();

        neuron.centersGenerator(750);
        List<double[]> fourthCentersArray_list = new ArrayList<>(neuron.getCentersArray_list());
        double fourthWidth = neuron.getWidths();

        for (int i = 0; i < 1000; i++) {
            Map<Integer, Double> neuralGas_winner = new HashMap<>();
            HashMap<Integer, Double> neuralGas_winner_sorted = new LinkedHashMap<>();
            double[] currentInput = inputArray_list.get(i);
            //Determining winner neuron in the first neural gas network
            neuralGas_winner.put(1, neuron.winnerDeterminer(firstCentersArray_list, currentInput));
            HashMap<Integer, Double> firstCenters_Responses_map_sorted = new LinkedHashMap<>(neuron.getCentersResponses_map_sorted());

            //Determining winner neuron in the second neural gas network
            neuralGas_winner.put(2, neuron.winnerDeterminer(secondCentersArray_list, currentInput));
            HashMap<Integer, Double> secondCenters_Responses_map_sorted = new LinkedHashMap<>(neuron.getCentersResponses_map_sorted());

            //Determining winner neuron in the third neural gas network
            neuralGas_winner.put(3, neuron.winnerDeterminer(thirdCentersArray_list, currentInput));
            HashMap<Integer, Double> thirdCenters_Responses_map_sorted = new LinkedHashMap<>(neuron.getCentersResponses_map_sorted());

            //Determining winner neuron in the forth neural gas network
            neuralGas_winner.put(4, neuron.winnerDeterminer(fourthCentersArray_list, currentInput));
            HashMap<Integer, Double> fourthCenters_Responses_map_sorted = new LinkedHashMap<>(neuron.getCentersResponses_map_sorted());

            //Determining the winner of all neural gas networks
            neuralGas_winner_sorted = new LinkedHashMap<>(neuron.sortByValues(neuralGas_winner));
            for (Map.Entry<Integer, Double> entry : neuralGas_winner_sorted.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                switch (key) {
                    case 1:
                        firstCentersArray_list = new ArrayList<>(neuron.centerUpdater(firstCentersArray_list, firstCenters_Responses_map_sorted, inputArray_list.get(i), firstWidth));
                        break;
                    case 2:
                        secondCentersArray_list = new ArrayList<>(neuron.centerUpdater(secondCentersArray_list, secondCenters_Responses_map_sorted, inputArray_list.get(i), secondWidth));
                        break;
                    case 3:
                        thirdCentersArray_list = new ArrayList<>(neuron.centerUpdater(thirdCentersArray_list, thirdCenters_Responses_map_sorted, inputArray_list.get(i), thirdWidth));
                        break;
                    case 4:
                        fourthCentersArray_list = new ArrayList<>(neuron.centerUpdater(fourthCentersArray_list, fourthCenters_Responses_map_sorted, inputArray_list.get(i), fourthWidth));
                        break;

                }
            }

        }
        /*System.out.println("centers after being updated are: ");
        for (int i=0; i< neuron.getCentersArray_list().size(); i++){
            System.out.println(Arrays.toString(neuron.getCentersArray_list().get(i)));
        }*/

        neuron.writeInFile("Centers after being updated are :");
        for (int j=0; j< firstCentersArray_list.size(); j++){
            neuron.writeInFile(Arrays.toString(firstCentersArray_list.get(j)));
        }
        for (int j=0; j< secondCentersArray_list.size(); j++){
            neuron.writeInFile(Arrays.toString(secondCentersArray_list.get(j)));
        }
        for (int j=0; j< thirdCentersArray_list.size(); j++){
            neuron.writeInFile(Arrays.toString(thirdCentersArray_list.get(j)));
        }
        for (int j=0; j< fourthCentersArray_list.size(); j++){
            neuron.writeInFile(Arrays.toString(fourthCentersArray_list.get(j)));
        }

    }

}
