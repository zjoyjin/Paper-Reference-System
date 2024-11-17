package interface_adapter.query;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.query.QueryOutputBoundary;
import use_case.query.QueryOutputBoundary;

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
    public void switchToResultsView() {
        viewManagerModel.setState("results");
        viewManagerModel.firePropertyChanged();
    }
}
