package com.example.hp_pc.googlebooklist;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-PC on 9/17/2017.
 */

public class Qutils {

    public static final String LOG_TAG = GoogleBookActivity.class.getName();


    private Qutils() {

    }


    /**
     * Query the USGS dataset and return a list of {@link BookDetails} objects.
     */
    public static List<BookDetails> fetchGoogleBookData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link BookDetails}s
        List<BookDetails> bookDetailses = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link BookDetails}s
        return bookDetailses;
    }

    /**
     * Return a list of {@link BookDetails} objects that has been built up from
     * parsing a JSON response.
     */
    /**
     * Return a list of {@link BookDetails} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<BookDetails> extractFeatureFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding BookDetails to
        List<BookDetails> book = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of features (or bookist).
            JSONArray bookArrayArray = baseJsonResponse.getJSONArray("items");

            // For each BookDetails in theBookDetailsArray, create an {@link BookDetails} object
            for (int i = 0; i < bookArrayArray.length(); i++) {

                // Get a single BookDetails at position i within the list of BookDetails
                JSONObject currentBookDetails = bookArrayArray.getJSONObject(i);

                // For a given BookDetails, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that BookDetails.
                JSONObject properties = currentBookDetails.getJSONObject("volumeInfo");

                // Extract the value for the key called "authors"
                String authors = properties.getString("authors");

                // Extract the value for the key called "publisher"
                String publisher = properties.getString("publisher");

                // Extract the value for the key called "publishedDate"
                String publishedDate = properties.getString("publishedDate");

                // Extract the value for the key called "previewLink"
                String url = properties.getString("previewLink");

                // Create a new {@link BookDetails} object with the magnitude, location, time,
                // and url from the JSON response.
                BookDetails bookDetails = new BookDetails(authors, publisher, publishedDate, url);

                // Add the new {@link BookDetails} to the list of BookDetails.
                book.add(bookDetails);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return book;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}
