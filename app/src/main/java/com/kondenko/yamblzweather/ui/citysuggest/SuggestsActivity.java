package com.kondenko.yamblzweather.ui.citysuggest;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.ui.BaseMvpActivity;
import com.kondenko.yamblzweather.utils.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;

public class SuggestsActivity extends BaseMvpActivity<SuggestsViewModel, SuggestsPresenter> implements SuggestsView {
    private static final String TAG = SuggestsActivity.class.getSimpleName();
    @BindView(R.id.search_field)
    EditText searchField;

    @BindView(R.id.suggests_progress_bar)
    ContentLoadingProgressBar suggestsProgressBar;

    @BindView(R.id.suggests_view)
    RecyclerView suggestsView;

    private SuggestsAdapter suggestsAdapter;

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
        setToolbar(toolbar, true);
        suggestsAdapter = new SuggestsAdapter(new ArrayList<>());
        suggestsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        suggestsView.setAdapter(suggestsAdapter);
        suggestsView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        suggestsView.setHasFixedSize(true);
    }

    @Override
    public void setData(SuggestsViewModel data) {
        super.setData(data);
        suggestsAdapter.setData(data.predictions());
    }

    @Override
    public void showLoading(boolean loading) {
        if (loading) {
            suggestsProgressBar.show();
        } else {
            suggestsProgressBar.hide();
        }
    }

    @Override
    public void showError(Throwable error) {
        Logger.w(TAG, error);
        Toast.makeText(this, this.getString(R.string.error_loading_cities), Toast.LENGTH_LONG).show();
    }

    @Override
    public Observable<String> getCityNamesStream() {
        return RxTextView.textChanges(searchField).skipInitialValue().map(CharSequence::toString);
    }

    @Override
    public Observable<Prediction> getClicks() {
        return suggestsAdapter.getItemClicks();
    }

    @Override
    public void finishScreen() {
        this.finish();
    }
}
