package com.example.storage;

public class Storage {

    private static Storage instance = null;

    private Storage() {
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    InternalStorageManager getInternalStorage() {
        return InternalStorageManager.getInstance();
    }

    ExternalStorageManager getExternalStorage() {
        return ExternalStorageManager.getInstance();
    }
}
