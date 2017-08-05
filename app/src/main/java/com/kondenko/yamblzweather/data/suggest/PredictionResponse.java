package com.kondenko.yamblzweather.data.suggest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class PredictionResponse implements Parcelable{
    @SerializedName("description")
    String place;
    @SerializedName("place_id")
    String id;

    public PredictionResponse(){

    }


    public PredictionResponse(String description, String placeId){
        place = description;
        id = placeId;
    }
    protected PredictionResponse(Parcel in) {
        place = in.readString();
        id = in.readString();
    }

    public static final Creator<PredictionResponse> CREATOR = new Creator<PredictionResponse>() {
        @Override
        public PredictionResponse createFromParcel(Parcel in) {
            return new PredictionResponse(in);
        }

        @Override
        public PredictionResponse[] newArray(int size) {
            return new PredictionResponse[size];
        }
    };

    public String getPlace() {
        return place;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(place);
        dest.writeString(id);
    }
}
