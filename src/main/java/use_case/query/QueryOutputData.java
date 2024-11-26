package use_case.query;

/**
 * Output Data for the Query Use Case.
 */
public class QueryOutputData {
    private final String topic;
    private final boolean useCaseFailed;

    public QueryOutputData(String topic, boolean useCaseFailed) {
        this.topic = topic;
        this.useCaseFailed = useCaseFailed;
    }

    public String getTopic() {
        return topic;
    }
}
