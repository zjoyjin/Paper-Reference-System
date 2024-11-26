package interface_adapter.query;

import interface_adapter.ViewModel;

/**
 * The View Model for the Query View.
 */
public class QueryViewModel extends ViewModel<QueryState> {

    public QueryViewModel() {
        super("query");
        setState(new QueryState());
    }

}
