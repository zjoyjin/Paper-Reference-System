package use_case.results;

/**
 * Input Boundary for actions which are related to the results of a query.
 */
public interface ResultsInputBoundary {

    /**
     * Executes the results use case.
     * @param resultsInputData the input data
     */
    void execute(ResultsInputData resultsInputData);

    /**
     * Switch to query page view.
     */
    void switchToQueryView();
}
