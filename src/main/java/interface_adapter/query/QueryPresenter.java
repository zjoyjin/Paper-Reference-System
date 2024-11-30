package interface_adapter.query;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import interface_adapter.ViewManagerModel;
import use_case.query.QueryOutputBoundary;
import use_case.query.QueryOutputData;

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

        // Tell View Manager to switch to QueryView.
        viewManagerModel.setState(queryViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToResultsView() {
        viewManagerModel.setState("results");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToQueryView() {
        viewManagerModel.setState("search");
        viewManagerModel.setState(queryViewModel.getViewName());

        viewManagerModel.firePropertyChanged();
    }

    /**
     * Showing search history.
     * @param arr representing the search history.
     * @param queryInputField where the user enters topic.
     * @param popupMenu the popup menu that will display history.
     */
    public void showSearchHistory(ArrayList<String> arr, JTextField queryInputField, JPopupMenu popupMenu) {
        final ArrayList<String> searchHistories = arr;
        // Clear existing suggestions
        popupMenu.removeAll();
        final String input = queryInputField.getText().toLowerCase();

        for (String history : searchHistories) {
            if (history.toLowerCase().contains(input)) {
                final JMenuItem item = new JMenuItem(history);
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Autofill the text field
                        queryInputField.setText(history);
                        // Hide the popup
                        popupMenu.setVisible(false);
                    }
                });
                popupMenu.add(item);
            }
        }

        if (popupMenu.getComponentCount() > 0) {
            popupMenu.show(queryInputField, 0, queryInputField.getHeight());
        }
    }

    @Override
    public void switchToLoggedInView() {
        viewManagerModel.setState("logged in");
        viewManagerModel.firePropertyChanged();

    }
}

