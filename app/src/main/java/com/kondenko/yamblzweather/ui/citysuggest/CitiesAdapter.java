package com.kondenko.yamblzweather.ui.citysuggest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.City;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Mishkun on 07.08.2017.
 */

class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = CitiesAdapter.class.getSimpleName();
    private final SortedList<City> citiesList;
    private final PublishSubject<City> onClickSubject;
    private final PublishSubject<City> onDeleteClickSubject;
    private final int selectedColor;
    private final int plainColor;
    private int selectedCity;

    CitiesAdapter(Context context, List<City> cities) {
        citiesList = new SortedList<>(City.class, new SortedListAdapterCallback<City>(this) {
            @Override
            public int compare(City o1, City o2) {
                return o1.name().compareTo(o2.name());
            }

            @Override
            public boolean areContentsTheSame(City oldItem, City newItem) {
                return oldItem.id().equals(newItem.id());
            }

            @Override
            public boolean areItemsTheSame(City item1, City item2) {
                return item1.id().equals(item2.id());
            }
        });
        citiesList.addAll(cities);
        onClickSubject = PublishSubject.create();
        onDeleteClickSubject = PublishSubject.create();
        selectedColor = ContextCompat.getColor(context, R.color.colorYandexAccent);
        plainColor = ContextCompat.getColor(context, R.color.colorTextPrimary);
    }

    void setCities(List<City> data, City city) {
        citiesList.beginBatchedUpdates();
        citiesList.clear();
        citiesList.addAll(data);
        selectedCity = citiesList.indexOf(city);
        citiesList.endBatchedUpdates();
    }

    Observable<City> getItemClicks() {
        return onClickSubject.hide();
    }

    Observable<City> getDeletionClicks() {
        return onDeleteClickSubject.hide();
    }

    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_favorite, parent, false);
        return new CitiesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CitiesAdapter.ViewHolder holder, int position) {
        holder.city = citiesList.get(position);
        holder.textView.setText(citiesList.get(position).name());
        if (position == selectedCity) {
            holder.textView.setTextColor(selectedColor);
        } else {
            holder.textView.setTextColor(plainColor);
        }
        if (citiesList.size() <= 1) {
            holder.button.setVisibility(View.GONE);
        } else {
            holder.button.setVisibility(View.VISIBLE);
        }
        final City element = citiesList.get(position);
        holder.itemView.setOnClickListener(v -> onClickSubject.onNext(element));
        holder.button.setOnClickListener(v -> onDeleteClickSubject.onNext(element));
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView textView;
        final ImageButton button;
        City city;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = (TextView) view.findViewById(R.id.suggest_text);
            button = (ImageButton) view.findViewById(R.id.delete_city_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView.getText() + "'";
        }
    }

}
