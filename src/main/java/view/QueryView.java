package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.query.QueryController;
import interface_adapter.query.QueryState;
import interface_adapter.query.QueryViewModel;
import interface_adapter.results.ResultsController;

/**
 * The View for when the user is entering a research topic in the program.
 */
public class QueryView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "query";

    private final QueryViewModel queryViewModel;
    private QueryController queryController;

    private ResultsController resultsController;

    private final JTextField queryInputField = new JTextField(15);
    private final JButton search;

    public QueryView(QueryViewModel queryViewModel) {
        this.queryViewModel = queryViewModel;
        this.queryViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Query Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel searchInfo = new LabelTextPanel(
                new JLabel("Enter search topic:"), queryInputField);

        final JPanel buttons = new JPanel();
        search = new JButton("Search!");
        buttons.add(search);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        search.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(search)) {
                        final QueryState currentState = queryViewModel.getState();
                        currentState.setTopic(queryInputField.getText());
                        this.resultsController.execute(
                                currentState.getTopic()
                        );
                    }
                }
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(search)) {
//                            final QueryState currentState = queryViewModel.getState();
//
//                            this.queryController.execute(currentState.getTopic());
//                        }
//                    }
//                }
        );
        this.add(title);
        this.add(searchInfo);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final QueryState state = (QueryState) evt.getNewValue();

        // Trying to debug T-T
        String newView = (String) evt.getNewValue();
        System.out.println("Current View Changed: " + newView);

    }

    public String getViewName() {
        return viewName;
    }

    public void setQueryController(QueryController queryController) {
        this.queryController = queryController;
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }
}
