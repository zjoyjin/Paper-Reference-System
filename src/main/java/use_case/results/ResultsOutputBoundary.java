package use_case.results;

/**
 * The output boundary for the Resylts Use Case.
 */
public interface ResultsOutputBoundary {

    /**
     * Prepares the success view for the Results Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ResultsOutputData outputData);

    /**
     * Switches to the Query View.
     */
    void switchToQueryView();
}
