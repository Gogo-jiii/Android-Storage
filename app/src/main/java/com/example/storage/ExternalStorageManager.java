package com.example.storage;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static android.os.storage.StorageManager.ACTION_MANAGE_STORAGE;

public class ExternalStorageManager {

    private static ExternalStorageManager instance = null;

    private ExternalStorageManager() {
    }

    public static ExternalStorageManager getInstance() {
        if (instance == null) {
            instance = new ExternalStorageManager();
        }
        return instance;
    }

    // Checks if a volume containing external storage is available for read and write.
    boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED;
    }

    // Checks if a volume containing external storage is available to at least read.
    boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
                Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY;
    }

    File getPrimaryExternalLocation(Context context) {
        File[] externalStorageVolumes =
                ContextCompat.getExternalFilesDirs(context, null);
        File primaryExternalStorage = externalStorageVolumes[0];
        return primaryExternalStorage;
    }

    boolean create(Context context, String fileName) {
        File appSpecificExternalDir = new File(context.getExternalFilesDir(fileName), fileName);
        return (appSpecificExternalDir == null) ? false : true;
    }

    File getFile(Context context, String fileName) {
        return new File(context.getExternalFilesDir(fileName), fileName);
    }

    void write(File file, String data) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(data);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    StringBuilder read(File file) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    boolean createCacheFile(Context context, String filename) {
        File externalCacheFile = new File(context.getExternalCacheDir(), filename);
        return (externalCacheFile == null) ? false : true;
    }

    File getCacheFile(Context context, String filename) {
        return new File(context.getExternalCacheDir(), filename);
    }

    boolean deleteCacheFile(Context context, String filename) {
        File externalCacheFile = new File(context.getExternalCacheDir(), filename);
        return externalCacheFile.delete();
    }

    File saveMediaFiles(Context context, String fileName, String directoryName) {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        //directoryName = Environment.DIRECTORY_PICTURES
        File file = new File(context.getExternalFilesDir(
                directoryName), fileName);
        if (file == null || !file.mkdirs()) {
            Toast.makeText(context, "File not created.", Toast.LENGTH_SHORT).show();
        }
        return file;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void queryFreeSpace(long spaceNeededForMyApp, Context context) {
        StorageManager storageManager =
                context.getSystemService(StorageManager.class);

        UUID appSpecificInternalDirUuid = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                appSpecificInternalDirUuid = storageManager.getUuidForPath(context.getFilesDir());

                long availableBytes = 0;

                availableBytes = storageManager.getAllocatableBytes(appSpecificInternalDirUuid);
                if (availableBytes >= spaceNeededForMyApp) {
                    storageManager.allocateBytes(
                            appSpecificInternalDirUuid, spaceNeededForMyApp);
                } else {
                    Intent storageIntent = new Intent();
                    storageIntent.setAction(ACTION_MANAGE_STORAGE);
                    // Display prompt to user, requesting that they choose files to remove.
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
