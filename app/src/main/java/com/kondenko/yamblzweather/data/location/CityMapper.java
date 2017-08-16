package com.kondenko.yamblzweather.data.location;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;

/**
 * Created by Mishkun on 06.08.2017.
 */

class CityMapper {
    static City dbToDomain(CityEntity cityEntity) {
        return City.create(Location.builder()
                                   .latitude(cityEntity.getLatitude())
                                   .longitude(cityEntity.getLongitude())
                                   .build(),
                           cityEntity.getName(),
                           cityEntity.getPlace_id());
    }

    static CityEntity domainToDb(City city) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setLatitude(city.location().latitude());
        cityEntity.setLongitude(city.location().longitude());
        cityEntity.setName(city.name());
        cityEntity.setSelected(false);
        cityEntity.setPlace_id(city.id());
        return cityEntity;
    }
}
