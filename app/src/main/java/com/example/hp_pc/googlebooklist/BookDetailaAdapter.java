package com.example.hp_pc.googlebooklist;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by HP-PC on 8/24/2017.
 */

public class BookDetailaAdapter extends ArrayAdapter<BookDetails> {
    private static final String LOG_TAG = BookDetailaAdapter.class.getSimpleName();


    public BookDetailaAdapter( Context context, ArrayList<BookDetails> googleBook) {
        super(context,0,  googleBook);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }
        BookDetails currentBookDetails = getItem(position);
// find book title in lstview by id

//find book id  in listview by id

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.Author);
        //set this text in author view
        authorTextView.setText(currentBookDetails.getAuthor());
// find publisher name in listview item by id
        TextView publisherTextView = (TextView) listItemView.findViewById(R.id.publisher);
        //set this text inpublisher view
       publisherTextView.setText(currentBookDetails.getPublisher());
//find publisher date in lsit item by id
        TextView publishDateTextView = (TextView) listItemView.findViewById(R.id.publishDate);
        //set this text in publishdate view
        publishDateTextView.setText(currentBookDetails.getPublishDate());



//return all the above 4 textView
        return listItemView;
    }

}
