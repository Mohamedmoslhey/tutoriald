package com.example.hp_pc.googlebooklist;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class GoogleBookActivity extends AppCompatActivity {
    public static final String LOG_TAG = GoogleBookActivity.class.getName();

    private static final String googleapi_request_Url = "https://www.googleapis.com/books/v1/volumes?format=geojson&q=free%20book&maxResults=40";
    private BookDetailaAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlebook);
        //create fake list of book title /id/authorname/publisher name /pulisher date in custom adapter



        //create new arrayadapter of googlebook

        //find refreance of listview in layout
        ListView googleBookListView = (ListView)findViewById(R.id.list);
//set the adapter in list so the data can be arise in user interface
        bookAdapter = new BookDetailaAdapter(this,new ArrayList<BookDetails>());
        googleBookListView.setAdapter(bookAdapter);

        //handle click for explicit intent
        googleBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookDetails currentBookDeay = bookAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri googleBookUri = Uri.parse(currentBookDeay.getUrl());

                // Create a new intent to view the googlebook URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, googleBookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        googleBookAsynTask task = new googleBookAsynTask();
        task.execute(googleapi_request_Url);

    }
    private class googleBookAsynTask extends AsyncTask<String,Void,List<BookDetails>> {

        @Override
        protected List<BookDetails> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<BookDetails> bookDeatils = Qutils.fetchGoogleBookData(urls[0]);
            return bookDeatils;
        }

        @Override
        protected void onPostExecute(List<BookDetails> data) {
            // Clear the adapter of previousBookDetails data
            bookAdapter.clear();

            // If there is a valid list of {@link Bookdetails, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                bookAdapter.addAll(data);
            }

        }
    }
    }
