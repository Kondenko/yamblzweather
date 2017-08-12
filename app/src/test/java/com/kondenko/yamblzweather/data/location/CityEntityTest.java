package com.kondenko.yamblzweather.data.location;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;

/**
 * Created by Mishkun on 11.08.2017.
 */
public class CityEntityTest {
    private static final String ID = "ID";
    private static final String NAME = "IZHEVSK";
    private static final double longitude = 0.32;
    private static final double latitude = 0.78;

    private static final String ID2 = "IDA";
    private static final String NAME2 = "IZHDEVSK";
    private static final double longitude2 = 0.52;
    private static final double latitude2 = 0.18;
    private CityEntity cityEntity;
    private CityEntity cityEntity3;
    private CityEntity cityEntity2;
    private CityEntity cityEntity4;

    @Before
    public void setUp() throws Exception {
        cityEntity = setupCityEntity(NAME, latitude, longitude, ID);
        cityEntity3 = setupCityEntity(NAME, latitude, longitude, ID);
        cityEntity2 = setupCityEntity(NAME2, latitude2, longitude2, ID2);
        cityEntity4 = setupCityEntity(NAME2, latitude2, longitude2, ID2);
    }

    private CityEntity setupCityEntity(String name, double lat, double lon, String id) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(name);
        cityEntity.setLatitude(lat);
        cityEntity.setLongitude(lon);
        cityEntity.setPlace_id(id);
        return cityEntity;
    }

    @Test
    public void shouldBeEqualToSelf() throws Exception {
        assertEquals(cityEntity, cityEntity);
    }

    @Test
    public void shouldBeNotEqualToNull() throws Exception {
        assertFalse(cityEntity.equals(null));
    }

    @Test
    public void shouldBeEqual() throws Exception {
        assertEquals(cityEntity, cityEntity3);
        assertEquals(cityEntity2, cityEntity4);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        assertFalse(cityEntity.equals(cityEntity2));
        assertFalse(cityEntity3.equals(cityEntity4));
    }

    @Test
    public void shouldHaveRightHashCode() throws Exception {
        assertEquals(cityEntity.hashCode(), cityEntity3.hashCode());
        assertEquals(cityEntity2.hashCode(), cityEntity4.hashCode());
    }

}