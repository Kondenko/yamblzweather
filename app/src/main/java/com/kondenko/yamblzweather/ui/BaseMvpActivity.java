package com.kondenko.yamblzweather.ui;


import android.os.Bundle;
import android.os.Parcelable;

public abstract class BaseMvpActivity<M extends Parcelable, P extends BasePresenter>
        extends BaseActivity implements BaseView<M> {

    private static final String KEY_MODEL = "model";
    private M data;
    protected P presenter;

    @Override
    protected void onResume() {
        super.onResume();
        //noinspection unchecked
        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (data != null) outState.putParcelable(KEY_MODEL, data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelable(KEY_MODEL);
            if (data != null)
                setData(data);
        }
    }

    @Override
    public void setData(M data) {
        this.data = data;
    }

}