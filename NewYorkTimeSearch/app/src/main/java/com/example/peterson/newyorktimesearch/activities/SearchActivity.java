package com.example.peterson.newyorktimesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.peterson.newyorktimesearch.Article;
import com.example.peterson.newyorktimesearch.ArticleArrayAdapter;
import com.example.peterson.newyorktimesearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText edtQuery;
    GridView gvResults;
    Button btnSearch;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void setupViews() {

        if (!isNetworkAvailable()){
            Toast.makeText(this,"Connection Network Failed", Toast.LENGTH_LONG).show();
        } else {
            edtQuery = (EditText) findViewById(R.id.edtQuery);
            gvResults = (GridView) findViewById(R.id.gvResult);
            btnSearch = (Button) findViewById(R.id.btnSearch);
            articles = new ArrayList<>();
            adapter = new ArticleArrayAdapter(this, articles);
            gvResults.setAdapter(adapter);

            // hook up listerner for grid click
            gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Create an intent to display
                    Intent i = new Intent(getApplicationContext(), ArticleActivity.class);

                    // get the article position to display
                    Article article = articles.get(position);

                    // pass in that article into intent
                    i.putExtra("article", article);

                    // launch the activity
                    startActivity(i);

                }
            });
        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if its present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onArticleSearch(View view) {
        String myEditText = edtQuery.getText().toString();
//        Toast.makeText(this, "Search for:" + myEditText, Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "823891cb183a4c6093883dbf7f9e6542");
        params.put("page", 0);
        params.put("q", myEditText);

        client.get(url, params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSOArray(articleJsonResults));
                    Log.d("DEBUG", articleJsonResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void onSortData(MenuItem item) {
        Intent intent = new Intent(SearchActivity.this, Settings.class);
        startActivity(intent);
    }
}
