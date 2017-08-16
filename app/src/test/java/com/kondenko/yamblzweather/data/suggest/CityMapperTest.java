package com.kondenko.yamblzweather.data.suggest;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Mishkun on 11.08.2017.
 */
public class CityMapperTest {
    private static final String PLACE_ID = "ID";
    private static final String NAME = "IZHEVSK";
    private static final double latitude = 0.56;
    private static final double longitude = 0.23;
    private CitySearchResult citySearchResult;
    private City city;

    @Before
    public void setUp() throws Exception {
        CitySearchResult.Result.Geometry.Coord coords = new CitySearchResult.Result.Geometry.Coord(latitude, longitude);
        CitySearchResult.Result.Geometry geometry = new CitySearchResult.Result.Geometry(coords);
        CitySearchResult.Result result = new CitySearchResult.Result(geometry, NAME, PLACE_ID);
        citySearchResult = new CitySearchResult(result);

        city = City.create(Location.builder().latitude(latitude).longitude(longitude).build(), NAME, PLACE_ID);
    }

    @Test
    public void shouldConvertResponseToDomain() throws Exception {
        assertEquals(CityMapper.responseToDomain(citySearchResult), city);
    }

}