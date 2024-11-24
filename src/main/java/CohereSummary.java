import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CohereSummary {
    private static final String API_KEY = "2YU85SBd4B0DOUxLoCHRbbuagFRqzJWz7oeJx26u";
    private static final String API_URL = "https://api.cohere.com/v2/chat";

    public static void main(String[] args) {
        // URL you want to summarize
        String urlToSummarize = "https://www.scienceabc.com/nature/bananas-change-colour-upon-ripening.html";

        try {
            String content = fetchUrlContent(urlToSummarize);
            String summary = summarizeContent(content);
            System.out.println("Summary: " + summary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch content from a URL
    public static String fetchUrlContent(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the content of the URL
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            StringBuilder content = new StringBuilder();
            while (scanner.hasNext()) {
                content.append(scanner.nextLine());
            }
            return content.toString();
        }
    }

    // Call Cohere API to summarize the content
    public static String summarizeContent(String content) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);

        // Create the JSON body for the request
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("text", content);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read the API response
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getString("summary");
        }
    }
}

