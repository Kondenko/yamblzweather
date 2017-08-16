package com.kondenko.yamblzweather.ui;

import android.os.Parcelable;

public interface BaseView<M extends Parcelable> {

    void setData(M data);

    void showLoading(boolean loading);

    void showError(Throwable error);

}
