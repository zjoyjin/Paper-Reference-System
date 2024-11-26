package use_case.results;

/**
 * The Input Data for the Results Use Case.
 */
public class ResultsInputData {
    private String query;

    public ResultsInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
