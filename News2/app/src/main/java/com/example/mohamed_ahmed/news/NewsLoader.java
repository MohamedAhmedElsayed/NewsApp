package com.example.mohamed_ahmed.news;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class NewsLoader extends AsyncTaskLoader<String> {
    private static final String LOG_TAG = MainActivity.class.getName();
    Context context;
    private String HttpUrl ;

    public NewsLoader(Context context, String url) {
        super(context);
        this.context = context;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("content.guardianapis.com").appendPath("search")
                .appendQueryParameter("q", url).appendQueryParameter("api-key", "test");
        HttpUrl = builder.build().toString();
        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
    }

    @Override
    public String loadInBackground() {
        try {
            QueryUtils queryUtils = new QueryUtils(context);
            return queryUtils.fetchData(HttpUrl);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error In loadInBackground ");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
