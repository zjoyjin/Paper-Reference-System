package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final int TEN = 10;
    private static final int FONT_SIZE = 28;
    private static final int LIGHT_BLUE_R = 135;
    private static final int LIGHT_BLUE_G = 206;
    private static final int LIGHT_BLUE_B = 250;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 30;
    private static final int LB_HYPERLINK_G = 102;
    private static final int LB_HYPERLINK_B = 204;

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    // private final JButton logIn;

    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Login Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // final LabelTextPanel usernameInfo = new LabelTextPanel(
        //        new JTextField(15);
        // final LabelTextPanel passwordInfo = new LabelTextPanel(
        //        new JPasswordField(15);

        // Custom JPanel with Lighter Multicolor Gradient Background
        final JPanel loginPanel = new JPanel() {
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

                repaint();
            }
        };
        this.setLayout(new BorderLayout());
        loginPanel.setLayout(new GridBagLayout());
        this.add(loginPanel, BorderLayout.CENTER);

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
        loginPanel.add(titleLabel, gbc);

        // Username Label and TextField
        // Reset grid width
        gbc.gridwidth = 1;
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
        // Center across two columns
        gbc.gridwidth = 2;
        final JButton loginButton = new JButton("Login");
        // Light blue button
        loginButton.setBackground(new Color(LIGHT_BLUE_R, LIGHT_BLUE_G, LIGHT_BLUE_B));
        loginButton.setForeground(Color.DARK_GRAY);
        loginButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loginPanel.add(loginButton, gbc);

        // Sign Up Hyperlink
        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel cancelLink = new JLabel("Cancel");
        // Light blue hyperlink color
        cancelLink.setForeground(new Color(0, LB_HYPERLINK_G, LB_HYPERLINK_B));
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

        // cancel.addActionListener(
        //        new ActionListener() {
        //            public void actionPerformed(ActionEvent evt) {
        //                loginController.switchToSignupView();
        //            }
        //        }
        // );

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
