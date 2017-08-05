package com.kondenko.yamblzweather.ui.citysuggest;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.data.suggest.PredictionResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Mishkun on 28.07.2017.
 */

class SuggestsAdapter extends RecyclerView.Adapter<SuggestsAdapter.ViewHolder> {

    private final SortedList<PredictionResponse> predictionList;
    private PublishSubject<PredictionResponse> onClickSubject;

    public SuggestsAdapter(List<PredictionResponse> items) {
        predictionList = new SortedList<>(PredictionResponse.class, new SortedListAdapterCallback<PredictionResponse>(this) {
            @Override
            public int compare(PredictionResponse o1, PredictionResponse o2) {
                return o1.getPlace().compareTo(o2.getPlace());
            }

            @Override
            public boolean areContentsTheSame(PredictionResponse oldItem, PredictionResponse newItem) {
                return oldItem.getPlace().equals(newItem.getPlace());
            }

            @Override
            public boolean areItemsTheSame(PredictionResponse item1, PredictionResponse item2) {
                return item1.getId().equals(item2.getId());
            }
        });
        predictionList.addAll(items);
        onClickSubject = PublishSubject.create();
    }


    public void setData(List<PredictionResponse> data) {
        predictionList.beginBatchedUpdates();
        predictionList.clear();
        predictionList.addAll(data);
        predictionList.endBatchedUpdates();
    }

    Observable<PredictionResponse> getItemClicks() {
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
        holder.predictionResponse = predictionList.get(position);
        holder.textView.setText(predictionList.get(position).getPlace());
        final PredictionResponse element = predictionList.get(position);
        holder.itemView.setOnClickListener(v -> onClickSubject.onNext(element));
    }

    @Override
    public int getItemCount() {
        return predictionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView textView;
        PredictionResponse predictionResponse;

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

