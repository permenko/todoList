package com.example.yury.todolist;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.yury.todolist.R;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {

    protected ArrayList<String> list = new ArrayList<String>();
    private Context context;
    static ImageButton deleteBtn, editBtn, checkboxBtn;



    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_view, null);



        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        deleteBtn = (ImageButton)view.findViewById(R.id.delete_button);
        editBtn = (ImageButton)view.findViewById(R.id.edit_button);
        checkboxBtn = (ImageButton) view.findViewById(R.id.check_button);

        try {
            if (MainActivity.json.getEmbedded().getTasks().get(position).getTaskStatus()) {
                checkboxBtn.setImageResource(R.drawable.done_icon);
                //Log.d("Adapter", "" + MainActivity.json.getEmbedded().getTasks().get(i).getTaskName());
            } else {
                checkboxBtn.setImageResource(R.drawable.undone_icon);
            }
        } catch (Exception e) {
            Log.e("LOG", e.getMessage(), e);
        }

        //Handle buttons and add onClickListeners



        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something

                list.remove(position); //or some other task


                MainActivity mMainActivity = new MainActivity();
                mMainActivity.delete(position);

                notifyDataSetChanged();

            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                MainActivity.editText.setVisibility(View.VISIBLE);
                MainActivity.update.setVisibility(View.VISIBLE);
                MainActivity.editText.setText(MainActivity.arrayList.get(position));
                final int currentPosition = position;
                MainActivity.update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.arrayList.set(currentPosition, MainActivity.editText.getText().toString());
                        MainActivity.editText.setVisibility(View.INVISIBLE);
                        MainActivity.update.setVisibility(View.INVISIBLE);
                        MainActivity.json.getEmbedded().getTasks().get(currentPosition).setTaskName(MainActivity.editText.getText().toString());
                        MainActivity.editText.getText().clear();
                        MainActivity mMainActivity = new MainActivity();
                        //mMainActivity.hideKeyboard();
                        mMainActivity.changeTaskName(currentPosition);


                        notifyDataSetChanged();
                    }
                });


                //MainActivity.arrayList.set(position, "FUCK YEAH");



                //
            }
        });
        checkboxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.json.getEmbedded().getTasks().get(position).getTaskStatus()) {
                    checkboxBtn.setImageResource(R.drawable.undone_icon);

                } else {
                    checkboxBtn.setImageResource(R.drawable.done_icon);

                }
                notifyDataSetChanged();
                MainActivity mMainActivity = new MainActivity();
                mMainActivity.changeTaskStatus(position);
            }
        });

        return view;
    }


}