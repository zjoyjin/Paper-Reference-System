package view;

import java.awt.Component;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import data_access.CohereSummary01;
import entity.Article;

/**
 * The Panel for the Summary Use Case.
 */
public class SummaryPanel extends JPanel {
    private static final int ROWS = 10;
    private static final int COLS = 30;
    private static final int FONT_SIZE_16 = 16;
    private static final int FONT_SIZE_14 = 14;
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 500;
    private static final String ARIAL = "Arial";
    public final JLabel titleLabel;
    public final JLabel authorsLabel;
    public final JLabel publicationLabel;
    public final JTextArea summaryTextArea;

    // initialize the panel aitcle details and the summary
    public SummaryPanel(String title, Set<String> authors, String publication, String url) {
        // Set up the layout for the panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        titleLabel = new JLabel("Title: " + title);
        titleLabel.setFont(new Font(ARIAL, Font.BOLD, FONT_SIZE_16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Authors
        final String authorsText = "Authors: " + String.join(", ", authors);
        authorsLabel = new JLabel(authorsText);
        authorsLabel.setFont(new Font(ARIAL, Font.PLAIN, FONT_SIZE_14));
        authorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Publication
        publicationLabel = new JLabel("Publication Year: " + publication);
        publicationLabel.setFont(new Font(ARIAL, Font.PLAIN, FONT_SIZE_14));
        publicationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Summary (scrollable)
        summaryTextArea = new JTextArea(ROWS, COLS);
        final String summary = CohereSummary01.getSummary(url);
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

    /**
     * The entry point of the application.
     * This method demonstrates creating an article, summarizing it, and displaying its data in a GUI.
     * @param args unused arguments.
     */
    public static void main(String[] args) {
        // Create Article
        // replace this later
        final String[] authors = {"Author A", "Author B"};
        // replace this with article.getpublication year
        final String publicationYear = "2024";
        final Article article = new Article("1234.5678",
                "Sample Article Title", authors, publicationYear, new HashSet<>());

        // Use the link
        // String urlToSummarize =
        // "https://www.scienceabc.com/nature/bananas-change-colour-upon-ripening.html";//article.getLink();
        final String urlToSummarize = "https://doi.org/10.1016/j.gendis.2022.02.020";
        // CohereSummary
        // Call the CohereSummary API to get the summary
        final String summary = CohereSummary01.getSummary(urlToSummarize);

        // Set metadata to the GUI components
        final JFrame frame = new JFrame("Article Summary");
        final SummaryPanel panel = new SummaryPanel(article.getTitle(),
                Set.of(article.getAuthors()), article.getPublication(), summary);

        // Set up the frame to display the panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }

}
