package interface_adapter.query;

import use_case.query.QueryInputBoundary;
import use_case.query.QueryInputData;

/**
 * The controller for the Query Use Case.
 */
public class QueryController {
    private final QueryInputBoundary queryUseCaseInteractor;

    public QueryController(QueryInputBoundary queryUseCaseInteractor) {
        this.queryUseCaseInteractor = queryUseCaseInteractor;
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
    public void switchToSignupView() {
        queryUseCaseInteractor.switchToResultsView();
    }
}
