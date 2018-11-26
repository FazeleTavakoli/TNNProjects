import org.jfree.ui.RefineryUtilities;

/**
 * Created by fazeletavakoli on 11/13/17.
 */
public class RBF {
    public static void main(String args[]){
        Neuron neuron = new Neuron();
        neuron.networkGenerator();
        System.out.println("Now you can see the learning curve on a new window");


        Plot chart = new Plot("Learning Curve",
                "RBF Learning curve",neuron.getPlotPoints());
        //chart.setXYPoints(neuron.getPlotPoints());
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}
