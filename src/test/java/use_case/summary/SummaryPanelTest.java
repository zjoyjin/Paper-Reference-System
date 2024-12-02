package use_case.summary;

import view.SummaryPanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.*;
import java.util.Set;

public class SummaryPanelTest {

    @Test
    public void testSummaryPanelAuthorsOrder() {

        Set<String> authors = Set.of("Author B", "Author A");


        String expectedAuthorsText = "Authors: Author A, Author B";


        String title = "Sample Article Title";
        String publicationYear = "2022";
        String URL = "https://www.example.com";


        SummaryPanel panel = new SummaryPanel(title, authors, publicationYear, URL);

        String actualAuthorsText = panel.authorsLabel.getText();

        assertEquals(expectedAuthorsText, actualAuthorsText, "The authors' order should be sorted alphabetically.");
    }

    @Test
    public void testTitleDisplay() {
        // Given article title
        String title = "Sample Article Title";

        // Create the SummaryPanel
        Set<String> authors = Set.of("Author A", "Author B");
        String publicationYear = "2024";
        String URL = "https://www.example.com";
        SummaryPanel panel = new SummaryPanel(title, authors, publicationYear, URL);

        // Check that the title is correctly displayed in the panel
        String actualTitleText = panel.titleLabel.getText();
        assertEquals("Title: " + title, actualTitleText, "The title should be displayed correctly.");
    }

    @Test
    public void testPublicationDisplay() {
        // Given article publication year
        String publicationYear = "2022";

        // Create the SummaryPanel
        Set<String> authors = Set.of("Author A", "Author B");
        String title = "Sample Article Title";
        String URL = "https://www.example.com";
        SummaryPanel panel = new SummaryPanel(title, authors, publicationYear, URL);

        // Check that the publication year is correctly displayed in the panel
        String actualPublicationText = panel.publicationLabel.getText();
        assertEquals("Publication Year: " + publicationYear, actualPublicationText, "The publication year should be displayed correctly.");
    }


    @Test
    public void testComponentAlignment() {

        Set<String> authors = Set.of("Author A", "Author B");
        String title = "Sample Article Title";
        String publicationYear = "2022";
        String URL = "https://www.example.com";

        SummaryPanel panel = new SummaryPanel(title, authors, publicationYear, URL);

        assertTrue(panel.titleLabel.getAlignmentX() == Component.CENTER_ALIGNMENT, "The title label should be centered.");

        assertTrue(panel.authorsLabel.getAlignmentX() == Component.CENTER_ALIGNMENT, "The authors label should be centered.");

        assertTrue(panel.publicationLabel.getAlignmentX() == Component.CENTER_ALIGNMENT, "The publication label should be centered.");

        assertTrue(panel.summaryTextArea.getAlignmentX() == Component.CENTER_ALIGNMENT, "The summary text area should be centered.");
    }

    @Test
    public void testSummaryPanelUIComponents() {
        Set<String> authors = Set.of("Author A", "Author B");
        String title = "Sample Article Title";
        String publicationYear = "2022";
        String URL = "https://www.example.com";

        SummaryPanel panel = new SummaryPanel(title, authors, publicationYear, URL);

        assertTrue(panel.titleLabel.isVisible(), "The title label should be visible.");

        assertTrue(panel.authorsLabel.isVisible(), "The authors label should be visible.");

        assertTrue(panel.publicationLabel.isVisible(), "The publication label should be visible.");

        assertTrue(panel.summaryTextArea.isVisible(), "The summary text area should be visible.");
    }

}
