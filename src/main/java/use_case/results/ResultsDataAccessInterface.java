package use_case.results;

import java.util.Set;

import entity.Article;
import entity.Edge;

/**
 * DAI for the Showing Results Use Case.
 */
public interface ResultsDataAccessInterface {

    Set<Article> getArticles(String query);

    Set<Edge> getEdges(String query);

}
