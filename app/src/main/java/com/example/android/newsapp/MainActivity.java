package com.example.android.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.newsapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText mSearchBoxEditText;
    private TextView newsTextView;
    private ProgressBar progress;
    private NewsAdapter newsAdapter;
    private TextView errorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        //newsTextView = (TextView) findViewById(R.id.news_text_data);
        progress = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        newsTextView.setText(githubSearchUrl.toString());
        String githubSearchResults = null;
        new GetDataTask().execute(githubSearchUrl);
        // TODO (4) Create a new GithubQueryTask and call its execute method, passing in the url to query
    }

    private void loadNewsData() {
        new GetDataTask().execute();
    }

    public class GetDataTask extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(URL... urls){
            String Result = null;
            URL url = NetworkUtils.buildUrl(Result);
            try {
                Result = NetworkUtils.getResponseFromHttpUrl(url);
            }catch(IOException e){
                e.printStackTrace();
            }
            return Result;

        }
        @Override
        protected void onPostExecute(String s){
            if (s!= null && !s.equals("")){
                newsTextView.setText(s);
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
