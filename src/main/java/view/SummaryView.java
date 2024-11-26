import entity.Article;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class SummaryView extends JFrame {

    private JTextArea summaryArea;
    private JTextArea authorsArea;
    private JLabel publicationLabel;
    private JLabel titleLabel;

    public SummaryView() {
        // Set up the frame
        setTitle("Summarization Viewer");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JButton fetchButton = new JButton("Fetch Summary");

        inputPanel.add(fetchButton);

        // Display Panel
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Title: ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        summaryArea = new JTextArea(10, 50);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        summaryArea.setEditable(false);
        JScrollPane summaryScroll = new JScrollPane(summaryArea);

        authorsArea = new JTextArea(5, 50);
        authorsArea.setLineWrap(true);
        authorsArea.setWrapStyleWord(true);
        authorsArea.setEditable(false);
        JScrollPane authorsScroll = new JScrollPane(authorsArea);

        publicationLabel = new JLabel("Publication Year: ");

        displayPanel.add(titleLabel);
        displayPanel.add(new JLabel("Summary:"));
        displayPanel.add(summaryScroll);
        displayPanel.add(new JLabel("Authors:"));
        displayPanel.add(authorsScroll);
        displayPanel.add(publicationLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        // Action listener for the fetch button
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchSummaryData();
            }
        });

        setVisible(true);
    }

    // Method to fetch summary
    private void fetchSummaryData() {
        try {
            // an Article object
            String[] authors = {"Author A", "Author B"};
            String publicationYear = "2022";
            Article article = new Article("1234.5678", "Sample Article Title", authors, publicationYear, new HashSet<>());

            String url = article.getLink();

            // Use CohereSummary
            String content = CohereSummary.fetchUrlContent(url);
            String summary = CohereSummary.summarizeContent(content);

            // Set metadata to GUI
            titleLabel.setText("Title: " + article.getTitle());
            summaryArea.setText(summary);
            authorsArea.setText(String.join(", ", article.getAuthors()));
            publicationLabel.setText("Publication Year: " + article.getPublication());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching summary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new SummaryView();
    }
}
