package use_case.query;

/**
 * The Input Data for the Query Use Case.
 */
public class QueryInputData {
    private final String topic;
    private final String username;

    public QueryInputData(String topic, String username) {
        this.topic = topic;
        this.username = username;
    }

    String getTopic() {
        return topic;
    }

    String getUsername() { return username; }

}
