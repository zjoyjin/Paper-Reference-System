/**
 * The View Model for the summary pannel.
 */
public class SummaryViewModel {
    private String summary;

    public SummaryViewModel(String summary) {
        this.summary = summary;
    }

    /**
     * Prints the summary of the article.
     */
    public void displaySummary() {
        System.out.println("Summary: " + summary);
    }
}

