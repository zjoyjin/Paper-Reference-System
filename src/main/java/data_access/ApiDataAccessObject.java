package data_access;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.User;
import entity.UserFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.request_data.ApiDataAccessInterface;

/**
 * The DAO for user data.
 */
public class ApiDataAccessObject implements ApiDataAccessInterface {
//        LoginUserDataAccessInterface,
//        ChangePasswordUserDataAccessInterface,
//        LogoutUserDataAccessInterface {
    private static final String SUCCESS_CODE = "ok";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status";
    private static final String MESSAGE = "message";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String GIVEN = "given";
    private static final String FAMILY = "family";
    private static final String DATE = "published";
    private static final String DATE_PARTS = "date-parts";
    private final UserFactory articleFactory;
    // TODO: -> Article Factory

    public ApiDataAccessObject(UserFactory userFactory) {
        this.articleFactory = userFactory;
        // No need to do anything to reinitialize a user list! The data is the cloud that may be miles away.
    }

//    @Overrides
    public User get(String doi) {
        // TODO: make Article (node) class

        // Make an API call to get the article object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url("https://api.crossref.org/works/" + doi)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getString(STATUS_CODE_LABEL).equals(SUCCESS_CODE)) {
                final JSONObject resultJSONObject = responseBody.getJSONObject(MESSAGE);
                final String title = resultJSONObject.getJSONArray(TITLE).getString(0);
                // get authors
                final JSONArray authorsJSONArr = resultJSONObject.getJSONArray(AUTHOR);
                final String[] authors = new String[authorsJSONArr.length()];
                for (int i = 0; i < authorsJSONArr.length(); i++) {
                    final JSONObject author = authorsJSONArr.getJSONObject(i);
                    authors[i] = author.getString(GIVEN) + " " + author.getString(FAMILY);
                }
                // get date
                final JSONArray dateJSONArr = resultJSONObject.getJSONObject(DATE).getJSONArray(DATE_PARTS)
                        .getJSONArray(0);
                final StringJoiner date = new StringJoiner("/");
                for (int i = 0; i < dateJSONArr.length(); i++) {
                    date.add(Integer.toString(dateJSONArr.getInt(i)));
                }
                // Just for squiggly removal:
                System.out.println(title);
                System.out.println(authors[0]);
                System.out.println(date.toString());
                // TODO: turn to articleFactory (remove password later)
                // params: title, authors, publication date, doi
                // return articleFactory.create(doi, title);
                return null;
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
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
//                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username))
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
