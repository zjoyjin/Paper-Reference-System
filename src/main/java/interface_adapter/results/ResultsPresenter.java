package interface_adapter.results;

import interface_adapter.ViewManagerModel;
import use_case.results.ResultsOutputBoundary;
import use_case.results.ResultsOutputData;

/**
 * The Presenter for the Results Use Case.
 */
public class ResultsPresenter implements ResultsOutputBoundary {
    private final ResultsViewModel resultsViewModel;
    private final ViewManagerModel viewManagerModel;

    public ResultsPresenter(ResultsViewModel resultsViewModel, ViewManagerModel viewManagerModel) {
        this.resultsViewModel = resultsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ResultsOutputData outputData) {
        final ResultsState resultsState = resultsViewModel.getState();
        resultsState.setArticles(outputData.getArticles());
        resultsState.setEdges(outputData.getEdges());

        resultsViewModel.setState(resultsState);
        resultsViewModel.firePropertyChanged();

        viewManagerModel.setState(resultsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToQueryView() {
        viewManagerModel.setState("query");
        viewManagerModel.firePropertyChanged();
    }

}
