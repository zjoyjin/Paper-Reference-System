package use_case.results;

public interface ResultsInputBoundary {

    /**
     * Executes the results use case.
     * @param resultsInputData the input data
     */
    void execute(ResultsInputData resultsInputData);

    // void switchToQueryView();
}