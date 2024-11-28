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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    // Colours
    private static final int LIGHT_BLUE_R = 173;
    private static final int LIGHT_BLUE_G = 216;
    private static final int LIGHT_BLUE_B = 230;
    private static final int LIGHT_GREEN_R = 144;
    private static final int LIGHT_GREEN_G = 238;
    private static final int LIGHT_GREEN_B = 144;
    private static final int LIGHT_PURPLE_R = 216;
    private static final int LIGHT_PURPLE_G = 191;
    private static final int LIGHT_PURPLE_B = 216;
    private static final int LIGHT_PINK_R = 255;
    private static final int LIGHT_PINK_G = 182;
    private static final int LIGHT_PINK_B = 193;
    private static final int LIGHT_ORANGE_R = 255;
    private static final int LIGHT_ORANGE_G = 228;
    private static final int LIGHT_ORANGE_B = 181;
    private static final int LIGHT_BLUE_BUTTON_R = 135;
    private static final int LIGHT_BLUE_BUTTON_G = 206;
    private static final int LIGHT_BLUE_BUTTON_B = 250;
    private static final int LIGHT_BLUE_LINK_R = 0;
    private static final int LIGHT_BLUE_LINK_G = 102;
    private static final int LIGHT_BLUE_LINK_B = 204;
    private static final int TOP = 10;
    private static final int LEFT = 10;
    private static final int BOTTOM = 10;
    private static final int RIGHT = 10;
    private static final int SIZE = 28;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 30;

    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController;

    private final JButton signUp;
    //    private final JButton cancel;
    //    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Custom JPanel with Lighter Tone Multicolor Gradient Background
        final JPanel signUpPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2d = (Graphics2D) g;

                // Define lighter tones of the colors for the gradient
                // Light Blue, Light Green, Light Purple, Light Pink, Light Orange
                final Color[] colors = {
                    new Color(LIGHT_BLUE_R, LIGHT_BLUE_G, LIGHT_BLUE_B),
                    new Color(LIGHT_GREEN_R, LIGHT_GREEN_G, LIGHT_GREEN_B),
                    new Color(LIGHT_PURPLE_R, LIGHT_PURPLE_G, LIGHT_PURPLE_B),
                    new Color(LIGHT_PINK_R, LIGHT_PINK_G, LIGHT_PINK_B),
                    new Color(LIGHT_ORANGE_R, LIGHT_ORANGE_G, LIGHT_ORANGE_B)};

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
        signUpPanel.setLayout(new GridBagLayout());
        this.add(signUpPanel, BorderLayout.CENTER);

        // Create reusable GridBagConstraints for layout
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(TOP, LEFT, BOTTOM, RIGHT);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 0;

        // Title Label
        title.setFont(new Font("Arial", Font.BOLD, SIZE));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Center across two columns
        gbc.gridwidth = 2;
        signUpPanel.add(title, gbc);

        // Username Label and TextField
        // Reset grid width
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLabel.setForeground(Color.DARK_GRAY);
        signUpPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        signUpPanel.add(usernameInputField, gbc);

        // Password Label and PasswordField
        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLabel.setForeground(Color.DARK_GRAY);
        signUpPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        signUpPanel.add(passwordInputField, gbc);

        // RepeatPassword Label and PasswordField
        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel repeatpassLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatpassLabel.setForeground(Color.DARK_GRAY);
        signUpPanel.add(repeatpassLabel, gbc);

        gbc.gridx = 1;
        signUpPanel.add(repeatPasswordInputField, gbc);

        // sign up Button
        gbc.gridy++;
        gbc.gridx = 0;
        // Center across two columns
        gbc.gridwidth = 2;
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        // Light blue button
        signUp.setBackground(new Color(LIGHT_BLUE_BUTTON_R, LIGHT_BLUE_BUTTON_G, LIGHT_BLUE_BUTTON_B));
        signUp.setForeground(Color.DARK_GRAY);
        signUp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        signUpPanel.add(signUp, gbc);

        // Login Hyperlink
        gbc.gridy++;
        final JLabel loginLink = new JLabel("Login");
        // Light blue hyperlink color
        loginLink.setForeground(new Color(LIGHT_BLUE_LINK_R, LIGHT_BLUE_LINK_G, LIGHT_BLUE_LINK_B));
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signUpPanel.add(loginLink, gbc);

        // Add Mouse Listener to Sign Up Hyperlink
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                signupController.switchToLoginView();
            }

        });

        gbc.gridx = 2;
        final JLabel cancel = new JLabel(SignupViewModel.CANCEL_BUTTON_LABEL);
        // Light blue hyperlink color
        cancel.setForeground(new Color(LIGHT_BLUE_LINK_R, LIGHT_BLUE_LINK_G, LIGHT_BLUE_LINK_B));
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signUpPanel.add(cancel, gbc);

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usernameInputField.setText("");
                passwordInputField.setText("");
                repeatPasswordInputField.setText("");
            }

        });

        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            final SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
