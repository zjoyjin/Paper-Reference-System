package data_access;

/**
 * The Interface for Summarization Gateway.
 */
public interface SummarizationGateway {
    // Method declaration (no body)
    /**
     * Gives a summary of the article with the given url.
     * @param url the url of the article.
     * @return a string containing the summary.
     */
    String getSummary(String url);
}
