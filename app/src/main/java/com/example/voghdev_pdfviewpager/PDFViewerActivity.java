package com.example.voghdev_pdfviewpager;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PdfScale;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;


public class PDFViewerActivity extends AppCompatActivity implements DownloadFile.Listener {
    RemotePDFViewPager remotePDFViewPager;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        progressBar = findViewById(R.id.progressBar);

        String url = getIntent().getStringExtra("url");

        remotePDFViewPager =
                new RemotePDFViewPager(PDFViewerActivity.this, url, this);



        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(getFilesDir().getName(), Context.MODE_PRIVATE);
        File file =  new File(directory,"File");
        String data = getFileUrl(url);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file,true);
            fos.write(data.getBytes());
            fos.close();
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        


    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        progressBar.setVisibility(View.GONE);
        PDFPagerAdapter adapter = new PDFPagerAdapter(this, getFileUrl(url));
        remotePDFViewPager.setAdapter(adapter);
        setContentView(remotePDFViewPager);


    }

    public static String getFileUrl(String url){
        return url.substring(url.lastIndexOf('/') + 1);
    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {


        progressBar.setVisibility(View.VISIBLE);

   }


}