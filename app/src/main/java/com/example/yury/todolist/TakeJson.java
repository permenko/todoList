package com.example.yury.todolist;

import android.os.AsyncTask;
import android.util.Log;

import com.example.yury.todolist.json.Example;

class TakeJson extends AsyncTask<Void, Void, Example> {
    protected Example doInBackground(Void... args) {
        try {
            Example json = MainActivity.rest.getForObject(MainActivity.url, Example.class);
            Log.d("LOG", "json done");
            return json;
        } catch (Exception e) {
            Log.e("LOG", e.getMessage(), e);

        }
        return null;
    }
}