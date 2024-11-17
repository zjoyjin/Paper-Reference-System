package use_case.query;

/**
 * The Input Data for the Query Use Case.
 */
public class QueryInputData {
    private final String topic;

    public QueryInputData(String topic) {
        this.topic = topic;
    }

    String getTopic() {
        return topic;
    }

}
