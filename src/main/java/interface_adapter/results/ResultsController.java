package interface_adapter.results;

import use_case.results.ResultsInputData;
import use_case.results.ResultsInputBoundary;

/**
 * The controller for the Results Use Case.
 */
public class ResultsController {
    private final ResultsInputBoundary resultsUseCaseInteractor;

    public ResultsController(ResultsInputBoundary resultsUseCaseInteractor) {
        this.resultsUseCaseInteractor = resultsUseCaseInteractor;
    }

    /**
     * Executes the Results Use Case.
     * @param query the search term.
     */
    public void execute(String query) {
        final ResultsInputData resultsInputData = new ResultsInputData(query);

        resultsUseCaseInteractor.execute(resultsInputData);
    }

//    /**
//     * Executes the "switch to QueryView" Use Case.
//     */
//    public void switchToQueryView() {
//        resultsUseCaseInteractor.switchToQueryView();
//    }
}
