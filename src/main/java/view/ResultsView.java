package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javax.swing.*;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartRandomPlacementStrategy;
import entity.Article;
import entity.Edge;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.query.QueryViewModel;
import interface_adapter.results.ResultsController;
import interface_adapter.results.ResultsState;
import interface_adapter.results.ResultsViewModel;
import javafx.scene.Scene;
import javafx.embed.swing.JFXPanel;


public class ResultsView extends JPanel implements ActionListener, PropertyChangeListener {

    private final ResultsViewModel resultsViewModel;
    private final String viewName = "results";
    private ResultsController resultsController;

    Set<Article> articles = new HashSet<>();
    Set<Edge> edges = new HashSet<>();

    private final JFXPanel jfxPanel = new JFXPanel();
    Digraph<String, String> g = new DigraphEdgeList<>();
    SmartPlacementStrategy initialPlacement = new SmartRandomPlacementStrategy();
    SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, initialPlacement);



    public ResultsView(ResultsViewModel resultsViewModel) {

        this.resultsViewModel = resultsViewModel;
        this.resultsViewModel.addPropertyChangeListener(this);

        // Set title
        final JLabel title = new JLabel("Results Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        /////////////////////
        this.setPreferredSize(new Dimension(800, 600));
        ////////////

        // add javafx panel for the graph
        this.add(jfxPanel);
        this.add(title);
    }

    private void populateGraph(Set<Article> articles, Set<Edge> edges) {
        for (Article a : articles) {
            try {
                g.insertVertex(a.getDoi());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        int counter = 0;
        for (Edge e : edges) {
            try {
                g.insertEdge(e.getPaper().getDoi(), e.getReference().getDoi(), Integer.toString(counter));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            counter++;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ResultsState state = (ResultsState) evt.getNewValue();
        articles = state.getArticles();
        edges = state.getEdges();
        // if state = error...

        // Create the graph
        populateGraph(articles, edges);

        /////////////////////////////////
        initialPlacement = new SmartCircularSortedPlacementStrategy();
        graphView = new SmartGraphPanel<>(g, initialPlacement);


        // add graph to javafx panel

        Scene scene = new Scene(graphView, 800, 600);
        jfxPanel.setScene(scene);

        //IMPORTANT! - Called after scene is displayed, so we can initialize the graph visualization
        graphView.setAutomaticLayout(true);
        graphView.init();

        // add node clicking handler
        graphView.setVertexDoubleClickAction(graphVertex -> {
            System.out.println("Vertex double-clicked!");
            for (Article a : articles) {
                if (a.getDoi().equals(graphVertex.getUnderlyingVertex().element())) {
                    System.out.println("Vertex has DOI: " + a.getDoi());
                }
            }

        });
        ///////////////////////////////////////////


        // Reset visibility of the fields

        revalidate();
        repaint();
    }

    public String getViewName() { return viewName; }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

}


