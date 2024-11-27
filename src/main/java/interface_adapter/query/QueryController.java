package interface_adapter.query;

import use_case.login.LoginInputData;
import data_access.QueryDataAccessObject;
import entity.Article;
import java.util.Set;

import use_case.query.QueryDataAccessInterface;
import use_case.query.QueryInputBoundary;
import use_case.query.QueryInputData;
import use_case.results.ResultsDataAccessInterface;

/**
 * The controller for the Query Use Case.
 */
public class QueryController {
    private final QueryInputBoundary queryUseCaseInteractor;
    private final ResultsDataAccessInterface queryDao;

    public QueryController(QueryInputBoundary queryUseCaseInteractor, ResultsDataAccessInterface queryDao) {
        this.queryUseCaseInteractor = queryUseCaseInteractor;
        this.queryDao = queryDao;
    }

    /**
     * Executes the Query Use Case.
     * @param topic the topic being searched about
     */
    public void execute(String topic) {
        final QueryInputData queryInputData = new QueryInputData(topic);

        queryUseCaseInteractor.execute(queryInputData);
    }

    /**
     * Executes the "switch to Results View" Use Case.
     */
    public void switchToResultsView() {
        queryUseCaseInteractor.switchToResultsView();
    }

    /**
     * //TODO .
     * @param topic the topic being searched about
     */
    public Set<Article> searchArticles(String topic) {
        // Uses default sorting by relevance
        return queryDao.get(topic);
    }

    /**
     * //TODO .
     * @param sortType the way to sort results
     * @param topic the topic being searched about
     */
    public Set<Article> searchArticles(String sortType, String topic) {
        // Allows custom sorting
        return queryDao.get(sortType, topic);
    }

    /**
     * Executes the "switch to QueryView" Use Case.
     */
    public void switchToQueryView() {
        queryUseCaseInteractor.switchToQueryView();
    }
}
