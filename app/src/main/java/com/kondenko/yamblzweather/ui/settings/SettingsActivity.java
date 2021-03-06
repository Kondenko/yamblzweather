package com.kondenko.yamblzweather.ui.settings;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.ui.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, true);
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }
}
