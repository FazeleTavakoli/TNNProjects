/**
 * Created by fazeletavakoli on 11/18/17.
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.awt.Color;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;

public class Plot extends ApplicationFrame {
    //private List<double []> XYPoints_list = new ArrayList<>();

    public Plot( String applicationTitle, String chartTitle, List<double []> XYPoints_list ) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "Training Set Size" ,
                "Error" ,
                createDataset(XYPoints_list) ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        /*renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.YELLOW );*/
        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        /*renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );*/
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset(List<double []> XYPoints_list ) {
        final XYSeries RBFPoints = new XYSeries( "RBFPoints" );
        for (int i=0; i<XYPoints_list.size();i++){
            double x = XYPoints_list.get(i)[0];
            double y = XYPoints_list.get(i)[1];
            RBFPoints.add(x,y);
        }
        /*RBFPoints.add( 1.0 , 1.0 );
        RBFPoints.add( 2.0 , 4.0 );
        RBFPoints.add( 3.0 , 3.0 );*/
        /*final XYSeries chrome = new XYSeries( "Chrome" );
        chrome.add( 1.0 , 4.0 );
        chrome.add( 2.0 , 5.0 );
        chrome.add( 3.0 , 6.0 );

        final XYSeries iexplorer = new XYSeries( "InternetExplorer" );
        iexplorer.add( 3.0 , 4.0 );
        iexplorer.add( 4.0 , 5.0 );
        iexplorer.add( 5.0 , 4.0 );*/

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( RBFPoints );
        /*dataset.addSeries( chrome );
        dataset.addSeries( iexplorer );*/
        return dataset;
    }


    /*public void setXYPoints(List<double[]> pointsList){
        XYPoints_list = new ArrayList<>(pointsList);
    }*/

    /*public static void main( String[ ] args ) {
        XYLineChart_AWT chart = new XYLineChart_AWT("Browser Usage Statistics",
                "Which Browser are you using?");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }*/
}
