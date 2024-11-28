package use_case.query;
import entity.User;

/**
 * The Input Data for the Query Use Case.
 */
public class QueryInputData {
    private final String topic;
    private final User user;

    public QueryInputData(String topic, User user) {
        this.topic = topic; this.user = user;
    }

    String getTopic() {
        return topic;
    }

    User getUser() { return user; }

}
