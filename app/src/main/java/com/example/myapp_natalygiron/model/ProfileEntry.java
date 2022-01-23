package com.example.myapp_natalygiron.model;

import android.content.res.Resources;

import com.example.myapp_natalygiron.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

public class ProfileEntry {

    private static final String TAG = ProfileEntry.class.getSimpleName();
    public String name;
    public String phone;

    public ProfileEntry(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public static ProfileEntry initProfileEntry(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.users);
        Writer writer = new StringWriter();

        String jsonProfileString = writer.toString();
        Gson gson = new Gson();
        ProfileEntry userProfile = gson.fromJson(jsonProfileString, ProfileEntry.class);
        return userProfile;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}