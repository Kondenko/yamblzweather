package com.kondenko.yamblzweather.ui.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnboardingActivity extends AppCompatActivity {

    @BindView(R.id.choose_city_button)
    Button chooseCityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        chooseCityButton.setOnClickListener(v -> finishOnBoarding());
    }

    private void finishOnBoarding() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        defaultSharedPreferences.edit().putBoolean(getString(R.string.key_onboarding_completion), true).apply();
        startActivity(new Intent(this, SuggestsActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
