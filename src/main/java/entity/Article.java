package entity;

import java.util.Set;

/**
 * This class represents the information about a research paper,
 * and represents an Article node in our directed graph.
 * It details the link to the article as well as the article's
 * doi, title, authors, publication date, and references.
 */
public class Article {
    private final String link;
    private final String doi;
    private final String title;
    private final String[] authors;
    private final String publication;
    private final Set<String> references;

    public Article(String doi, String title, String[] authors, String publication, Set<String> references) {
        this.link = "https://doi.org/" + doi;
        this.doi = doi;
        this.title = title;
        this.authors = authors;
        this.publication = publication;
        this.references = references;
    }

    public String getLink() {
        return link;
    }

    public String getDoi() {
        return doi;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getPublication() {
        return publication;
    }

    public Set<String> getReferences() {
        return references;
    }

    /**
     * Checks if this article has a reference to the given article.
     * @param article the article to check for a reference to
     * @return whether this article contains a reference to the given article
     */
    public boolean hasReference(Article article) {
        return this.references.contains(article.getDoi());
    }

}
