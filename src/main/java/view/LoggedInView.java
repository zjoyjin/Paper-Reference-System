package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.login.LoginController;
import interface_adapter.query.QueryController;
import interface_adapter.query.QueryViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final QueryViewModel queryViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private LoginController loginController;
    private QueryController queryController;

    private final JLabel username;

    private final JButton logOut;

    private final JTextField passwordInputField = new JTextField(15);
    private final JButton changePassword;

    private final JTextField searchInputField = new JTextField(15);
    private final JButton search;

    public LoggedInView(LoggedInViewModel loggedInViewModel, QueryViewModel queryViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.queryViewModel = queryViewModel;
        this.queryViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(""), passwordInputField);
        // Hide password input field until change password button pressed
        passwordInputField.setVisible(false);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final LabelTextPanel searchInfo = new LabelTextPanel(new JLabel(""), searchInputField);
        // Hide search input field until change password button pressed
        searchInputField.setVisible(false);

        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);

        changePassword = new JButton("Change Password");
        buttons.add(changePassword);

        search = new JButton("Search");
        buttons.add(search);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoggedInState currentState = loggedInViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                loggedInViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        searchInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoggedInState currentState = loggedInViewModel.getState();
                currentState.setTopic(searchInputField.getText());
                loggedInViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        changePassword.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        if (passwordInputField.isVisible()) {
                            // Allow user to change password
                            final LoggedInState currentState = loggedInViewModel.getState();

                            this.changePasswordController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                        else {
                            // Make password input & password error field visible again
                            passwordInputField.setVisible(!passwordInputField.isVisible());
                        }
                        revalidate();
                        repaint();
                    }
                }
        );

        logOut.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(logOut)) {
                        final LoggedInState currentState = loggedInViewModel.getState();

                        this.logoutController.execute(
                                currentState.getUsername()
                        );
                        revalidate();
                        repaint();
                    }
                }
        );

        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                queryController.switchToQueryView();
                System.out.println("HELP");
            }
        });

        search.addActionListener(
                evt -> {
                    System.out.println("Search button clicked");
                    if (this.queryController != null) {
                        this.queryController.switchToQueryView();
                        System.out.println("Switched to query view");
                    }
                    else {
                        System.err.println("QueryController is null!");
                    }
                }
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        loginController.switchToQueryView();
//                    }
//                }
                // This creates an anonymous subclass of ActionListener and instantiates it.
//                evt -> {
//                    if (evt.getSource().equals(search)) {
//                        if (searchInputField.isVisible()) {
//                            // Allow user to enter a research topic in search bar
//                            String topic = searchInputField.getText().trim();
//                            if (!topic.isEmpty()) {
//                                queryController.execute(topic);
//                                queryController.switchToResultsView();
//                            }
//                            else {
//                                JOptionPane.showMessageDialog(
//                                        this, "Please enter a valid search term");
//                            }
////                            final LoggedInState currentState = loggedInViewModel.getState();
////                            this.queryController.execute(currentState.getTopic());
//                        }
//                        else {
//                            // Make search input field and label visible again
//                            searchInputField.setVisible(!searchInputField.isVisible());
//                        }
//                        revalidate();
//                        repaint();
//                    }
//                }
        );

        this.add(title);
        this.add(usernameInfo);
        this.add(username);

        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(searchInfo);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
//            String newView = (String) evt.getNewValue();
//            if ("search".equals(newView)) {
//                System.out.println("Switching to search (QueryView)");
//                switchToQueryView();
//            }

            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());

            // Reset visibility of the password input field and error field
            passwordInputField.setVisible(false);
            passwordErrorField.setVisible(false);

            // Reset visibility of the search input field
            searchInputField.setVisible(false);

            revalidate();
            repaint();
        }
        else if (evt.getPropertyName().equals("password")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setQueryController(QueryController queryController) {
        this.queryController = queryController;
    }
}
