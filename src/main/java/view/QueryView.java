package view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.query.QueryController;
import interface_adapter.query.QueryState;
import interface_adapter.query.QueryViewModel;
import interface_adapter.results.ResultsController;
import use_case.query.QueryInteractor;

/**
 * The View for when the user is entering a research topic in the program.
 */
public class QueryView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "query";

    private final QueryViewModel queryViewModel;
    private QueryController queryController;
    private QueryInteractor queryInteractor;

    private final LoggedInViewModel loggedInViewModel;

    private ResultsController resultsController;

    private final JTextField queryInputField = new JTextField(15);
    private final JButton search;

    public QueryView(QueryViewModel queryViewModel, LoggedInViewModel loggedInViewModel) {
        this.queryViewModel = queryViewModel;
        this.queryViewModel.addPropertyChangeListener(this);
        this.loggedInViewModel = loggedInViewModel;

        final JLabel title = new JLabel("Query Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel searchInfo = new LabelTextPanel(
                new JLabel("Enter search topic:"), queryInputField);

        final JPanel buttons = new JPanel();
        search = new JButton("Search!");
        buttons.add(search);

        final JLabel cancelLink = new JLabel("Cancel");
        // Light blue hyperlink color
        cancelLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttons.add(cancelLink);

        // Add Mouse Listener to Sign Up Hyperlink
        cancelLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                queryController.switchToLoggedInView();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final LoggedInState loggedInState = loggedInViewModel.getState();
        search.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(search)) {
                        final QueryState currentState = queryViewModel.getState();
                        currentState.setTopic(queryInputField.getText());
                        this.queryController.execute(queryInputField.getText(), loggedInState.getUsername());
                        this.resultsController.execute(
                                currentState.getTopic()
                        );
                    }
                }
        );

        final JPopupMenu popupMenu = new JPopupMenu();

        // ArrayList<String> searchHistories = this.queryController.getSearchHistory(loggedInState.getUsername());

        queryInputField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                queryController.showSearchHistory(loggedInState.getUsername(), queryInputField, popupMenu);
            }
        });

        // queryInputField.addMouseListener(new MouseAdapter() {
        //      @Override
        //      public void mouseClicked(MouseEvent e) {
        //          popupMenu.removeAll(); // Clear existing suggestions
        //          String input = queryInputField.getText().toLowerCase();
        //          for (String history : searchHistories) {
        //              if (history.toLowerCase().contains(input)) {
        //                  JMenuItem item = new JMenuItem(history);
        //                  item.addActionListener(new ActionListener() {
        //                      @Override
        //                      public void actionPerformed(ActionEvent e) {
        //                          queryInputField.setText(history); // Autofill the text field
        //                          popupMenu.setVisible(false); // Hide the popup
        //                      }
        //                  });
        //                  popupMenu.add(item);
        //              }
        //          }
        //          if (popupMenu.getComponentCount() > 0) {
        //              popupMenu.show(queryInputField, 0, queryInputField.getHeight());
        //          }
        //      }
        //  });
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
