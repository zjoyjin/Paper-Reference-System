package interface_adapter.query;

import java.util.Set;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import entity.Article;
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
     * @param username the username of the user.
     */
    public void execute(String topic, String username) {
        final QueryInputData queryInputData = new QueryInputData(topic, username);

        queryUseCaseInteractor.execute(queryInputData);
    }

    /**
     * Executes the "switch to Results View" Use Case.
     */
    public void switchToResultsView() {
        queryUseCaseInteractor.switchToResultsView();
    }

    /**
     * Searches for research articles based on given topic input.
     * @param topic the topic being searched about
     * @return a set of Articles related to given topic
     */
    public Set<Article> searchArticles(String topic) {
        // Uses default sorting by relevance
        return queryDao.get(topic);
    }

    /**
     * Searches for research articles based on given topic input and sorting type.
     * @param sortType the way to sort results
     * @param topic the topic being searched about
     * @return a set of Articles related to given topic based on given sorting method
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

    /**
     * Executes the "show search history" Use Case.
     * @param username the username.
     * @param queryInputField the text field that take the topic entered.
     * @param popupMenu the popup menu that displays search history.
     */
    public void showSearchHistory(String username, JTextField queryInputField, JPopupMenu popupMenu) {
        queryUseCaseInteractor.showSearchHistory(username, queryInputField, popupMenu);
    }

    /**
     * Executes the "switch to LoggedInView" Use Case.
     */
    public void switchToLoggedInView() {
        queryUseCaseInteractor.switchToLoggedInView();
    }

}
