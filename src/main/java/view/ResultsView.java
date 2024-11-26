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

    private final QueryViewModel queryViewModel;

    private BufferedImage image;
    Digraph<String, String> g = new DigraphEdgeList<>();

    public ResultsView(QueryViewModel queryViewModel, Set<Article> articles, Set<Edge> edges) {

        this.queryViewModel = queryViewModel;
        this.queryViewModel.addPropertyChangeListener(this);

        // Set title
        final JLabel title = new JLabel("Results Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add javafx panel for the graph
        final JFXPanel jfxPanel = new JFXPanel();
        this.add(jfxPanel);
    private void populateGraph(Set<Article> articles, Set<Edge> edges) {
        for (Article a : articles) {
            g.insertVertex(a.getTitle());
        }
        for (Edge e : edges) {
            g.insertEdge(e.getPaper().getTitle(), e.getReference().getTitle(), "");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ResultsState state = (ResultsState) evt.getNewValue();
        final Set<Article> articles = state.getArticles();
        final Set<Edge> edges = state.getEdges();
        // if state = error...

        // Create the graph
        populateGraph(articles, edges);

        final SmartPlacementStrategy initialPlacement = new SmartRandomPlacementStrategy();
        final SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, initialPlacement);

        // add node clicking handler
        graphView.setVertexDoubleClickAction(graphVertex -> {
            for (Article a : articles) {
                if (a.getTitle().equals(graphVertex.getUnderlyingVertex().element())) {
                    System.out.println("Vertex has DOI: " + a.getDoi());
                }
            }

        });

        // add graph to javafx panel

        Scene scene = new Scene(graphView, 1024, 768);
        jfxPanel.setScene(scene);

        //IMPORTANT! - Called after scene is displayed, so we can initialize the graph visualization
        graphView.setAutomaticLayout(true);
        graphView.init();

        // Reset visibility of the fields

        revalidate();
        repaint();
    }

    public String getViewName() { return viewName; }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

}
