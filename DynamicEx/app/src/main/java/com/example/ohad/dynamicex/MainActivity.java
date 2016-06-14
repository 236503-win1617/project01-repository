package com.example.ohad.dynamicex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;


/**
 * Created by Ohad.
 */

public class MainActivity extends AppCompatActivity {

    Lesson lesson = new Lesson(MainActivity.this);
    XmlParser parser = new XmlParser(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = getIntent().getExtras();
        String lessonPath = b.getString("lessonPath");
        File lessonFolder = new File(lessonPath);

        File[] xmlFiles = lessonFolder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".xml");
            }
        });

        if (xmlFiles.length == 0) {
            Toast.makeText(getApplicationContext(), "Error! XML file doesn't exist in the lesson's directory!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else if (xmlFiles.length > 1) {
            Toast.makeText(getApplicationContext(), "Error! Multiple XML files exist in the lesson's directory!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        File xmlFile = xmlFiles[0];
        parser.parse(xmlFile.getAbsolutePath(), lessonPath, lesson); // Loads the data from the XML into the lesson
        lesson.showFirstSlide();

        FloatingActionButton fab_exit = (FloatingActionButton) findViewById(R.id.fab_exit);
        fab_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

    @Override
    public void onBackPressed() {
        lesson.clearSounds();
        finish();
    }
}
