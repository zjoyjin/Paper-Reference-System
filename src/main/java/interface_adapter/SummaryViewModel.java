package interface_adapter;

/**
 * The View Model for the Summary View.
 */
public class SummaryViewModel {
    private String summary;

    public SummaryViewModel(String summary) {
        this.summary = summary;
    }

    /**
     * Displays a summary of the article.
     */
    public void displaySummary() {
        System.out.println("Summary: " + summary);
    }
}

