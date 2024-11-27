package data_access;

import entity.Article;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import use_case.query.QueryDataAccessInterface;
import use_case.results.ResultsDataAccessInterface;

public class TestDataAccessObject implements QueryDataAccessInterface, ResultsDataAccessInterface {
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

    private static final int NUM_OUTPUTS = 10;
    // private final UserFactory articleFactory;
    // TODO: -> Article Factory

    /**
     * Returns the set of articles matching the current query in ascending order by relevance.
     * @param query the username to look up
     * @return the set of articles.
     */
    @Override
    public Set<Article> get(String sortType, String query) {
        Set<Article> articles = new HashSet<>();

        try {
            final String jsonString = Files.readString(Paths.get(getClass().getClassLoader()
                    .getResource("sample.json").toURI()));
            final JSONObject responseBody = new JSONObject(jsonString);

            if (responseBody.getString(STATUS_CODE_LABEL).equals(SUCCESS_CODE)) {
                final JSONArray resultsJSONArray = responseBody.getJSONObject(MESSAGE).getJSONArray(ITEMS);
                int i = 0;
                while (articles.size() < Integer.min(NUM_OUTPUTS, resultsJSONArray.length())) {
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
                            authors[j] = author.getString(GIVEN) + " " + author.getString(FAMILY);
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
                        articles.add(new Article(doi, title, authors, date.toString(), references));
                    }
                    i++;
                }
                return articles;
            }
            // if there's an error code, return the error message:
            else {
                throw new RuntimeException(responseBody.getJSONArray(MESSAGE).getJSONObject(0)
                        .getString(MESSAGE));
            }
        }
        catch (IOException | JSONException | URISyntaxException ex) {
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
}
