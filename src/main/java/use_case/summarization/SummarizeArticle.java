package use_case.summarization;

import data_access.SummarizationGateway;

public class SummarizeArticle {
    private final SummarizationGateway gateway;

    public SummarizeArticle(SummarizationGateway gateway) {
        this.gateway = gateway;
    }

    public String summarize(String url) {
        return gateway.getSummary(url);
    }
}

