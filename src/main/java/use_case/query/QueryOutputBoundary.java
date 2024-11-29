package use_case.query;

import javax.swing.*;
import java.util.ArrayList;

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

    /**
     * Switches to the Query View.
     */
    void switchToQueryView();


    void showSearchHistory(ArrayList<String> username, JTextField queryInputField, JPopupMenu popupMenu);

    void switchToLoggedInView();

}
