package interface_adapter.query;

import interface_adapter.ViewManagerModel;
import use_case.query.QueryOutputBoundary;
import use_case.query.QueryOutputData;
import use_case.signup.SignupOutputData;

/**
 * The Presenter for the Query Use Case.
 */
public class QueryPresenter implements QueryOutputBoundary {
    private final QueryViewModel queryViewModel;
    private final ViewManagerModel viewManagerModel;

    public QueryPresenter(ViewManagerModel viewManagerModel,
                          QueryViewModel queryViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.queryViewModel = queryViewModel;
    }

    @Override
    public void prepareSuccessView(QueryOutputData response) {
        // On success, switch to the query view.
        final QueryState queryState = queryViewModel.getState();
        queryState.setTopic(response.getTopic());
        this.queryViewModel.setState(queryState);
        queryViewModel.firePropertyChanged();

        viewManagerModel.setState(queryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

//    @Override
//    public void prepareFailView(String error) {
//        final QueryState queryState = queryViewModel.getState();
//        queryState.setQueryError(error);
//        queryViewModel.firePropertyChanged();
//    }

    @Override
    public void switchToResultsView() {
        viewManagerModel.setState("results");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToQueryView() {
//        viewManagerModel.setState("search");
        viewManagerModel.setState(queryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
