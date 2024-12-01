package data_access;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Article;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.query.QueryDataAccessInterface;
import use_case.results.ResultsDataAccessInterface;

/**
 * The DAO for user data.
 */
public class QueryDataAccessObject implements QueryDataAccessInterface, ResultsDataAccessInterface {
    private static final String SUCCESS_CODE = "ok";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status";
    private static final String MESSAGE = "message";
    private static final String ITEMS = "items";

    private static final String DOI = "DOI";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String GIVEN = "given";
    private static final String FAMILY = "family";
    private static final String DATE = "published";
    private static final String DATE_PARTS = "date-parts";
    private static final String REFERENCE = "reference";

    private static final int NUM_OUTPUTS = 20;

    private Set<Article> articles = new HashSet<>();

    public QueryDataAccessObject() {
        // this.articleFactory = userFactory;
    }

    @Override
    public Set<Article> get(String sortType, String query) {

        // format query for insertion into API request
        final String urlQuery = query.replaceAll(" +", "+");

        // Make an API call to get the article object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("https://api.crossref.org/works?query=%s"
                                + "&select=DOI,title,published,author,reference"
                                + "&sort=%s&order=desc", urlQuery, sortType))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getString(STATUS_CODE_LABEL).equals(SUCCESS_CODE)) {
                final JSONArray resultsJSONArray = responseBody.getJSONObject(MESSAGE).getJSONArray(ITEMS);
                int i = 0;
                while (i < Integer.min(NUM_OUTPUTS, resultsJSONArray.length())) {
                    final JSONObject articleJSONObject = resultsJSONArray.getJSONObject(i);

                    if (articleJSONObject.has(DOI) && articleJSONObject.has(TITLE) && articleJSONObject.has(AUTHOR)
                            && articleJSONObject.has(DATE) && articleJSONObject.has(REFERENCE)) {

                        final String doi = articleJSONObject.getString(DOI);
                        final String title = articleJSONObject.getJSONArray(TITLE).getString(0);
                        final JSONArray authorsJSONArr = articleJSONObject.getJSONArray(AUTHOR);
                        final String[] authors = new String[authorsJSONArr.length()];
                        final StringJoiner date = new StringJoiner("/");
                        final Set<String> references = new HashSet<>();

                        // get authors
                        for (int j = 0; j < authorsJSONArr.length(); j++) {
                            final JSONObject author = authorsJSONArr.getJSONObject(j);
                            try {
                                authors[j] = author.getString(GIVEN) + " " + author.getString(FAMILY);
                            }
                            catch (JSONException exception) {
                                authors[j] = "[Author not found]";
                            }
                        }
                        // get date
                        final JSONArray dateJSONArr = articleJSONObject.getJSONObject(DATE).getJSONArray(DATE_PARTS)
                                .getJSONArray(0);
                        for (int k = 0; k < dateJSONArr.length(); k++) {
                            date.add(Integer.toString(dateJSONArr.getInt(k)));
                        }
                        // get references (as set of DOIs)
                        final JSONArray refsJSONArr = articleJSONObject.getJSONArray(REFERENCE);
                        for (int l = 0; l < refsJSONArr.length(); l++) {
                            final JSONObject refJSONObject = refsJSONArr.getJSONObject(l);
                            if (refJSONObject.has(DOI)) {
                                references.add(refsJSONArr.getJSONObject(l).getString(DOI));
                            }
                        }

                        // System.out.println(title + "\n" + authors[0] + "\n" + date.toString());
                        // NOTE: use articleFactory?
                        this.articles.add(new Article(doi, title, authors, date.toString(), references));
                    }
                    i++;
                }
                return this.articles;
            }
            // if there's an error code, return the error message:
            else {
                throw new RuntimeException(responseBody.getJSONArray(MESSAGE).getJSONObject(0)
                        .getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the set of articles matching the current query in ascending order by relevance.
     * @param query the username to look up
     * @return the set of articles.
     */
    public Set<Article> get(String query) {
        return get("relevance", query);
    }

    //////////////////////STUFF BELOW COPIED OVER FROM DBUSERACCESSOBJECT ////////////////////////

    //    @Override
    //    public void setCurrentUsername(String name) {
    //        // this isn't implemented for the lab
    //    }
    //
    //    @Override
    //    public boolean existsByName(String username) {
    //        final OkHttpClient client = new OkHttpClient().newBuilder()
    //                .build();
    //        final Request request = new Request.Builder()
    //                .url(String
    //                .format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username))
    //                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
    //                .build();
    //        try {
    //            final Response response = client.newCall(request).execute();
    //
    //            final JSONObject responseBody = new JSONObject(response.body().string());
    //
    //            return responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
    //        }
    //        catch (IOException | JSONException ex) {
    //            throw new RuntimeException(ex);
    //        }
    //    }
    //
    //    @Override
    //    public void save(User user) {
    //        final OkHttpClient client = new OkHttpClient().newBuilder()
    //                .build();
    //
    //        // POST METHOD
    //        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
    //        final JSONObject requestBody = new JSONObject();
    //        requestBody.put(USERNAME, user.getName());
    //        requestBody.put(PASSWORD, user.getPassword());
    //        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
    //        final Request request = new Request.Builder()
    //                .url("http://vm003.teach.cs.toronto.edu:20112/user")
    //                .method("POST", body)
    //                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
    //                .build();
    //        try {
    //            final Response response = client.newCall(request).execute();
    //
    //            final JSONObject responseBody = new JSONObject(response.body().string());
    //
    //            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
    //                // success!
    //            }
    //            else {
    //                throw new RuntimeException(responseBody.getString(MESSAGE));
    //            }
    //        }
    //        catch (IOException | JSONException ex) {
    //            throw new RuntimeException(ex);
    //        }
    //    }
    //
    //    @Override
    //    public void changePassword(User user) {
    //        final OkHttpClient client = new OkHttpClient().newBuilder()
    //                .build();
    //
    //        // POST METHOD
    //        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
    //        final JSONObject requestBody = new JSONObject();
    //        requestBody.put(USERNAME, user.getName());
    //        requestBody.put(PASSWORD, user.getPassword());
    //        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
    //        final Request request = new Request.Builder()
    //                .url("http://vm003.teach.cs.toronto.edu:20112/user")
    //                .method("PUT", body)
    //                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
    //                .build();
    //        try {
    //            final Response response = client.newCall(request).execute();
    //
    //            final JSONObject responseBody = new JSONObject(response.body().string());
    //
    //            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
    //                // success!
    //            }
    //            else {
    //                throw new RuntimeException(responseBody.getString(MESSAGE));
    //            }
    //        }
    //        catch (IOException | JSONException ex) {
    //            throw new RuntimeException(ex);
    //        }
    //    }
    //
    //    @Override
    //    public String getCurrentUsername() {
    //        return null;
    //    }
}
