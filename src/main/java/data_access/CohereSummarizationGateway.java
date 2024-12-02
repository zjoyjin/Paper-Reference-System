package data_access;

import com.cohere.api.Cohere;
import com.cohere.api.core.CohereApiError;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;

public class CohereSummarizationGateway implements SummarizationGateway {
    private final Cohere cohere;

    public CohereSummarizationGateway(String apiKey) {
        this.cohere = Cohere.builder().token(apiKey).clientName("snippet").build();
    }

    @Override
    public String getSummary(String url) {
        try {
            NonStreamedChatResponse response = cohere.chat(
                    ChatRequest.builder()
                            .message("I have a URL, and I would like a detailed summary of its content. "
                                    + "Please analyze the article at the following link: "
                                    + url
                                    + ". Focus on summarizing the main points, key details, and any notable insights provided.").build());
            return response.getText();
        } catch (CohereApiError e) {
            System.err.println("Cohere API Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
        return "Error in summarizing content.";
    }
}
