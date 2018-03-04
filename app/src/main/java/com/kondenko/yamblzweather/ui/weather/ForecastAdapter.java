package com.kondenko.yamblzweather.ui.weather;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.graphics.Color.TRANSPARENT;

/**
 * Created by Mishkun on 09.08.2017.
 */

class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = ForecastAdapter.class.getSimpleName();
    private final static DateFormat containerDateFormat =
        new SimpleDateFormat("E, dd MMM", Locale.getDefault());
    private final SortedList<Weather> weatherSortedList;
    private final RecyclerView recyclerView;
    private final OnItemClickListener onItemClickListener;
    private TempUnit tempUnit;
    private int expandedPosition = -1;

    interface OnItemClickListener {
        void call(Weather item, boolean isSelected);
    }

    ForecastAdapter(List<Weather> items, RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.recyclerView = recyclerView;
        this.onItemClickListener = onItemClickListener;
        weatherSortedList =
            new SortedList<>(Weather.class, new SortedListAdapterCallback<Weather>(this) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Weather weather = weatherSortedList.get(position);
        Boolean expanded = position == expandedPosition;
        holder.updateData(weather);
        holder.dateView.setText(containerDateFormat.format(weather.timestamp() * 1000L));
        holder.dayTemp.setText(TemperatureFormatter.format(weather.dayTemperature(), tempUnit, Locale.getDefault()));
        holder.nightTemp.setText(TemperatureFormatter.format(weather.nightTemperature(), tempUnit, Locale.getDefault()));
        holder.weatherIconView.setBackgroundResource(ConditionMapper.mapDark(weather.weatherConditions()));
        holder.expand(expanded);
        holder.view.setOnClickListener(v -> {
            int previousPosition = expandedPosition;
            expandedPosition = expanded ? -1 : position;
            if (previousPosition != -1) notifyItemChanged(previousPosition);
            if (position != -1) notifyItemChanged(position);
            onItemClickListener.call(weather, !expanded);
        });
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
        final LineChart lineChart;
        Weather weather;
        private final LineDataSet baselineDataSet;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            dateView = (TextView) view.findViewById(R.id.forecast_date_text);
            dayTemp = (TextView) view.findViewById(R.id.forecast_day_temp);
            nightTemp = (TextView) view.findViewById(R.id.forecast_night_temp);
            weatherIconView = (ImageView) view.findViewById(R.id.weather_forecast_icon_condition);
            lineChart = (LineChart) view.findViewById(R.id.lineChart);
            lineChart.getDescription().setEnabled(false);
            lineChart.setTouchEnabled(false);
            lineChart.setDrawGridBackground(false);
            lineChart.getAxisLeft().setEnabled(false);
            lineChart.getAxisRight().setEnabled(false);
            lineChart.getLegend().setEnabled(false);
            lineChart.getXAxis().setEnabled(false);
            ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry(0, 0));
            entries.add(new Entry(1, 0));
            entries.add(new Entry(2, 0));
            baselineDataSet = new LineDataSet(entries, "");
            baselineDataSet.setDrawCircles(false);
            baselineDataSet.setColor(view.getContext().getResources().getColor(R.color.colorPrimaryDark));
            baselineDataSet.setValueTextColor(TRANSPARENT);
            baselineDataSet.setLabel("Zero");
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dateView.getText() + "'";
        }

        void updateData(Weather data){
            weather = data;
            ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry(0, (float) data.temperature().celsiusDegrees()));
            entries.add(new Entry(1, (float) data.dayTemperature().celsiusDegrees()));
            entries.add(new Entry(2, (float) data.nightTemperature().celsiusDegrees()));
            LineDataSet lineDataSet = new LineDataSet(entries, "");
            lineDataSet.setDrawCircleHole(false);
            int color = view.getContext().getResources().getColor(R.color.colorPrimaryDark);
            int colorDark = view.getContext().getResources().getColor(R.color.colorTextPrimary);
            lineDataSet.setCircleColor(colorDark);
            lineDataSet.setFillColor(color);
            lineDataSet.setColor(color);
            lineDataSet.setValueTextSize(14);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setValueFormatter((IValueFormatter) (value, entry, dataSetIndex, viewPortHandler) -> String.format("%.1f", value));
            lineDataSet.setFillFormatter(new DefaultFillFormatter(){
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return 0;
                }
            });
            lineChart.setData(new LineData(lineDataSet, baselineDataSet));
        }

        void expand(Boolean expanded) {
            lineChart.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }
}
