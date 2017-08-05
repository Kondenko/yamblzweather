package com.kondenko.yamblzweather.data.suggest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class CitySuggest implements Parcelable{
    @SerializedName("predictions")
    private List<PredictionResponse> predictionResponses;

    protected CitySuggest(Parcel in) {
        predictionResponses = in.createTypedArrayList(PredictionResponse.CREATOR);
    }

    public static final Creator<CitySuggest> CREATOR = new Creator<CitySuggest>() {
        @Override
        public CitySuggest createFromParcel(Parcel in) {
            return new CitySuggest(in);
        }

        @Override
        public CitySuggest[] newArray(int size) {
            return new CitySuggest[size];
        }
    };

    public List<PredictionResponse> getPredictionResponses() {
        return predictionResponses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(predictionResponses);
    }
}
