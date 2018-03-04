package com.kondenko.yamblzweather.domain.guards;

/**
 * Created by Mishkun on 23.07.2017.
 */

public interface JobsScheduler {
    void scheduleUpdateJob(int refreshRateHr);
}
