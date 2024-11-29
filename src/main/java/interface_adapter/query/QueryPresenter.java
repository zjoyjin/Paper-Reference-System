package interface_adapter.query;

import interface_adapter.ViewManagerModel;
import use_case.query.QueryOutputBoundary;
import use_case.query.QueryOutputData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

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


    public void showSearchHistory(ArrayList<String> arr, JTextField queryInputField , JPopupMenu popupMenu) {
        ArrayList<String> searchHistories = arr;

        popupMenu.removeAll(); // Clear existing suggestions
        String input = queryInputField.getText().toLowerCase();

        for (String history : searchHistories) {
            if (history.toLowerCase().contains(input)) {
                JMenuItem item = new JMenuItem(history);
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        queryInputField.setText(history); // Autofill the text field
                        popupMenu.setVisible(false); // Hide the popup
                    }
                });
                popupMenu.add(item);
            }
        }

        if (popupMenu.getComponentCount() > 0) {
            popupMenu.show(queryInputField, 0, queryInputField.getHeight());
        }

        @Override
        public void switchToLoggedInView(){
            viewManagerModel.setState("logged in");
            viewManagerModel.firePropertyChanged();

        }
    }
}
