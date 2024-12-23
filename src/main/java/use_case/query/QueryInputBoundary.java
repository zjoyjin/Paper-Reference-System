package use_case.query;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;

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
     * To get the arraylist of search history with a given username.
     * @param username the username.
     * @param queryInputField where the user enters topic.
     * @param popupMenu the popup menu that will display history.
     */
    void showSearchHistory(String username, JTextField queryInputField, JPopupMenu popupMenu);

    /**
     * Switch to LoggedIn page view.
     */
    void switchToLoggedInView();

}
