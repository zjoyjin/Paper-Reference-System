package SummaryPanelModule;

import entity.Article;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SummaryPanel extends JPanel {
    public final JLabel titleLabel;
    public final JLabel authorsLabel;
    public final JLabel publicationLabel;
    public final JTextArea summaryTextArea;
    
    // initialize the panel aitcle details and the summary
    public SummaryPanel(String title, Set<String> authors, String publication, String URL) {
        // Set up the layout for the panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        titleLabel = new JLabel("Title: " + title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Authors
        final String authorsText = "Authors: " + String.join(", ", authors);
        authorsLabel = new JLabel(authorsText);
        authorsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        authorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Publication
        publicationLabel = new JLabel("Publication Year: " + publication);
        publicationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        publicationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Summary (scrollable)
        summaryTextArea = new JTextArea(10, 30);
        final String summary = CohereSummary01.getSummary(URL);
        summaryTextArea.setText(summary);
        summaryTextArea.setWrapStyleWord(true);
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel
        add(titleLabel);
        add(authorsLabel);
        add(publicationLabel);
        add(scrollPane);
    }

    public static void main(String[] args) {
        // Create Article
        // replace this later
        final String[] authors = {"Author A", "Author B"};
        // replace this with article.getpublication year
        final String publicationYear = "2022";
        final Article article = new Article("1234.5678", "Sample Article Title", authors, publicationYear, new HashSet<>());

        // Use the link
//      String urlToSummarize =
//                             "https://www.scienceabc.com/nature/bananas-change-colour-upon-ripening.html";//article.getLink();
        final String urlToSummarize = "https://doi.org/10.1016/j.gendis.2022.02.020";
        // CohereSummary
        // Call the CohereSummary API to get the summary
        final String summary = CohereSummary01.getSummary(urlToSummarize);

        // Set metadata to the GUI components
        final JFrame frame = new JFrame("Article Summary");
        final SummaryPanel panel = new SummaryPanel(article.getTitle(), Set.of(article.getAuthors()), article.getPublication(), summary);

        // Set up the frame to display the panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }

}


