package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

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
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
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
                final Color[] colors = {
                    new Color(173, 216, 230), // Light Blue
                    new Color(144, 238, 144), // Light Green
                    new Color(216, 191, 216), // Light Purple
                    new Color(255, 182, 193), // Light Pink
                    new Color(255, 228, 181)  // Light Orange
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

        signUpPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for proportional scaling
        this.add(signUpPanel, BorderLayout.CENTER);

        // Create reusable GridBagConstraints for layout
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 0;



        // Title Label
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Center across two columns
        signUpPanel.add(title, gbc);

        // Username Label and TextField
        gbc.gridwidth = 1; // Reset grid width
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
        gbc.gridwidth = 2; // Center across two columns
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setBackground(new Color(135, 206, 250)); // Light blue button
        signUp.setForeground(Color.DARK_GRAY);
        signUp.setPreferredSize(new Dimension(150, 30));
        signUpPanel.add(signUp, gbc);

        // Login Hyperlink
        gbc.gridy++;
        final JLabel loginLink = new JLabel("Login");
        loginLink.setForeground(new Color(0, 102, 204)); // Light blue hyperlink color
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
        cancel.setForeground(new Color(0, 102, 204)); // Light blue hyperlink color
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
