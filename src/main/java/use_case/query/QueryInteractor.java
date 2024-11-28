package use_case.query;

import use_case.search_history.SearchHistoryDataAcessInterface;
import entity.User;
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
        final User user = queryInputData.getUser();
        searchHistoryDao.save(user, topic);
    }

    @Override
    public void switchToResultsView() {
        queryPresenter.switchToResultsView();
    }

    @Override
    public void switchToQueryView() {
        queryPresenter.switchToQueryView();
    }
}
