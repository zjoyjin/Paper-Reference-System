package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartRandomPlacementStrategy;
import entity.Article;
import entity.Edge;
import interface_adapter.results.ResultsController;
import interface_adapter.results.ResultsState;
import interface_adapter.results.ResultsViewModel;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

/**
 * The View for the result graph.
 */
public class ResultsView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final ResultsViewModel resultsViewModel;
    private final String viewName = "results";
    private ResultsController resultsController;

    private Set<Article> articles = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();

    private final JFXPanel jfxPanel = new JFXPanel();
    private Digraph<String, String> g = new DigraphEdgeList<>();
    private SmartPlacementStrategy initialPlacement = new SmartRandomPlacementStrategy();
    private SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, initialPlacement);

    public ResultsView(ResultsViewModel resultsViewModel) {

        this.resultsViewModel = resultsViewModel;
        this.resultsViewModel.addPropertyChangeListener(this);

        // Set title
        final JLabel title = new JLabel("Results Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        /////////////////////
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ////////////

        // add javafx panel for the graph
        this.add(jfxPanel);
        this.add(title);
    }

    private void populateGraph(Set<Article> articleList, Set<Edge> edgeList) {
        for (Article a : articleList) {
            try {
                g.insertVertex(a.getDoi());
            }
            catch (IllegalArgumentException exception) {
                exception.printStackTrace();
            }
        }
        int counter = 0;
        for (Edge e : edgeList) {
            try {
                g.insertEdge(e.getPaper().getDoi(), e.getReference().getDoi(), Integer.toString(counter));
            }
            catch (IllegalArgumentException exception) {
                exception.printStackTrace();
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

        final Scene scene = new Scene(graphView, 800, 600);
        jfxPanel.setScene(scene);

        // IMPORTANT! - Called after scene is displayed, so we can initialize the graph visualization
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

    public String getViewName() {
        return viewName;
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }
}
