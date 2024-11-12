package use_case.request_data;

import entity.Article;

/**
 * DAO for the Api Data Request Use Case.
 */
public interface ApiDataAccessInterface {
    /**
     * Returns the set of articles matching the current query.
     * @param sortType how the results should be sorted
     * @param query the username to look up
     * @return the set of articles.
     */
    Set<Article> get(String sortType, String query);

    /**
     * Returns the set of articles matching the current query in ascending order by relevance.
     * @param query the username to look up
     * @return the set of articles.
     */
    default Set<Article> getByRelevance(String query) {
        return get("relevance", query);
    }

}
