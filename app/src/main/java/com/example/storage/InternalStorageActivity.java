package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class InternalStorageActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCreateCacheFile, btnCreateFile, btnRead,
            btnWrite, btnGotoExternalStorage;
    private InternalStorageManager internalStorageManager =
            Storage.getInstance().getInternalStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        btnCreateCacheFile = findViewById(R.id.btnCreateCacheFile);
        btnCreateFile = findViewById(R.id.btnCreateFile);
        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);
        btnGotoExternalStorage = findViewById(R.id.btnGotoExternalStorage);

        btnCreateCacheFile.setOnClickListener(this);
        btnCreateFile.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnGotoExternalStorage.setOnClickListener(this);
    }

    @Override public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCreateFile:
                boolean isFileCreated =
                        internalStorageManager.createFile(this, "one");
                if (isFileCreated) {
                    Toast.makeText(this, "File created.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnWrite:
                File file = internalStorageManager.getFile(this, "one");
                Storage.getInstance().getInternalStorage().write(file,
                        "qwertyuiopasdfghjklzxcvbnm.+111");
                Toast.makeText(this, "File written.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnRead:
                File file1 = Storage.getInstance().getInternalStorage().getFile(this, "one");
                StringBuilder data = Storage.getInstance().getInternalStorage().read(file1);
                Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnCreateCacheFile:
                boolean isFileCreated3 = internalStorageManager.createCacheFile(this,
                        "CacheFile");
                if (isFileCreated3) {
                    Toast.makeText(this, "File created.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                //write something
                File file3 = internalStorageManager.getCacheFile(this, "CacheFile");
                internalStorageManager.write(file3, "Cache Text.");

                //read it
                StringBuilder data3 = internalStorageManager.read(file3);
                Toast.makeText(this, data3.toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnGotoExternalStorage:
                startActivity(new Intent(InternalStorageActivity.this,
                        ExternalStorageActivity.class));
                break;

        }
    }
}