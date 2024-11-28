package interface_adapter.query;

import java.util.HashSet;
import java.util.Set;

import entity.Article;

/**
 * The state for the Query View Model.
 */
public class QueryState {
    private String topic = "";
    private Set<Article> articles = new HashSet<>();

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articlesSet) {
        this.articles = articlesSet;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
