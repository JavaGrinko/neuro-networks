package edu.grinch;

import edu.grinch.graph.Vertex;
import edu.grinch.simulator.Simulator;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author Grinch
 * Date: 03.05.14
 * Time: 14:48
 */
public class Neuro {
    static JPanel graphPanel, controlPanel, mainPanel, imagesPanel;
    static JButton startButton;
    static VisualizationViewer<Vertex,String> vv;
    static Simulator simulator;
    static Timer timer = new Timer();
    static int millisecondsDelay = 1000;
    static JTextField fieldDelay;
    static JTextField fieldID;
    static JLabel aliveAnts;
    static JLabel deadAnts;
    static JTextArea imageArea;

    public static void main(String[] args){
        initUI();
        simulator = new Simulator();

        Layout<Vertex, String> layout = new KKLayout<Vertex, String>(simulator.getGraphWays().getGraph());
        layout.setSize(new Dimension(650,650));

        vv = new VisualizationViewer <Vertex,String>(layout);
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
                if (v.getGameObject() != null){
                    return Color.GRAY;
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
        // Create a graph mouse and add it to the visualization component
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        //graphPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        graphPanel.add(vv);


        //labelTime.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        JPanel panelDelay = new JPanel(new FlowLayout());
        fieldDelay =  new JTextField(5);
        fieldDelay.setText("1000");
        JLabel enterDelay = new JLabel("Enter delay");
        panelDelay.add(enterDelay);
        panelDelay.add(fieldDelay);

        controlPanel.add(panelDelay, BorderLayout.CENTER);
        controlPanel.add(startButton);
        controlPanel.add(aliveAnts);
        controlPanel.add(deadAnts);

        JPanel enterID = new JPanel(new FlowLayout());
        fieldID =  new JTextField(7);
        fieldID.setText("0");
        JLabel enterId = new JLabel("Enter ID:");
        enterID.add(enterId);
        enterID.add(fieldID);

        JButton buttonID = new JButton("OK");

        imagesPanel.add(enterID);
        imagesPanel.add(buttonID);
        imagesPanel.add(imageArea);

        buttonID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Report:").append("\n");
                String sId = fieldID.getText();
                int iId = Integer.valueOf(sId);
                Vertex v = simulator.getGraphWays().getVertexes().get(iId);
                if (v.getGameObject() != null){
                    sb.append("Object: ").append(v.getGameObject()).append("\n");
                    sb.append(v.getGameObject().toPrint());
                }else{
                    sb.append("Object: without object").append("\n");
                }
                imageArea.setText(sb.toString());
            }
        });

        mainPanel.add(imagesPanel,BorderLayout.EAST);
        mainPanel.add(controlPanel,BorderLayout.WEST);
        mainPanel.add(graphPanel,BorderLayout.CENTER);

        JFrame frame = new JFrame("Ants");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static private Transformer<Vertex, String> getVertexLabels(){
        return new Transformer<Vertex, String>() {
                    public String transform(Vertex v) {
                        if (v.getGameObject() != null){
                            String d = v.getGameObject().getClusterID() != -1 ? ", D: "+v.getGameObject().getCurrentDangerous() : "";
                            return v.toString()+", A="+simulator.getAntCount(v)+", "+v.getGameObject().toString()+d;
                        }
                        return v.toString()+", A="+simulator.getAntCount(v)+", F="+Math.round(v.getPheromone()*1000.)/1000.;
                    }
                };
    }

    private static void initUI() {
        aliveAnts = new JLabel("Alive: ");
        aliveAnts.setBorder(new EmptyBorder(0, 5, 0, 5) );
        deadAnts = new JLabel("Dead: ");
        deadAnts.setBorder(new EmptyBorder(0, 5, 0, 5) );
        imageArea = new JTextArea(6, 6);
        graphPanel = new JPanel(new GridBagLayout());
        imagesPanel = new JPanel();
        imagesPanel.setLayout(new BoxLayout(imagesPanel, BoxLayout.Y_AXIS));
        imagesPanel.setBorder(new TitledBorder("DataViewer"));
        controlPanel = new JPanel(new GridLayout(10,1,10,10));
        controlPanel.setBorder(new TitledBorder("Control panel"));
        mainPanel = new JPanel(new BorderLayout());
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                    timer.cancel();
                    timer = new Timer();
                }
                String s = fieldDelay.getText();
                millisecondsDelay = Integer.valueOf(s);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        simulator.move();
                        vv.getRenderContext().setVertexLabelTransformer(getVertexLabels());
                        vv.updateUI();
                        int alive = simulator.getAliveAnts();
                        int dead = simulator.getDeadAnts();
                        aliveAnts.setText("Alive: "+alive);
                        deadAnts.setText("Dead: "+dead);
                    }
                }, 0, millisecondsDelay);
            }
        });
    }
}
