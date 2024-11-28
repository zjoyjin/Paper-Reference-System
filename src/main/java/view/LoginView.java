package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

//    private final JButton logIn;

    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Login Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

//        final LabelTextPanel usernameInfo = new LabelTextPanel(
//                new JTextField(15);
//        final LabelTextPanel passwordInfo = new LabelTextPanel(
//                new JPasswordField(15);





        // Custom JPanel with Lighter Multicolor Gradient Background
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Define lighter tones of the colors for the gradient
                Color[] colors = {
                        new Color(255, 200, 140), // Light Orange
                        new Color(255, 255, 200), // Light Yellow
                        new Color(173, 216, 230), // Light Blue
                        new Color(255, 182, 193), // Light Red (Pinkish)
                        new Color(255, 192, 203)  // Light Pink
                };


                float[] fractions = {0.0f, 0.25f, 0.5f, 0.75f, 1.0f};

                // Create a LinearGradientPaint for the multicolor background
                LinearGradientPaint gradient = new LinearGradientPaint(
                        0, 0, getWidth(), getHeight(), fractions, colors
                );

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        loginPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for proportional scaling


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 0;

        // Title Label
        JLabel titleLabel = new JLabel(viewName);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Center across two columns
        loginPanel.add(titleLabel, gbc);

        // Username Label and TextField
        gbc.gridwidth = 1; // Reset grid width
        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.DARK_GRAY);
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;

        loginPanel.add(usernameInputField, gbc);

        // Password Label and PasswordField
        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.DARK_GRAY);
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;

        loginPanel.add(passwordInputField, gbc);


        // Login Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Center across two columns
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(135, 206, 250)); // Light blue button
        loginButton.setForeground(Color.DARK_GRAY);
        loginButton.setPreferredSize(new Dimension(150, 30));
        loginPanel.add(loginButton, gbc);

        // Sign Up Hyperlink
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel cancelLink = new JLabel("Cancel");
        cancelLink.setForeground(new Color(0, 102, 204)); // Light blue hyperlink color
        cancelLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(cancelLink, gbc);

        // Add Mouse Listener to Sign Up Hyperlink
        cancelLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginController.switchToSignupView();
            }
        });

        loginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(loginButton)) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

//        cancel.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        loginController.switchToSignupView();
//                    }
//                }
//        );

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
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


        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
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


        this.add(loginPanel);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
