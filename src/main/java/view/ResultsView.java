package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SummaryPanelModule.SummaryPanel;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.InvalidVertexException;
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
 * The View for the Results Use Case.
 */
public class ResultsView extends JPanel implements PropertyChangeListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 500;

    private final ResultsViewModel resultsViewModel;
    private final String viewName = "results";
    private ResultsController resultsController;

    private Set<Article> articles = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();

    private final JFXPanel jfxPanel = new JFXPanel();
    private final Digraph<String, String> g = new DigraphEdgeList<>();
    private SmartPlacementStrategy initialPlacement = new SmartRandomPlacementStrategy();
    private SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, initialPlacement);

    public ResultsView(ResultsViewModel resultsViewModel) {

        this.resultsViewModel = resultsViewModel;
        this.resultsViewModel.addPropertyChangeListener(this);

        // Set title
        final JLabel title = new JLabel("Results Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JButton goBack = new JButton("return");
        goBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        /////////////////////
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ////////////

        // add javafx panel for the graph

        this.add(jfxPanel);
        this.add(title);
        this.add(goBack);

        goBack.addActionListener(
                evt -> {
                    if (this.resultsController != null) {
                        this.resultsController.switchToQueryView();
                    }
                    else {
                        System.err.println("ResultsController is null!");
                    }
                }
        );
    }

    private void populateGraph(Set<Article> articleList, Set<Edge> edgeList) {
        for (Article a : articleList) {
            try {
                g.insertVertex(a.getDoi());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        int counter = 0;
        for (Edge e : edgeList) {
            try {
                g.insertEdge(e.getPaper().getDoi(), e.getReference().getDoi(), Integer.toString(counter));
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            counter++;
        }
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
                    // System.out.println("Vertex has DOI: " + a.getDoi());
                    System.out.println(a.getLink());

                    final Set<String> authorSet = Stream.of(a.getAuthors()).collect(Collectors.toSet());

                    final SummaryPanel sumPanel = new SummaryPanel(a.getTitle(), authorSet, a.getPublication(),
                            a.getLink());

                    final JFrame frame = new JFrame("Article Summary");

                    // Set up the frame to display the panel
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                    frame.setLocationRelativeTo(null);
                    frame.add(sumPanel);
                    frame.setVisible(true);

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
