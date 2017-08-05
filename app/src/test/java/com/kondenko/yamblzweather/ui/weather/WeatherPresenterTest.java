package com.kondenko.yamblzweather.ui.weather;

import android.content.Context;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.data.weather.WeatherModel;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 22.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    private static final String DEFAULT = "default";
    private static final String F = "f";
    private static final int REFRESH_RATE = 10;
    private static final long TIMESTAMP = 69;

    @Mock
    WeatherView weatherView;
    @Mock
    GetCurrentWeatherInteractor getCurrentWeatherInteractor;
    @Mock
    SettingsManager settingsManager;
    @Mock
    UpdateWeatherInteractor updateWeatherInteractor;
    @Mock
    GetCurrentCityInteractor getCurrentCityInteractor;
    @Mock
    JobsScheduler weatherJobScheduler;
    @Mock
    Context context;
    @Mock
    WeatherModel weatherModel;
    @Mock
    WeatherModel.Main main;

    @Before
    public void setUp() {
        when(settingsManager.getCity()).thenReturn(Const.ID_MOSCOW);
        when(settingsManager.getUnitKey()).thenReturn(DEFAULT);
        when(settingsManager.getUnitValue()).thenReturn(F);
        when(settingsManager.getRefreshRateHr()).thenReturn(REFRESH_RATE);

        when(weatherModel.getMain()).thenReturn(main);
    }

    @Test
    public void shouldLoadData() throws Exception {
        WeatherPresenter presenter = new WeatherPresenter(getCurrentWeatherInteractor, updateWeatherInteractor, getCurrentCityInteractor);
        InOrder loadingInOrder = inOrder(weatherView, getCurrentWeatherInteractor);
        presenter.attachView(weatherView);
        presenter.updateData();
        verify(settingsManager).getRefreshRateHr();
        verify(weatherJobScheduler).scheduleUpdateJob(REFRESH_RATE);
        verifyNoMoreInteractions(weatherJobScheduler);

        loadingInOrder.verify(weatherView).showLoading(true);

        verifyNoMoreInteractions(getCurrentWeatherInteractor);

        loadingInOrder.verify(weatherView).showLoading(false);

        verify(settingsManager).getUnitKey();

        verify(weatherView).showLatestUpdate(Utils.millisTo24time(TIMESTAMP));
        verify(settingsManager).getUnitValue();

        verifyNoMoreInteractions(settingsManager);
        verifyNoMoreInteractions(weatherView);
    }

    @Test
    public void shouldShowError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(getCurrentWeatherInteractor.run()).thenReturn(Observable.error(nothing));
        WeatherPresenter weatherPresenter = new WeatherPresenter(getCurrentWeatherInteractor, updateWeatherInteractor, getCurrentCityInteractor);
        InOrder loadingInOrder = inOrder(weatherView, getCurrentWeatherInteractor);

        weatherPresenter.attachView(weatherView);
        weatherPresenter.updateData();
        verify(settingsManager).getRefreshRateHr();
        verify(weatherJobScheduler).scheduleUpdateJob(REFRESH_RATE);
        verifyNoMoreInteractions(weatherJobScheduler);

        loadingInOrder.verify(weatherView).showLoading(true);

        verifyNoMoreInteractions(getCurrentWeatherInteractor);

        loadingInOrder.verify(weatherView).showLoading(false);

        verify(settingsManager).getUnitKey();
        verifyNoMoreInteractions(settingsManager);

        verify(weatherView).showError(nothing);
        verifyNoMoreInteractions(weatherView);
    }


    @Test
    public void shouldDetachViewAndNotShowData() {
        WeatherPresenter succcessPresenter = new WeatherPresenter(getCurrentWeatherInteractor, updateWeatherInteractor, getCurrentCityInteractor);
        succcessPresenter.attachView(weatherView);
        succcessPresenter.detachView();
        succcessPresenter.updateData();

        verifyZeroInteractions(weatherView);
    }

    @Test
    public void shouldDetachViewAndNotShowError() {
        WeatherPresenter errorPresenter = new WeatherPresenter(getCurrentWeatherInteractor, updateWeatherInteractor, getCurrentCityInteractor);
        errorPresenter.attachView(weatherView);
        errorPresenter.detachView();
        errorPresenter.updateData();

        verifyZeroInteractions(weatherView);
    }
}