package use_case.results;

import java.util.HashSet;
import java.util.Set;

import entity.Article;
import entity.Edge;

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
        Set<Edge> edges = new HashSet<>();
        Article[] articlesList = articles.toArray(new Article[articles.size()]);
        for (int a = 0; a < articles.size(); a++) {
            for (int b = a + 1; b < articles.size(); b++) {
                if (articlesList[a].hasReference(articlesList[b])) {
                    edges.add(new Edge(articlesList[a], articlesList[b]));
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
