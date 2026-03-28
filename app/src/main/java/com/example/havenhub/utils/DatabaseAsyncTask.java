package com.example.havenhub.utils;

import android.os.AsyncTask;

public abstract class DatabaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private OnDatabaseOperationCompleteListener<Result> mListener;

    public interface OnDatabaseOperationCompleteListener<Result> {
        void onOperationComplete(Result result);
    }

    public void setOnDatabaseOperationCompleteListener(OnDatabaseOperationCompleteListener<Result> listener) {
        mListener = listener;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (mListener != null) {
            mListener.onOperationComplete(result);
        }
    }
}