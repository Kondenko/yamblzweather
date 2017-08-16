package com.kondenko.yamblzweather.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.ui.BaseActivity;
import com.kondenko.yamblzweather.ui.about.AboutActivity;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.settings_select_city)
    TextView selectCityTextView;
    @BindView(R.id.settings_about)
    TextView aboutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, true);

        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null){
            supportActionBar.setTitle(getString(R.string.title_settings));
        }

        ButterKnife.bind(this);
        selectCityTextView.setOnClickListener(v -> startActivity(new Intent(this, SuggestsActivity.class)));
        aboutTextView.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));

        getFragmentManager().beginTransaction()
                            .replace(R.id.settings_container, new SettingsFragment())
                            .commit();
    }
}
