package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartRandomPlacementStrategy;
import entity.Article;
import entity.Edge;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.query.QueryState;
import interface_adapter.query.QueryViewModel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.embed.swing.JFXPanel;


public class ResultsView extends JPanel implements ActionListener, PropertyChangeListener {

    private final QueryViewModel queryViewModel;

    private BufferedImage image;

    public ResultsView(QueryViewModel queryViewModel, Set<Article> articles, Set<Edge> edges) {

        this.queryViewModel = queryViewModel;
        this.queryViewModel.addPropertyChangeListener(this);

        // Set title
        final JLabel title = new JLabel("Results Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add javafx panel for the graph
        final JFXPanel jfxPanel = new JFXPanel();
        this.add(jfxPanel);

        // Create the graph
        Digraph<String, String> g = createGraph(articles, edges);

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
    }

    private Digraph<String, String> createGraph(Set<Article> articles, Set<Edge> edges) {
        Digraph<String, String> g = new DigraphEdgeList<>();
        for (Article a : articles) {
            g.insertVertex(a.getTitle());
        }
        for (Edge e : edges) {
            g.insertEdge(e.getPaper().getTitle(), e.getReference().getTitle(), "");
        }
        return g;
    }

    //article creation logic here for now -- TODO: MOVE LATER!
    public Set<Edge> createEdges(Set<Article> articles) {
        Article[] articleList = articles.toArray(new Article[articles.size()]);
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < articleList.length; i++) {
            for (int j = i + 1; j < articleList.length; j++) {
                if (articleList[i].hasReference(articleList[j])) {
                    edges.add(new Edge(articleList[i], articleList[j]));
                }
            }
        }
        return edges;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
/
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ResultsState state = (ResultsState) evt.getNewValue();
        // if state = error...
    }

}
