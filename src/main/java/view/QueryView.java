package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.change_password.LoggedInState;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.query.QueryController;
import interface_adapter.query.QueryState;
import interface_adapter.query.QueryViewModel;

/**
 * The View for when the user is entering a research topic in the program.
 */
public class QueryView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "query";

    private JPanel queryPanel = new JPanel();
    private JTextField queryField = new JTextField(15);
    private JButton search = new JButton("search");;
    private QueryController queryController;

    public QueryView(QueryViewModel queryViewModel) {
        final JLabel title = new JLabel("Search Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel searchInfo = new LabelTextPanel(
                new JLabel(""), queryField);

        queryPanel.add(new JLabel("Enter topic:"));
        queryPanel.add(queryField);
        queryPanel.add(search);

        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(search)) {
                            final QueryState currentState = queryViewModel.getState();

                            queryController.execute(currentState.getTopic());
//                            queryController.switchToResultsView();
                        }
                    }
                }
        );
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

        //this is from loggedinView
//        if (evt.getPropertyName().equals("state")) {
//            final LoggedInState state = (LoggedInState) evt.getNewValue();
//            username.setText(state.getUsername());
//
//            // Reset visibility of the password input field and error field
//            passwordInputField.setVisible(false);
//            passwordErrorField.setVisible(false);
//
//            // Reset visibility of the search input field
//            searchInputField.setVisible(false);
//
//            revalidate();
//            repaint();
//        }
//        else if (evt.getPropertyName().equals("password")) {
//            final LoggedInState state = (LoggedInState) evt.getNewValue();
//            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
//        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setQueryController(QueryController queryController) {
        this.queryController = queryController;
    }

}
