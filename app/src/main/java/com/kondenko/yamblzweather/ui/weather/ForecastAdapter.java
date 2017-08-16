package com.kondenko.yamblzweather.ui.weather;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mishkun on 09.08.2017.
 */

class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = ForecastAdapter.class.getSimpleName();
    private final static DateFormat containerDateFormat = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
    private final SortedList<Weather> weatherSortedList;
    private TempUnit tempUnit;

    ForecastAdapter(List<Weather> items) {
        weatherSortedList = new SortedList<>(Weather.class, new SortedListAdapterCallback<Weather>(this) {
            @Override
            public int compare(Weather o1, Weather o2) {
                return (int) (o1.timestamp() - o2.timestamp());
            }

            @Override
            public boolean areContentsTheSame(Weather oldItem, Weather newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Weather item1, Weather item2) {
                return item1.equals(item2);
            }
        });
        weatherSortedList.addAll(items);
    }

    void setWeather(List<Weather> data, TempUnit tempUnit) {
        this.tempUnit = tempUnit;
        weatherSortedList.beginBatchedUpdates();
        weatherSortedList.clear();
        weatherSortedList.addAll(data);
        weatherSortedList.endBatchedUpdates();
        notifyDataSetChanged();
    }


    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ForecastAdapter.ViewHolder holder, int position) {
        Weather weather = weatherSortedList.get(position);
        holder.weather = weather;
        holder.dateView.setText(containerDateFormat.format(weather.timestamp() * 1000L));
        holder.dayTemp.setText(TemperatureFormatter.format(weather.dayTemperature(), tempUnit, Locale.getDefault()));
        holder.nightTemp.setText(TemperatureFormatter.format(weather.nightTemperature(), tempUnit, Locale.getDefault()));
        holder.weatherIconView.setBackgroundResource(ConditionMapper.mapDark(weather.weatherConditions()));
    }

    @Override
    public int getItemCount() {
        return weatherSortedList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView dateView;
        final TextView nightTemp;
        final TextView dayTemp;
        final ImageView weatherIconView;
        Weather weather;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            dateView = (TextView) view.findViewById(R.id.forecast_date_text);
            dayTemp = (TextView) view.findViewById(R.id.forecast_day_temp);
            nightTemp = (TextView) view.findViewById(R.id.forecast_night_temp);
            weatherIconView = (ImageView) view.findViewById(R.id.weather_forecast_icon_condition);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dateView.getText() + "'";
        }
    }
}
