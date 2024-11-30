package view;

import java.awt.*;
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
    private static final int TOP = 5;
    private static final int LEFT = 10;
    private static final int BOTTOM = 5;
    private static final int RIGHT = 10;
    private static final int SIZE = 20;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 30;

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

    private final String viewName = "query";

    private final QueryViewModel queryViewModel;
    private QueryController queryController;
    private QueryInteractor queryInteractor;

    private final LoggedInViewModel loggedInViewModel;

    private ResultsController resultsController;

    private final JTextField queryInputField = new JTextField(20);
    private final JButton search;

    public QueryView(QueryViewModel queryViewModel, LoggedInViewModel loggedInViewModel) {
        this.queryViewModel = queryViewModel;
        this.queryViewModel.addPropertyChangeListener(this);
        this.loggedInViewModel = loggedInViewModel;

        final JLabel title = new JLabel("Paper Reference System: ");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        final JPanel queryPanel = new JPanel() {
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
        queryPanel.setLayout(new GridBagLayout());
        this.add(queryPanel, BorderLayout.CENTER);

        this.setBackground(new Color(251, 242, 234));

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
        queryPanel.add(title, gbc);


        gbc.gridy++;
        gbc.gridx = 0;
        final LabelTextPanel searchInfo = new LabelTextPanel(
                new JLabel("Enter search topic:"), queryInputField);
        searchInfo.setBackground(new Color(251, 242, 234));




        gbc.gridy = 4;
        gbc.gridx = 0;
        search = new JButton("Search!");
        search.setBackground(new Color(LIGHT_BLUE_BUTTON_R, LIGHT_BLUE_BUTTON_G, LIGHT_BLUE_BUTTON_B));
        search.setForeground(Color.DARK_GRAY);
        search.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        queryPanel.add(search);

        gbc.gridy = 4;
        gbc.gridx = 1;
        final JLabel cancelLink = new JLabel("Cancel");
        // Light blue hyperlink color
        cancelLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        queryPanel.add(cancelLink);

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
//        this.add(quer);
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
