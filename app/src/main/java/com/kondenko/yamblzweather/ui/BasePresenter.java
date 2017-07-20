package com.kondenko.yamblzweather.ui;

import com.kondenko.yamblzweather.utils.lifecycle.PresenterEvent;
import com.kondenko.yamblzweather.utils.lifecycle.RxLifecyclePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BasePresenter<V extends BaseView, I extends BaseInteractor>
        implements LifecycleProvider<Integer> {

    protected final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

    private WeakReference<V> viewReference;
    private I interactor;

    public BasePresenter(V view, I interactor) {
        attachView(view);
        this.interactor = interactor;
    }

    public void attachView(V view) {
        viewReference = new WeakReference<V>(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    public void detachView(boolean retainInstance) {
        viewReference.clear();
        lifecycleSubject.onNext(PresenterEvent.DETACH);
    }

    protected V getView() { return viewReference.get(); }

    protected boolean isViewAttached() { return viewReference.get() != null; }

    protected I getInteractor() { return interactor; }

    /* RxLifecycle */

    @Nonnull
    @Override
    public Observable<Integer> lifecycle() {
        return lifecycleSubject;
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull Integer event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecyclePresenter.bindPresenter(lifecycleSubject);
    }
}
