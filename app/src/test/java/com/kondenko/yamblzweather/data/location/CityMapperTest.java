package com.kondenko.yamblzweather.data.location;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Mishkun on 11.08.2017.
 */
public class CityMapperTest {

    private static final String ID = "ID";
    private static final String NAME = "IZHEVSK";
    private static final double longitude = 0.32;
    private static final double latitude = 0.78;
    City city;
    CityEntity cityEntity;

    @Before
    public void setUp() throws Exception {
        city = City.create(Location.builder().longitude(longitude).latitude(latitude).build(), NAME, ID);
        setupCityEntity();
    }

    private void setupCityEntity() {
        cityEntity = new CityEntity();
        cityEntity.setName(NAME);
        cityEntity.setLatitude(latitude);
        cityEntity.setLongitude(longitude);
        cityEntity.setPlace_id(ID);
    }

    @Test
    public void shouldConvertdbToDomain() throws Exception {
        assertEquals(CityMapper.dbToDomain(cityEntity), city);
    }

    @Test
    public void shouldConvertdomainToDb() throws Exception {
        CityEntity testCityEntity = CityMapper.domainToDb(city);
        assertEquals(testCityEntity.getLatitude(), city.location().latitude(), 0.01);
        assertEquals(testCityEntity.getLongitude(), city.location().longitude(), 0.01);
        assertEquals(testCityEntity.getName(), city.name());
        assertEquals(testCityEntity.getPlace_id(), city.id());
    }

    @Test
    public void shouldNotMissDomainData() throws Exception {
        City testCity = CityMapper.dbToDomain(CityMapper.domainToDb(city));
        assertEquals(city, testCity);
    }
}