public class SummaryViewModel {
    private String summary;
    //3
    public SummaryViewModel(String summary) {
        this.summary = summary;
    }

    public void displaySummary() {
        System.out.println("Summary: " + summary);
    }
}

