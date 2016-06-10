package com.example.ohad.dynamicex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ohad.
 */

public class MenuActivity extends AppCompatActivity {

    // main folder contains all the lessons (as folders)
    private String mainFolderPath = Environment.getExternalStorageDirectory().getPath() + "/AALessons";
    private ListView myList;
    private String selectedLessonName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        File mainFolder = new File(mainFolderPath);
        if (!mainFolder.exists()) {
            Toast.makeText(MenuActivity.this, "Error! Main lessons folder doesn't exist.", Toast.LENGTH_SHORT).show();
            return;
        }

        File[] lessonsFolders = mainFolder.listFiles();
        if (lessonsFolders == null)
            lessonsFolders = new File[0];
        List<String> data = new ArrayList<String>();

        for (int i = 0; i < lessonsFolders.length; ++i) {
            if (lessonsFolders[i].isDirectory())
                data.add(lessonsFolders[i].getName());
        }

        myList = (ListView)findViewById(R.id.list);
        myList.setAdapter(new dataListAdapter(data));
        myList.setEmptyView(findViewById(R.id.emptyView));

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedLessonName = (String)myList.getItemAtPosition(position);
                view.setSelected(true);
            }
        });

        final Button button = (Button) findViewById(R.id.startBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedLessonName == null){
                    Toast.makeText(MenuActivity.this, "Please select a lesson.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String lessonPath = mainFolderPath + "/" + selectedLessonName;
                if (new File(lessonPath).exists() == false) {
                    Toast.makeText(MenuActivity.this, "Error! The selected lesson's directory doesn't exist anymore.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("lessonPath", lessonPath);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }


    public class dataListAdapter extends BaseAdapter {

        List<String> data;

        public dataListAdapter(List<String> data) {
           this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            public TextView lessonName;
        }

        @SuppressLint("InflateParams") @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;

            if (convertView == null) {
                // Create View (just until the screen is full at the first time)
                LayoutInflater inflater = LayoutInflater.from(MenuActivity.this);
                view = inflater.inflate(R.layout.list_item, null);

                // Create ViewHolder
                holder = new ViewHolder();
                holder.lessonName = (TextView) view.findViewById(R.id.text);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            holder.lessonName.setText(data.get(position));
            return view;
        }

    }

}
