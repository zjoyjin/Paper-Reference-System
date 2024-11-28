package use_case.results;

import java.util.Set;

import entity.Article;
import entity.Edge;

/**
 * Output Data for the Results Use Case.
 */
public class ResultsOutputData {

    private final Set<Article> articles;
    private final Set<Edge> edges;
    private final boolean useCaseFailed;

    public ResultsOutputData(Set<Article> articles, Set<Edge> edges, boolean useCaseFailed) {
        this.articles = articles;
        this.edges = edges;
        this.useCaseFailed = useCaseFailed;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
