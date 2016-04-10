package com.example.ohad.dynamicex;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    //String xmlPath = Environment.getExternalStorageDirectory().getPath() + "/AAXml/lessonExample.xml";
    final File xmlFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/AAXml");

    Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO add create fragment here


        lesson = new Lesson(MainActivity.this);

        String xmlPath = xmlFolder.listFiles()[0].getPath(); // For now, just take the first file in the xml folder
        XmlParser parser = new XmlParser(xmlPath);
        parser.parse(lesson); // Loads the data from the XML into the lesson
        lesson.showFirstSlide();


//        for (final File fileEntry : xmlFolder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                listFilesForFolder(fileEntry);
//            xmlPath = xmlFolder.getPath() + "/" + fileEntry.getName();
//
//
//                System.out.println(fileEntry.getName());
//        }


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
