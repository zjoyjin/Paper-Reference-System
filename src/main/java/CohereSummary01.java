import com.cohere.api.Cohere;
import com.cohere.api.core.CohereApiError;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.*;
public class CohereSummary01 {
    private static final String API_KEY = "2YU85SBd4B0DOUxLoCHRbbuagFRqzJWz7oeJx26u";

    public static String getSummary(String urlToSummarize) {
        Cohere cohere = Cohere.builder().token(API_KEY).clientName("snippet").build();
        try {
            NonStreamedChatResponse response = cohere.chat(
                    ChatRequest.builder()
                            .message("I have a URL, and I would like a detailed summary of its content. "
                                    + "Please analyze the article at the following link: "
                                    + urlToSummarize
                                    + ". Focus on summarizing the main points, key details, and any notable insights provided.").build());
            String fullSummary = response.getText();
            System.out.println("Full Summary: " + fullSummary);
            return fullSummary;
        }
        catch (CohereApiError e) {
            System.err.println("Cohere API Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
        return "Error in summarizing content.";

    }

    public static void main(String[] args) {
        // Example usage:
        String urlToSummarize = "https://www.scienceabc.com/nature/bananas-change-colour-upon-ripening.html";
        String summary = getSummary(urlToSummarize);

        // Create the view model and pass the summary
        SummaryViewModel viewModel = new SummaryViewModel(summary);
        viewModel.displaySummary();
    }
}

