package entity;

/**
 * This class represents the edge that connects two Article nodes,
 * where one article (paper) cites the other (reference).
 */
public class Edge {
    private final Article paper;
    private final Article reference;

    public Edge(Article paper, Article reference) {
        this.paper = paper;
        this.reference = reference;
    }

    public Article getPaper() {
        return paper;
    }

    public Article getReference() {
        return reference;
    }

}
