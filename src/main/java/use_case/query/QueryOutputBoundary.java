package use_case.query;

import use_case.query.QueryOutputData;
import use_case.signup.SignupOutputData;

/**
 * The output boundary for the Query Use Case.
 */
public interface QueryOutputBoundary {
    /**
     * Prepares the success view for the Query Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(QueryOutputData outputData);

    /**
     * Switches to the Results View.
     */
    void switchToResultsView();

    void switchToQueryView();
}
