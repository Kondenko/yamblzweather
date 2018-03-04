package com.themishkun.contentproviderreader;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Cursor cursor =
                getContentResolver().query(Uri.parse("content://com.kondenko.yamblzweather.infrastructure"), null, null, null, null);
            assert cursor != null;
            cursor.moveToNext();
            String weather = cursor.getString(2);
            float temperature = cursor.getFloat(1);
            String city = cursor.getString(0);
            ((TextView) findViewById(R.id.weatherText)).setText(String.format("There is %s in %s! Temperature is %.2f", weather, city, temperature - 273));
            cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
