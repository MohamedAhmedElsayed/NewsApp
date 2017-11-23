package com.example.mohamed_ahmed.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String LOG_TAG = MainActivity.class.getName();
    @BindView(R.id.ListNews)
    ListView NewsList;
    @BindView(R.id.TextEmpty)
    TextView txtempty;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    List<ListItem> News;
    private String Query;

    private List<ListItem> extractFeatureFromJson(String ResultJSON) throws JSONException {
        String Type, SectionName, WebPublicationDate, WebUrl, WebTitle;
        if (TextUtils.isEmpty(ResultJSON)) {
            return null;
        }
        News = new ArrayList<>();
        JSONObject object = new JSONObject(ResultJSON);
        object = object.optJSONObject("response");
        JSONArray NewsItems = null;
        Log.e(LOG_TAG, "Resultd" + object.has("results"));
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
//        Log.e(LOG_TAG, "News Size" + News.size());
        return News;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnItemClick(R.id.ListNews)
    public void onItemClick(int position) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(News.get(position).getWebUrl()));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.SearchAble);
        SearchView sv = new SearchView(this.getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtempty.setVisibility(View.INVISIBLE);
                Query = query;
                Loader<String> loader = null;
                LoaderManager loaderManager = getSupportLoaderManager();
                loader = loaderManager.getLoader(1);
                if (loader != null) {
                    loader = getSupportLoaderManager().restartLoader(1, null, MainActivity.this);
                    loader.forceLoad();
                } else {
                    loader = getSupportLoaderManager().initLoader(1, null, MainActivity.this);
                    loader.forceLoad();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //System.out.println("tap");
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new NewsLoader(this, Query);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        progressBar.setVisibility(View.INVISIBLE);
        try {
            ListAdapter listAdapter = null;
            List<ListItem> list = extractFeatureFromJson(data);
           // Log.e(LOG_TAG, "My Data size" + list.size());
            if (list.size() == 0) {
                txtempty.setVisibility(View.VISIBLE);
                listAdapter = new ListAdapter(new ArrayList<ListItem>(),
                        this, R.layout.raw_view);
            } else {
                listAdapter = new ListAdapter(list, this, R.layout.raw_view);
            }
            NewsList.setAdapter(listAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //   loader.reset();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }
}
