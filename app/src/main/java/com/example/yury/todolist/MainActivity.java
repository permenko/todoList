package com.example.yury.todolist;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.yury.todolist.json.Example;
import com.example.yury.todolist.json.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    protected static RestTemplate rest;
    protected static String url = "http://192.168.0.10:8080/tasks";
    static EditText addText, editText;
    static Button add, update;
    //Task myTasks;
    ListView listView;
    static ArrayList<String> arrayList;
    //ArrayAdapter<String> adapter;

    MyCustomAdapter adapter;
    protected static Example json;
    TakeJson takeJson = new TakeJson();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);
        addText = (EditText) findViewById(R.id.addText);
        editText = (EditText) findViewById(R.id.editText);

        rest = new RestTemplate();

        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            takeJson.execute();
            json = takeJson.get();
        } catch (Exception e) {
            Log.e("LOG", e.getMessage(), e);
        }
        //String urlStr = json.getEmbedded().getTasks().get(0).getTaskName();
        //Log.d("URL", "URL DELETE: " + urlStr);

        //add();
        //delete();







        //myTasks = new Task();

        arrayList = new ArrayList<String>();

        MyCustomAdapter adapter = new MyCustomAdapter(arrayList, this);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);


        //adapter = new ListAdapter(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayList);
        //listView.setAdapter(adapter);
        showTasks();
        add();

        //delete();

        //new DELETE().execute(myTasks);



        //new POST().execute(myTasks);
    }

    private void add() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(addText.getText().toString().equals(""))) {

                    new AddTask().execute(addText.getText().toString()); //post task to mongodb

                } else {
                    Log.d("LOG", "editText is empty");
                }

            }
        });
    }

    private class AddTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... tasks) {
            try  {
                Task task = new Task();
                task.setTaskName(tasks[0]);
                task.setTaskStatus(false);
                Log.d("LOG", tasks[0]);
                rest.postForObject(new URI(url), task, Task.class);

            }
            catch (Exception e) {
                Log.e("LOG", e.getMessage(), e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void args) {
            super.onPostExecute(args);
            try { //update json
                TakeJson takeJson = new TakeJson();
                takeJson.execute();
                json = takeJson.get();
                arrayList.add(addText.getText().toString());
                addText.getText().clear();
                hideKeyboard();
            } catch (Exception e) {
                Log.e("LOG", e.getMessage(), e);
            }
        }
    }

    void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void delete(final Integer position) {
        new DELETE().execute(position);
    }
    private class DELETE extends AsyncTask<Integer, Void, Void> {
        protected Void doInBackground(Integer... args) {
            try {

                rest.delete(new URI(json.getEmbedded().getTasks().get(args[0]).getLinks().getSelf().getHref()));

            } catch (Exception e) {
                Log.e("LOG", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            super.onPostExecute(args);
            try { //update json
                TakeJson takeJson = new TakeJson();
                takeJson.execute();
                json = takeJson.get();
            } catch (Exception e) {
                Log.e("LOG", e.getMessage(), e);
            }

        }
    }

    private class ChangeTaskStatus extends AsyncTask<Integer, Void, Void> {

        protected Void doInBackground(Integer... position) {
            try  {
                if (!json.getEmbedded().getTasks().get(position[0]).getTaskStatus()) {

                    json.setAdditionalProperty("taskStatus", true);
                    json.setAdditionalProperty("taskName", json.getEmbedded().getTasks().get(position[0]).getTaskName());
                    json.getEmbedded().getTasks().get(position[0]).setTaskStatus(true);

                } else {

                    json.setAdditionalProperty("taskStatus", false);
                    json.setAdditionalProperty("taskName", json.getEmbedded().getTasks().get(position[0]).getTaskName());
                    json.getEmbedded().getTasks().get(position[0]).setTaskStatus(false);

                }
                //Log.d("LOG", "" + json.getEmbedded().getAdditionalProperties());
                rest.put(json.getEmbedded().getTasks().get(position[0]).getLinks().getSelf().getHref(), json.getAdditionalProperties());

            }
            catch (Exception e) {
                Log.e("LOG", e.getMessage(), e);
            }

            return null;
        }
    }

    protected void changeTaskStatus(Integer position) {
        new ChangeTaskStatus().execute(position);

    }



    private void showTasks() {
        for (int i = 0; i < json.getPage().getTotalElements(); ++i) {
            try {

                arrayList.add(json.getEmbedded().getTasks().get(i).getTaskName());


            } catch (Exception e) {
                Log.e("SHOWlog", e.getMessage(), e);
            }

        }
        //


    }

    private class ChangeTaskName extends AsyncTask<Integer, Void, Void> {

        protected Void doInBackground(Integer... position) {
            try  {
                if (!json.getEmbedded().getTasks().get(position[0]).getTaskStatus()) {

                    json.setAdditionalProperty("taskStatus", false);
                    json.setAdditionalProperty("taskName", json.getEmbedded().getTasks().get(position[0]).getTaskName());
                    json.getEmbedded().getTasks().get(position[0]).setTaskName(json.getEmbedded().getTasks().get(position[0]).getTaskName());

                } else {

                    json.setAdditionalProperty("taskStatus", true);
                    json.setAdditionalProperty("taskName", json.getEmbedded().getTasks().get(position[0]).getTaskName());
                    json.getEmbedded().getTasks().get(position[0]).setTaskName(json.getEmbedded().getTasks().get(position[0]).getTaskName());

                }
                //Log.d("LOG", "" + json.getEmbedded().getAdditionalProperties());
                rest.put(json.getEmbedded().getTasks().get(position[0]).getLinks().getSelf().getHref(), json.getAdditionalProperties());

            }
            catch (Exception e) {
                Log.e("LOG", e.getMessage(), e);
            }

            return null;
        }
    }

    protected void changeTaskName(Integer position) {
        new ChangeTaskName().execute(position);

    }

}
