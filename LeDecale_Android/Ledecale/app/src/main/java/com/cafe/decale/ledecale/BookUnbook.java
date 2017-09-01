package com.cafe.decale.ledecale;

import android.os.AsyncTask;

/**
 * Created by manut on 28/08/2017.
 */

public abstract class BookUnbook extends AsyncTask<String, Void, Boolean> {
    public BookUnbook(Listener listener){
        mListener = listener;
    }
    protected Listener mListener;

    public interface Listener{
        void onLoaded(boolean hasJoined, String status);
        void onError(String status);
    }
}
