public class SummaryViewModel {
    private String summary;

    public SummaryViewModel(String summary) {
        this.summary = summary;
    }

    public void displaySummary() {
        System.out.println("Summary: " + summary);
    }
}

