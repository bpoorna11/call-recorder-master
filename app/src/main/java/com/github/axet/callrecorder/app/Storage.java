package com.github.axet.callrecorder.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Storage extends com.github.axet.audiolibrary.app.Storage {
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

    public Storage(Context context) {
        super(context);
    }

    public File getNewFile(String phone) {
        String name = "";

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        String ext = shared.getString(com.github.axet.audiolibrary.app.MainApplication.PREFERENCE_ENCODING, "");

        String format = shared.getString(MainApplication.PREFERENCE_FORMAT, "%s");

        if (format.equals("%T")) {
            name = "" + System.currentTimeMillis() / 1000;
        }

        if (format.equals("%T - %p")) {
            name = System.currentTimeMillis() / 1000 + " - " + phone;
        }

        if (format.equals("%s - %p")) {
            name = simple.format(new Date()) + " - " + phone;
        }

        if (format.equals("%s") || name.isEmpty()) {
            name = simple.format(new Date());
        }

        File parent = getStoragePath();
        if (!parent.exists()) {
            if (!parent.mkdirs())
                throw new RuntimeException("Unable to create: " + parent);
        }

        return getNextFile(parent, name, ext);
    }
}
