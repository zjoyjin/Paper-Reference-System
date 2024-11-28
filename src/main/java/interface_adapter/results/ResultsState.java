package interface_adapter.results;

import java.util.HashSet;
import java.util.Set;

import entity.Article;
import entity.Edge;

/**
 * The state for the Results View Model.
 */
public class ResultsState {
    private Set<Article> articles = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }
}
