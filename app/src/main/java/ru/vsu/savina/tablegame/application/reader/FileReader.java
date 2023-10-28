package ru.vsu.savina.tablegame.application.reader;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.vsu.savina.tablegame.view.activity.GameActivity;

public class FileReader {
    public static String readFile(String fileName, GameActivity activity) {
        StringBuilder text = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(activity.getAssets().open(fileName)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
            }
        } catch (IOException e) {
            Log.e("FILEREADER", "exception", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("FILEREADER", "exception", e);
                }
            }
        }

        return text.toString();
    }
}
