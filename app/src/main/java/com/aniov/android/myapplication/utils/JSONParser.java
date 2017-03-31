package com.aniov.android.myapplication.utils;

import android.util.Log;

import com.aniov.android.myapplication.R;
import com.aniov.android.myapplication.activity.MainActivity;
import com.aniov.android.myapplication.model.RecyclerViewItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marius on 3/31/2017.
 */

public class JSONParser {

    private MainActivity mainActivity;
    private String jsonObject;

    public JSONParser(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<RecyclerViewItem> getJsonObjects() {
        List<RecyclerViewItem> quoteList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(getJsonObject());
            JSONArray jsonArray = jsonObject.getJSONArray("quotes");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject quotesObject = jsonArray.getJSONObject(i);
                quoteList.add(new RecyclerViewItem(quotesObject.getString("quote"), quotesObject.getString("name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return quoteList;
    }

    public String getJsonObject() {
        String json;

        try {
            InputStream inputStream = mainActivity.getResources().openRawResource(R.raw.quotes);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            StringBuilder stringBuilder = new StringBuilder();
            while (inputStream.read(buffer) > 0) {
                stringBuilder.append(new String(buffer, "UTF-8"));
            }
            inputStream.close();
            json = stringBuilder.toString();
        } catch (IOException exception) {
            Log.e(JSONParser.class.getSimpleName(), exception.getMessage(), exception);
            return null;
        }

        return json;
    }
}
