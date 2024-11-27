package use_case.results;

import java.util.HashSet;
import java.util.Set;

import entity.Article;
import entity.Edge;

/**
 * The Results Interactor.
 */
public class ResultsInteractor implements ResultsInputBoundary {

    private ResultsDataAccessInterface dataAccessInterface;
    private ResultsOutputBoundary resultsPresenter;

    public ResultsInteractor(ResultsDataAccessInterface dataAccess,
                             ResultsOutputBoundary resultsOutputBoundary) {
        this.dataAccessInterface = dataAccess;
        resultsPresenter = resultsOutputBoundary;
    }

    @Override
    public void execute(ResultsInputData inputData) {
        final String query = inputData.getQuery();
        final Set<Article> articles = dataAccessInterface.get(query);
        final Set<Edge> edges = createEdges(articles);

        final ResultsOutputData outputData = new ResultsOutputData(articles, edges, false);

        resultsPresenter.prepareSuccessView(outputData);
    }

    @Override
    public void switchToQueryView() {
        resultsPresenter.switchToQueryView();
    }

    private Set<Edge> createEdges(Set<Article> articles) {
        final Set<Edge> edges = new HashSet<>();
        for (Article a : articles) {
            for (Article b : articles) {
                if (a.hasReference(b)) {
                    edges.add(new Edge(a, b));
                }
            }
        }
        return edges;
    }

    /* @Override
    public void switchToQueryView(){
        resultsPresenter.switchToQueryView();
    }
    */
}
