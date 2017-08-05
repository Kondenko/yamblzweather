package com.kondenko.yamblzweather.ui.citysuggest;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.Prediction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Mishkun on 28.07.2017.
 */

class SuggestsAdapter extends RecyclerView.Adapter<SuggestsAdapter.ViewHolder> {

    private final SortedList<Prediction> predictionList;
    private PublishSubject<Prediction> onClickSubject;

    public SuggestsAdapter(List<Prediction> items) {
        predictionList = new SortedList<>(Prediction.class, new SortedListAdapterCallback<Prediction>(this) {
            @Override
            public int compare(Prediction o1, Prediction o2) {
                return o1.id().compareTo(o2.id());
            }

            @Override
            public boolean areContentsTheSame(Prediction oldItem, Prediction newItem) {
                return oldItem.id().equals(newItem.id());
            }

            @Override
            public boolean areItemsTheSame(Prediction item1, Prediction item2) {
                return item1.id().equals(item2.id());
            }
        });
        predictionList.addAll(items);
        onClickSubject = PublishSubject.create();
    }


    public void setData(List<Prediction> data) {
        predictionList.beginBatchedUpdates();
        predictionList.clear();
        predictionList.addAll(data);
        predictionList.endBatchedUpdates();
    }

    Observable<Prediction> getItemClicks() {
        return onClickSubject.hide();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_city_suggest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.Prediction = predictionList.get(position);
        holder.textView.setText(predictionList.get(position).name());
        final Prediction element = predictionList.get(position);
        holder.itemView.setOnClickListener(v -> onClickSubject.onNext(element));
    }

    @Override
    public int getItemCount() {
        return predictionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView textView;
        Prediction Prediction;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = (TextView) view.findViewById(R.id.suggest_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView.getText() + "'";
        }
    }
}

