package com.kondenko.yamblzweather.ui.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.ui.BaseMvpActivity;
import com.kondenko.yamblzweather.ui.onboarding.OnboardingActivity;
import com.kondenko.yamblzweather.ui.settings.SettingsActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;

public class WeatherActivity extends BaseMvpActivity<WeatherViewModel, WeatherPresenter> implements WeatherView {
    @SuppressWarnings("unused")
    private static final String TAG = WeatherActivity.class.getSimpleName();

    @BindView(R.id.weather_button_city)
    Spinner spinnerCity;

    @BindView(R.id.weather_text_temperature)
    TextView textTemperature;

    @BindView(R.id.weather_icon_condition)
    ImageView weatherIcon;

    @BindView(R.id.weather_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.weather_text_latest_update)
    TextView textLatestUpdate;

    @BindView(R.id.forecast_container)
    RecyclerView forecastView;

    @BindView(R.id.weather_title)
    TextView weatherSummaryTitle;

    @BindView(R.id.scroll_view)
    @Nullable
    NestedScrollView scrollView;

    @BindView(R.id.weather_subtitle)
    TextView weatherSummarySubtitle;

    @BindView(R.id.settings_button)
    ImageButton settingsButton;

    @BindView(R.id.bottom_guideline)
    Guideline bottomGuideline;

    @BindView(R.id.arrow_image_view)
    @Nullable
    ImageButton arrowButton;

    private ArrayAdapter<City> spinnerAdapter;
    private ForecastAdapter forecastAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateData();
    }

    @Inject
    public void Inject(WeatherPresenter presenter) {
        this.presenter = presenter;
    }


    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!defaultSharedPreferences.getBoolean(getString(R.string.key_onboarding_completion), false)) {
            startActivity(new Intent(this, OnboardingActivity.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        refreshLayout.setOnRefreshListener(presenter::updateData);

        Point point = new Point();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) bottomGuideline.getLayoutParams();
        layoutParams.guideBegin = point.y;
        bottomGuideline.setLayoutParams(layoutParams);
        if (arrowButton != null && scrollView != null) {
            arrowButton.setOnClickListener(v -> scrollView.smoothScrollTo(0, point.y));
        }

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(spinnerAdapter);

        forecastAdapter = new ForecastAdapter(new ArrayList<>(), forecastView, presenter::onSelected);
        forecastView.setAdapter(forecastAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            forecastView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
        } else {
            forecastView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        forecastView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    @Override
    public void setData(WeatherViewModel weather) {
        super.setData(weather);
        setCity(weather.city(), weather.cities());
        showTemperature(weather.weather().temperature(), weather.tempUnit());
        showCondition(ConditionMapper.map(weather.weather().weatherConditions()));
        showForecast(weather.forecast().weatherList(), weather.tempUnit());
        showLatestUpdate(weather.weather().timestamp());
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 3 || hour > 16) {
            if (weather.forecast().weatherList().size() > 0) {
                showTextualTomorrow(weather.weather(), weather.forecast().weatherList().get(0));
            }
        } else {
            showTextualTonight(weather.weather());
        }
    }

    private void showTextualTonight(Weather weatherToday) {
        int titleResource = TitleMapper.map(weatherToday.temperature(),
                                            weatherToday.weatherConditions(),
                                            weatherToday.humidity());
        String title = String.format(Locale.getDefault(),
                                     getString(R.string.title_text),
                                     getString(titleResource));
        weatherSummaryTitle.setText(title);
        int subtitleResource = TitleMapper.map(weatherToday.nightTemperature(),
                                               weatherToday.weatherConditions(),
                                               weatherToday.humidity());
        String subtitle = String.format(Locale.getDefault(),
                                        getString(titleResource == subtitleResource
                                                          ? R.string.subtitle_tonight_also_text
                                                          : R.string.subtitle_tonight_text),
                                        getString(subtitleResource));
        weatherSummarySubtitle.setText(subtitle);
    }

    private void showTextualTomorrow(Weather weatherToday, Weather weatherTomorrow) {
        int titleResource = TitleMapper.map(weatherToday.temperature(),
                                            weatherToday.weatherConditions(),
                                            weatherToday.humidity());
        String title = String.format(Locale.getDefault(),
                                     getString(R.string.title_text),
                                     getString(titleResource));
        weatherSummaryTitle.setText(title);
        int subtitleResource = TitleMapper.map(weatherTomorrow.temperature(),
                                               weatherTomorrow.weatherConditions(),
                                               weatherTomorrow.humidity());
        String subtitle = String.format(Locale.getDefault(),
                                        getString(titleResource == subtitleResource
                                                          ? R.string.subtitle_tomorrow_also_text
                                                          : R.string.subtitle_tomorrow_text),
                                        getString(subtitleResource));
        weatherSummarySubtitle.setText(subtitle);
    }

    private void showForecast(List<Weather> weathers, TempUnit tempUnit) {
        forecastAdapter.setWeather(weathers, tempUnit);
    }

    private void setCity(City city, List<City> cities) {
        spinnerAdapter.clear();
        spinnerAdapter.addAll(cities);
        spinnerAdapter.notifyDataSetChanged();
        spinnerCity.setSelection(cities.indexOf(city));
    }

    @Override
    public void showLoading(boolean loading) {
        refreshLayout.setRefreshing(loading);
    }

    @Override
    public void showError(Throwable error) {
        Toast.makeText(this, this.getString(R.string.error_loading_weather), Toast.LENGTH_LONG).show();
    }

    // Precise data formatting

    private void showTemperature(Temperature temperature, TempUnit units) {
        textTemperature.setText(TemperatureFormatter.format(temperature, units, Locale.getDefault()));
    }

    private void showCondition(int condition) {
        weatherIcon.setBackgroundResource(condition);
    }

    private void showLatestUpdate(long updateTime) {
        Date date = new Date(updateTime);
        DateFormat formatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        String latestUpdateTime = formatter.format(date);
        String latestUpdateTimeString = getString(R.string.weather_latest_update_time_value, latestUpdateTime);
        textLatestUpdate.setText(latestUpdateTimeString);
    }

    @Override
    public Observable<City> getCitySelections() {
        return RxAdapterView.itemSelections(spinnerCity)
                            .skipInitialValue()
                            .map(spinnerAdapter::getItem)
                            .distinctUntilChanged();
    }


}
