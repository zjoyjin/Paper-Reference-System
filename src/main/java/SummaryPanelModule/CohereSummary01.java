package SummaryPanelModule;

import interface_adapter.SummaryViewModel;
import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;

public class CohereSummary01 {
    private static final String API_KEY = "2YU85SBd4B0DOUxLoCHRbbuagFRqzJWz7oeJx26u";

    public static String getSummary(String urlToSummarize) {
        Cohere cohere = Cohere.builder().token(API_KEY).clientName("snippet").build();
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("I have a URL, and I would like a detailed summary of its content. "
                                + "Please analyze the article at the following link: "
                                + urlToSummarize
                                + ". Focus on summarizing the main points, key details, and any notable insights provided.").build());

        String fullSummary = response.getText();
        System.out.println("Full Summary: " + fullSummary); // Get the full summary from the API response

        return fullSummary;
    }

    public static void main(String[] args) {
        // Example usage:
//        String urlToSummarize = "https://www.scienceabc.com/nature/bananas-change-colour-upon-ripening.html";
//        String urlToSummarize = "https://doi.org/10.1016/j.gendis.2022.02.020";
          String urlToSummarize = "https://www.sciencedirect.com/science/article/pii/S2352304222000782?via%3Dihub";
//        String urlToSummarize = "https://doi.org/10.1016/j.cub.2014.07.034";
        String summary = getSummary(urlToSummarize);

        // Create the view model and pass the summary
        SummaryViewModel viewModel = new SummaryViewModel(summary);
        viewModel.displaySummary();
    }
}

