package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import interface_adapter.login.LoginController;
import interface_adapter.logout.LogoutController;
import interface_adapter.query.QueryController;
import interface_adapter.query.QueryViewModel;
import interface_adapter.results.ResultsController;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private static final int TEN = 10;
    private static final int FONT_SIZE = 28;
    private static final int LIGHT_BLUE_R = 135;
    private static final int LIGHT_BLUE_G = 206;
    private static final int LIGHT_BLUE_B = 250;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 30;
    private static final int COLUMN = 15;
    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final QueryViewModel queryViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private LoginController loginController;
    private QueryController queryController;
    private ResultsController resultsController;

    private final JLabel username;

    private final JTextField passwordInputField;

    // private final JTextField searchInputField = new JTextField(15);

    public LoggedInView(LoggedInViewModel loggedInViewModel, QueryViewModel queryViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.queryViewModel = queryViewModel;
        this.queryViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        // Custom JPanel with Lighter Multicolor Gradient Background
        final JPanel loggedinPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2d = (Graphics2D) g;

                // Define lighter tones of the colors for the gradient
                final Color[] colors = {
                    // Light Orange, Yellow Blue, Red (Pinkish), Pink
                    new Color(255, 200, 140),
                    new Color(255, 255, 200),
                    new Color(173, 216, 230),
                    new Color(255, 182, 193),
                    new Color(255, 192, 203),
                };

                final float[] fractions = {0.0f, 0.25f, 0.5f, 0.75f, 1.0f};

                // Create a LinearGradientPaint for the multicolor background
                final LinearGradientPaint gradient = new LinearGradientPaint(
                        0, 0, getWidth(), getHeight(), fractions, colors
                );

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        this.setLayout(new BorderLayout());
        // Use GridBagLayout for proportional scaling
        loggedinPanel.setLayout(new GridBagLayout());
        this.add(loggedinPanel, BorderLayout.CENTER);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(TEN, TEN, TEN, TEN);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 0;

        // Title Label
        final JLabel titleLabel = new JLabel(viewName);
        titleLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loggedinPanel.add(titleLabel, gbc);

        // Logout Button
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;

        final JButton logOut = new JButton("Log Out");
        // Light blue button
        logOut.setBackground(new Color(LIGHT_BLUE_R, LIGHT_BLUE_G, LIGHT_BLUE_B));
        logOut.setForeground(Color.DARK_GRAY);
        logOut.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loggedinPanel.add(logOut, gbc);

        gbc.gridx = 1;

        final JButton changePassword = new JButton("Change Password");
        // Light blue button
        changePassword.setBackground(new Color(LIGHT_BLUE_R, LIGHT_BLUE_G, LIGHT_BLUE_B));
        changePassword.setForeground(Color.DARK_GRAY);
        changePassword.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loggedinPanel.add(changePassword, gbc);

        gbc.gridx = 2;

        final JButton search = new JButton("Go to Search");
        // Light blue button
        search.setBackground(new Color(LIGHT_BLUE_R, LIGHT_BLUE_G, LIGHT_BLUE_B));
        search.setForeground(Color.DARK_GRAY);
        search.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loggedinPanel.add(search, gbc);

        // instantiate the hidden password field
        // Reset grid width
        gbc.gridy++;
        gbc.gridx = 1;
        passwordInputField = new JTextField(COLUMN);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(""), passwordInputField);
        // Hide password input field until change password button pressed
        passwordInputField.setVisible(false);
        loggedinPanel.add(passwordInputField, gbc);

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

        // searchInputField.getDocument().addDocumentListener(new DocumentListener() {
        //    private void documentListenerHelper() {
        //        final LoggedInState currentState = loggedInViewModel.getState();
        //        currentState.setTopic(searchInputField.getText());
        //        loggedInViewModel.setState(currentState);
        //    }
        //
        //    @Override
        //    public void insertUpdate(DocumentEvent e) {
        //        documentListenerHelper();
        //    }

        //    @Override
        //    public void removeUpdate(DocumentEvent e) {
        //        documentListenerHelper();
        //    }

        //    @Override
        //    public void changedUpdate(DocumentEvent e) {
        //        documentListenerHelper();
        //    }
        // });

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
                            passwordInputField.setVisible(false);
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

        search.addActionListener(
                evt -> {
                    if (this.queryController != null) {
                        this.queryController.switchToQueryView();
                    }
                    else {
                        System.err.println("ResultsController is null!");
                    }
                }
        );

        loggedinPanel.add(title);
        loggedinPanel.add(usernameInfo);
        loggedinPanel.add(username);

        loggedinPanel.add(passwordInfo);
        loggedinPanel.add(passwordErrorField);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {

            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());

            // Reset visibility of the password input field and error field
            passwordInputField.setVisible(false);
            passwordErrorField.setVisible(false);

            // Reset visibility of the search input field

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

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }
}
