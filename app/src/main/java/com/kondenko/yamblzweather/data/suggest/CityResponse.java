package com.kondenko.yamblzweather.data.suggest;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class CityResponse {
    private final String city;
    private final Coord coordinates;

    public CityResponse(Coord coords, String city) {
        int indexOf = city.indexOf(",");
        if (indexOf > 0){
            this.city = city.substring(0, indexOf);
        }else {
            this.city = city;
        }

        this.coordinates = coords;
    }

    public String getCity() {
        return city;
    }

    public Coord getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityResponse city1 = (CityResponse) o;

        if (city != null ? !city.equals(city1.city) : city1.city != null) return false;
        return coordinates != null ? coordinates.equals(city1.coordinates) : city1.coordinates == null;

    }

    @Override
    public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
