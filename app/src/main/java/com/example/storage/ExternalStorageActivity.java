package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCreateFile, btnRead, btnWrite, btnCreateCacheFile;
    private ExternalStorageManager externalStorageManager =
            Storage.getInstance().getExternalStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        btnCreateFile = findViewById(R.id.btnCreateFile);
        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);
        btnCreateCacheFile = findViewById(R.id.btnCreateCacheFile);

        btnCreateFile.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnCreateCacheFile.setOnClickListener(this);
    }

    @Override public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCreateFile:
                boolean isFileCreated = externalStorageManager.create(this, "Four");
                if (isFileCreated) {
                    Toast.makeText(this, "File created.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "File NOT created.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnWrite:
                File file = externalStorageManager.getFile(this, "Four");
                externalStorageManager.write(file, "999999999");
                Toast.makeText(this, "File written.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnRead:
                File file2 = externalStorageManager.getFile(this, "Four");
                StringBuilder stringBuilder = externalStorageManager.read(file2);
                Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnCreateCacheFile:
                boolean isCacheFileCreated = externalStorageManager.createCacheFile(this,
                        "CacheFile");

                if (isCacheFileCreated) {
                    Toast.makeText(this, "Cache File created.", Toast.LENGTH_SHORT).show();

                    File file3 = externalStorageManager.getCacheFile(this,"CacheFile");
                    externalStorageManager.write(file3, "Cache Text");

                    StringBuilder stringBuilder1 = externalStorageManager.read(file3);
                    Toast.makeText(this, stringBuilder1, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}