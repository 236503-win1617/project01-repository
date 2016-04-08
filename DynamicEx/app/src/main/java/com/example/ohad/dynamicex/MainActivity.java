package com.example.ohad.dynamicex;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;

public class MainActivity extends AppCompatActivity {

    Lesson lesson = new Lesson(MainActivity.this);
    XmlParser parser = new XmlParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        String lessonPath = b.getString("lessonPath");
        File lessonFolder = new File(lessonPath);

        File[] xmlFiles = lessonFolder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".xml");
            }
        });

        File xmlFile = xmlFiles[0]; // Take the first XML file in the lesson's folder
        parser.parse(xmlFile.getAbsolutePath(), lessonPath, lesson); // Loads the data from the XML into the lesson
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
