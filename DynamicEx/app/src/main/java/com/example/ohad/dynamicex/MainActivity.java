package com.example.ohad.dynamicex;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    String xmlPath = Environment.getExternalStorageDirectory().getPath() + "/AAXml/lessonExample.xml";
    Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lesson = new Lesson(MainActivity.this);
        XmlParser parser = new XmlParser(xmlPath);
        parser.parse(lesson); // Loads the data from the XML into the lesson
        lesson.showFirstSlide();


        FloatingActionButton fab_next = (FloatingActionButton) findViewById(R.id.fab_next);
        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lesson.showNextSlide();
            }
        });

        FloatingActionButton fab_prev = (FloatingActionButton) findViewById(R.id.fab_prev);
        fab_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lesson.showPrevSlide();
            }
        });

    }

}
