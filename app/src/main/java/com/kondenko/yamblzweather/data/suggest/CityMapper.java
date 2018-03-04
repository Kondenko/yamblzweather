package com.kondenko.yamblzweather.data.suggest;

import com.kondenko.yamblzweather.data.suggest.CitySearchResult.Result.Geometry.Coord;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;

/**
 * Created by Mishkun on 04.08.2017.
 */

class CityMapper {
    static City responseToDomain(CitySearchResult citySearchResult) {
        Coord coordinates = citySearchResult.getResult().getGeometry().getCoordinates();
        Location location = Location.builder()
                                    .latitude(coordinates.getLat())
                                    .longitude(coordinates.getLon())
                                    .build();
        return City.create(location, citySearchResult.getResult().getName(), citySearchResult.getResult().getId());
    }

}
