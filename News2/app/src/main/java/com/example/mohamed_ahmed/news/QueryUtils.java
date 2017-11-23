package com.example.mohamed_ahmed.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static Context context;

    public QueryUtils(Context context) {
        this.context = context;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.e(LOG_TAG, "MY URL" + url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        Log.e(LOG_TAG, "iam In Url ");
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }
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
                inputStream.close();
            }
        }
        Log.e(LOG_TAG, jsonResponse);
        return jsonResponse;
    }

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
        Log.e(output.toString(), LOG_TAG);
        return output.toString();
    }

    /*
        private static List<ListItem> extractFeatureFromJson(String ResultJSON) throws JSONException {
            String Type, SectionName, WebPublicationDate, WebUrl, WebTitle;
            if (TextUtils.isEmpty(ResultJSON)) {
                return null;
            }
            List<ListItem> News = new ArrayList<>();
            JSONObject object = new JSONObject(ResultJSON);
            JSONArray NewsItems = null;
            if (object.has("results")) {
                NewsItems = object.optJSONArray("results");
                for (int i = 0; i < NewsItems.length(); i++) {
                    JSONObject CurrentObject = NewsItems.optJSONObject(i);
                    Type = CurrentObject.optString("type");
                    SectionName = CurrentObject.optString("sectionName");
                    WebPublicationDate = CurrentObject.optString("webPublicationDate");
                    WebUrl = CurrentObject.optString("webUrl");
                    WebTitle = CurrentObject.optString("webTitle");
                    News.add(new ListItem(Type, SectionName, WebPublicationDate, WebUrl, WebTitle));
                }
            }
            return News;
        }
    */
    public static String fetchData(String requestUrl) throws JSONException, IOException {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            return null;
        }
        return makeHttpRequest(url);
    }
}
