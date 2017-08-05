package com.kondenko.yamblzweather.ui.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;
import com.kondenko.yamblzweather.ui.BaseMvpActivity;
import com.kondenko.yamblzweather.ui.about.AboutActivity;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;
import com.kondenko.yamblzweather.ui.settings.SettingsActivity;
import com.kondenko.yamblzweather.utils.Logger;
import com.kondenko.yamblzweather.utils.WeatherUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class WeatherActivity
        extends BaseMvpActivity<Weather, WeatherPresenter>
        implements WeatherView {

    private static final java.lang.String TAG = "WeatherActivity";

    @Inject
    public void Inject(WeatherPresenter presenter){
        this.presenter = presenter;
    }

    @BindView(R.id.weather_button_city)
    public Button buttonCity;

    @BindView(R.id.weather_text_temperature)
    public TextView textTemperature;

    @BindView(R.id.weather_icon_condition)
    public WeatherIconView weatherIcon;

    @BindView(R.id.weather_text_condition)
    public TextView textCondition;

    @BindView(R.id.weather_refresh_layout)
    public SwipeRefreshLayout refreshLayout;

    @BindView(R.id.weather_text_latest_update)
    public TextView textLatestUpdate;

    @BindView(R.id.text_rain_level_header)
    public TextView textRainLevelHeader;

    @BindView(R.id.text_rain_level_value)
    public TextView textRainLevel;

    @BindView(R.id.text_wind_value)
    public TextView textWindSpeed;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateData();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setToolbar(toolbar, false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
        refreshLayout.setOnRefreshListener(presenter::updateData);
        buttonCity.setOnClickListener((v) -> startActivity(new Intent(this, SuggestsActivity.class)));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            }
            case R.id.action_about: {
                startActivity(new Intent(this, AboutActivity.class));
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong menu item used");
            }
        }
        return true;
    }

    @Override
    public void setData(Weather weather) {
        super.setData(weather);
        showCity(weather.city().name());
        showTemperature(weather.temperature().celsiusDegrees(), "C°");
        showCondition(weather.weatherConditions());
        showWindSpeed(weather.windSpeed());
    }

    @Override
    public void showLoading(boolean loading) {
        refreshLayout.setRefreshing(loading);
    }

    @Override
    public void showError(Throwable error) {
        Logger.w(TAG, error);
        Toast.makeText(this, this.getString(R.string.error_loading_weather), Toast.LENGTH_LONG).show();
    }

    // Precise data formatting

    private void showTemperature(double temp, String units) {
        String temperature = String.valueOf(Math.round(temp));
        Spannable temperatureSpannable = WeatherUtils.getTemperatureString(this, temperature, units);
        textTemperature.setText(temperatureSpannable);
    }

    private void showCondition(WeatherConditions condition) {
        weatherIcon.setIconResource(getString(WeatherUtils.getIconStringResource(condition)));
    }

    private void showRainLevel(double value) {
        textRainLevel.setText(value > 0 ? getString(R.string.weather_rain_level_value, value) : getString(R.string.weather_unknown_rain_level_value));
    }

    private void showWindSpeed(double value) {
        String windSpeed = getString(R.string.weather_wind_speed_value, value);
        textWindSpeed.setText(windSpeed);
    }

    // STOPSHIP
    // Only for task #2
    private void showCity(String city) {
        Log.d(TAG, city);
        buttonCity.setText(city);
    }

    @Override
    public void showLatestUpdate(String latestUpdateTime) {
        String latestUpdateTimeString = getString(R.string.weather_latest_update_time_value, latestUpdateTime);
        textLatestUpdate.setText(latestUpdateTimeString);
    }

}
