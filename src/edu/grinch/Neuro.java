package edu.grinch;

import edu.grinch.graph.Vertex;
import edu.grinch.ins.HopfieldNeuralNetwork;
import edu.grinch.ins.KohonensNeuralNetwork;
import edu.grinch.ins.SingleLayerPerceptron;
import edu.grinch.linearalgebra.Matrix;
import edu.grinch.linearalgebra.Vector;
import edu.grinch.simulator.Simulator;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * Author Grinch
 * Date: 03.05.14
 * Time: 14:48
 */
public class Neuro {
    static JPanel graphPanel, controlPanel, mainPanel;
    static JButton startButton;
    static BasicVisualizationServer<Vertex,String> vv;
    static Simulator simulator;
    static Timer timer = new Timer();
    public static final int MILLISECONDS_STEP = 1;

    public static void main(String[] args){
        initUI();
        simulator = new Simulator();

        Layout<Vertex, String> layout = new KKLayout<Vertex, String>(simulator.getGraphWays().getGraph());
        layout.setSize(new Dimension(650,650));

        vv = new BasicVisualizationServer<Vertex,String>(layout);
        //vv.setPreferredSize(new Dimension(650,650));
        Transformer<Vertex,Paint> vertexPaint = new Transformer<Vertex,Paint>() {
            public Paint transform(Vertex v) {
                if (v == simulator.getGraphWays().getStartVertex()){
                    return Color.WHITE;
                }
                if (v == simulator.getGraphWays().getEndVertex()){
                    return Color.RED;
                }
                if (simulator.getAntCount(v) > 0){
                    //return simulator.getFirstAnt(v).getColor();
                    return Color.BLACK;
                }
                return Color.GREEN;
            }
        };
        // Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

        Transformer<String, Stroke> edgeStrokeTransformer =
                new Transformer<String, Stroke>() {
                    public Stroke transform(String s) {
                        return edgeStroke;
                    }
                };



        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);

        vv.getRenderContext().setVertexLabelTransformer(getVertexLabels());
        //vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.N);

        graphPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        graphPanel.add(vv);


        JLabel label1 = new JLabel("Time: 00:00:00", null, JLabel.CENTER);

        controlPanel.add(label1);
        controlPanel.add(startButton);
        mainPanel.add(controlPanel,BorderLayout.PAGE_START);
        mainPanel.add(graphPanel,BorderLayout.CENTER);

        JFrame frame = new JFrame("Ants");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static private Transformer<Vertex, String> getVertexLabels(){
        return new Transformer<Vertex, String>() {
                    public String transform(Vertex v) {
                        return v.toString()+", A="+simulator.getAntCount(v)+", F="+Math.round(v.getPheromone()*1000.)/1000.;
                    }
                };

    }
    static boolean enable = false;
    private static void initUI() {
        graphPanel = new JPanel(new GridBagLayout());
        controlPanel = new JPanel(new FlowLayout());
        mainPanel = new JPanel(new BorderLayout());
        startButton = new JButton("Speed UP");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        simulator.move();
                        vv.getRenderContext().setVertexLabelTransformer(getVertexLabels());
                        vv.updateUI();
                    }
                }, 0, MILLISECONDS_STEP);
            }
        });
    }

    static void hnn(){
        HopfieldNeuralNetwork hnn = new HopfieldNeuralNetwork(9);

        Vector v = new Vector(9);
        v.setData(0,1);v.setData(1,-1);v.setData(2,1);
        v.setData(3,-1);v.setData(4,1);v.setData(5,-1);
        v.setData(6,1);v.setData(7,-1);v.setData(8,1);
        hnn.training(v);

        v.setData(0,1);v.setData(1,1);v.setData(2,1);
        v.setData(3,-1);v.setData(4,-1);v.setData(5,1);
        v.setData(6,-1);v.setData(7,-1);v.setData(8,1);
        hnn.training(v);

        v.setData(0,1);v.setData(1,-1);v.setData(2,-1);
        v.setData(3,-1);v.setData(4,1);v.setData(5,-1);
        v.setData(6,-1);v.setData(7,-1);v.setData(8,1);

        Vector R = hnn.combat(v);
        Matrix k = new Matrix(new double[][]{{R.getData(0),R.getData(1),R.getData(2)},
                                             {R.getData(3),R.getData(4),R.getData(5)},
                                             {R.getData(6),R.getData(7),R.getData(8)}});
        System.out.println(k);
    }

    static void knn(){
        KohonensNeuralNetwork knn = new KohonensNeuralNetwork(9,3);
        Vector v = new Vector(3);
        v.setData(0,0);
        v.setData(1,1);
        v.setData(2,0);
        knn.training(v);

        v = new Vector(3);
        v.setData(0,1);
        v.setData(1,0);
        v.setData(2,1);
        knn.training(v);

        v = new Vector(3);
        v.setData(0,1);
        v.setData(1,1);
        v.setData(2,1);
        knn.training(v);

        v = new Vector(3);
        v.setData(0,0);
        v.setData(1,1);
        v.setData(2,0);
        knn.training(v);
    }

    static void slp(){
        SingleLayerPerceptron slp = new SingleLayerPerceptron(3,2);
        Vector X = new Vector(2);
        double eps = 1;
        double delta;

        List<Vector> samples = new LinkedList<Vector>();
        List<Double> results = new LinkedList<Double>();
        Random r = new Random();

        for (int i = 0; i < 1500; i++){
            double arg1 = r.nextDouble()*5;
            double arg2 = r.nextDouble()*5;
            Vector v = new Vector(2);
            v.setData(0,arg1);
            v.setData(1,arg2);
            samples.add(v);
            results.add(f(arg1,arg2));
        }

        do{
            delta = 0;
            for (int i = 0; i < samples.size(); i++){
                //slp.training(samples.get(i),results.get(i));
                delta += Math.abs(results.get(i)-slp.getY().toDouble());
            }

            delta /= samples.size();
        }while (delta > eps);

        X.setData(0,3);
        X.setData(1,2);
        System.out.println(slp.combat(X).toDouble());
    }

    public static double f(double x, double y){
        return x;
    }
}
