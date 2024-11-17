package use_case.query;

import entity.User;
import use_case.query.*;

/**
 * The Query Interactor.
 */
public class QueryInteractor implements QueryInputBoundary {
    private final QueryOutputBoundary queryPresenter;

    public QueryInteractor(QueryOutputBoundary queryOutputBoundary) {
        this.queryPresenter = queryOutputBoundary;
    }

    @Override
    public void execute(QueryInputData queryInputData) {
        final String topic = queryInputData.getTopic();
    }

    @Override
    public void switchToResultsView() {
        queryPresenter.switchToResultsView();
    }
}
