package use_case.query;

import java.util.ArrayList;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import use_case.search_history.SearchHistoryDataAcessInterface;

/**
 * The Query Interactor.
 */
public class QueryInteractor implements QueryInputBoundary {
    private final QueryOutputBoundary queryPresenter;
    private final SearchHistoryDataAcessInterface searchHistoryDao;

    public QueryInteractor(QueryOutputBoundary queryOutputBoundary, SearchHistoryDataAcessInterface searchHistoryDao) {
        this.queryPresenter = queryOutputBoundary;
        this.searchHistoryDao = searchHistoryDao;
    }

    @Override
    public void execute(QueryInputData queryInputData) {
        final String topic = queryInputData.getTopic();
        final String username = queryInputData.getUsername();
        searchHistoryDao.save(username, topic);
    }

    @Override
    public void switchToResultsView() {
        queryPresenter.switchToResultsView();
    }

    @Override
    public void switchToQueryView() {
        queryPresenter.switchToQueryView();
    }

    @Override

    public void showSearchHistory(String username, JTextField queryInputField, JPopupMenu popupMenu) {
        if (searchHistoryDao.get(username) == null) {
            queryPresenter.showSearchHistory(new ArrayList<>(), queryInputField, popupMenu);
        }

        else {

            queryPresenter.showSearchHistory(searchHistoryDao.get(username), queryInputField, popupMenu);
        }

    }

    /**
     * Switch to logged in view.
     */
    public void switchToLoggedInView() {
        queryPresenter.switchToLoggedInView();
    }

}
