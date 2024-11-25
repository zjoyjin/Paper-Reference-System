package use_case.results;

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
        final Set<Article> articles = dataAccessInterface.getArticles(query);
        final Set<Edge> edges = dataAccessInterface.getEdges(query);

        final ResultsOutputData outputData = new ResultsOutputData(articles, edges, false);

        resultsPresenter.prepareSuccessView(outputData);
    }

    /* @Override
    public void switchToQueryView(){
        resultsPresenter.switchToQueryView();
    }
    */
}
