package com.example.android.newsapp.utilities;

/**
 * Created by cy on 6/23/17.
 */

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    final static String BASE_URL = "https://newsapi.org/v1/articles";

    final static String SOURCE = "the-next-web";
    final static String SORT = "latest";

    final static String SOURCE_PARAM = "source";
    final static String SORT_PARAM = "sortBy";
    final static String KEY_PARAM = "apiKey";


    public static URL buildUrl(String key) {
        // COMPLETED (1) Fill in this method to build the proper Github query URL
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, SOURCE)
                .appendQueryParameter(SORT_PARAM, SORT)
                .appendQueryParameter(KEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
