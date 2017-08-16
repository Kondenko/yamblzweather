package com.kondenko.yamblzweather.ui.citysuggest;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.ui.BaseMvpActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;

public class SuggestsActivity extends BaseMvpActivity<SuggestsViewModel, SuggestsPresenter> implements SuggestsView {
    @SuppressWarnings("unused")     private static final String TAG = SuggestsActivity.class.getSimpleName();
    @BindView(R.id.search_field)
    EditText searchField;

    @BindView(R.id.suggests_progress_bar)
    ContentLoadingProgressBar suggestsProgressBar;

    @BindView(R.id.suggests_view)
    RecyclerView suggestsView;

    @BindView(R.id.cities_view)
    RecyclerView citiesView;

    @BindView(R.id.suggests_city_error)
    LinearLayout errorTextView;
    private SuggestsAdapter suggestsAdapter;
    private CitiesAdapter citiesAdapter;
    private boolean canQuit;

    @Inject
    public void Inject(SuggestsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suggests);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, false);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_suggests);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        citiesAdapter = new CitiesAdapter(this, new ArrayList<>());
        citiesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        citiesView.setAdapter(citiesAdapter);
        citiesView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        suggestsAdapter = new SuggestsAdapter(new ArrayList<>());
        suggestsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        suggestsView.setAdapter(suggestsAdapter);
        suggestsView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void setData(SuggestsViewModel data) {
        super.setData(data);
        errorTextView.setVisibility(View.GONE);
        if (data.predictions().isEmpty() && !data.cities().isEmpty()) {
            suggestsView.setVisibility(View.GONE);
            citiesView.setVisibility(View.VISIBLE);
            citiesAdapter.setCities(data.cities(), data.selectedCity());
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                canQuit = true;
            }
        } else if (!data.predictions().isEmpty()) {
            citiesView.setVisibility(View.GONE);
            suggestsView.setVisibility(View.VISIBLE);
            suggestsAdapter.setPredictions(data.predictions());
        } else {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                canQuit = false;
            }
        }
    }

    @Override
    public void showLoading(boolean loading) {
        if (loading) {
            suggestsProgressBar.show();
            citiesView.setVisibility(View.GONE);
            suggestsView.setVisibility(View.GONE);
        } else {
            suggestsProgressBar.hide();
        }
    }

    @Override
    public void showError(Throwable error) {
        citiesView.setVisibility(View.GONE);
        suggestsView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (canQuit) {
            super.onBackPressed();
        }
    }

    @Override
    public Observable<String> getCityNamesStream() {
        return RxTextView.textChanges(searchField).map(CharSequence::toString).map(String::trim);
    }

    @Override
    public Observable<Prediction> getSuggestsClicks() {
        return suggestsAdapter.getItemClicks();
    }

    @Override
    public Observable<City> getCitiesClicks() {
        return citiesAdapter.getItemClicks();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public Observable<City> getCitiesDeletionsClicks() {
        return citiesAdapter.getDeletionClicks();
    }

    @Override
    public void finishScreen() {
        this.finish();
    }
}
