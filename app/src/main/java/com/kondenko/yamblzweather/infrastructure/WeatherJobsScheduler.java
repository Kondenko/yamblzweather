package com.kondenko.yamblzweather.infrastructure;

import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;

import java.util.concurrent.TimeUnit;

public class WeatherJobsScheduler implements JobsScheduler {

    public WeatherJobsScheduler(JobCreator appJobCreator) {
        JobManager.instance().addJobCreator(appJobCreator);
    }

    public void scheduleUpdateJob(int refreshRateHr) {
        UpdateWeatherJob.schedulePeriodicJob(TimeUnit.HOURS.toMillis(refreshRateHr));
    }
}