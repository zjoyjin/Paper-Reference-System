package interface_adapter.summary;

import data_access.CohereSummarizationGateway;
import use_case.summarization.SummarizeArticle;

public class CohereSummaryApp {
    public static void main(String[] args) {

        String apiKey = "2YU85SBd4B0DOUxLoCHRbbuagFRqzJWz7oeJx26u";
        CohereSummarizationGateway gateway = new CohereSummarizationGateway(apiKey);
        SummarizeArticle summarizeArticle = new SummarizeArticle(gateway);
        String urlToSummarize = "https://www.scienceabc.com/nature/bananas-change-colour-upon-ripening.html";

        String summary = summarizeArticle.summarize(urlToSummarize);
        SummaryViewModel viewModel = new SummaryViewModel(summary);

        // Display the summary
        viewModel.displaySummary();
    }
}

