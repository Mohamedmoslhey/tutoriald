package com.example.hp_pc.googlebooklist;

/**
 * Created by HP-PC on 8/24/2017.
 */

public class BookDetails {



    private String mAuthor;
    private String mPublisher;
    private String mPublishDate;
    private String mUrl ;


    public BookDetails(  String Author, String Publisher, String PublishDate ,String url ) {


       mAuthor = Author ;
       mPublisher =  Publisher ;
        mPublishDate = PublishDate  ;
        mUrl = url ;

    }




    public String getAuthor() {
        return mAuthor;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public String getUrl(){return mUrl;}


}
