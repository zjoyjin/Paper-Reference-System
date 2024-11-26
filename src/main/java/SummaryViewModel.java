public class SummaryViewModel {
    private String summary;
    //check
    public SummaryViewModel(String summary) {
        this.summary = summary;
    }

    public void displaySummary() {
        System.out.println("Summary: " + summary);
    }
}

