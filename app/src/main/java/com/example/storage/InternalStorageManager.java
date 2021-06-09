package com.example.storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InternalStorageManager {

    private static InternalStorageManager instance = null;

    private InternalStorageManager() {
    }

    public static InternalStorageManager getInstance() {
        if (instance == null) {
            instance = new InternalStorageManager();
        }
        return instance;
    }

    boolean createFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return (file == null) ? false : true;
    }

    File getFile(Context context, String fileName) {
        return new File(context.getFilesDir(), fileName);
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

    String[] getFileNames(Context context) {
        return context.fileList();
    }

    boolean createFileInDirectory(Context context, String fileName) {
        File directory = context.getFilesDir();
        File file = new File(directory, fileName);
        return (file == null) ? false : true;
    }

    File getFileInDirectory(Context context, String fileName) {
        File directory = context.getFilesDir();
        File file = new File(directory, fileName);
        return file;
    }

    boolean createCacheFile(Context context, String filename) {
        File file = null;
        try {
            file = File.createTempFile(filename, null, context.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (file == null) ? false : true;
    }

    File getCacheFile(Context context, String filename) {
        return new File(context.getCacheDir(), filename);
    }

    boolean deleteCacheFile(Context context, String filename) {
        return context.deleteFile(filename);
    }
}
