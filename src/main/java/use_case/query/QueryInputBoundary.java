package use_case.query;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Input Boundary for actions which are related to querying.
 */
public interface QueryInputBoundary {

    /**
     * Executes the query use case.
     * @param queryInputData the input data
     */
    void execute(QueryInputData queryInputData);

    /**
     * Switch to results page view.
     */
    void switchToResultsView();

    /**
     * Switch to query page view.
     */
    void switchToQueryView();

    /**
     * to get the arraylist of search history with a given username
     */
    void showSearchHistory(String username, JTextField queryInputField, JPopupMenu popupMenu);
}
