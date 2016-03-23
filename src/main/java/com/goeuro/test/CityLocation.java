package com.goeuro.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author marembo (marembo2008@gmail.com)
 * @since Mar 23, 2016, 7:17:15 PM
 */
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CityLocation {

    @Nonnull
    @JsonProperty("_id")
    private String id;

    @Nonnull
    private String locationId;

    @Nullable
    private String key;

    @Nonnull
    private String name;

    @Nonnull
    private String fullName;

    @Nullable
    @JsonProperty("iata_airport_code")
    private String iataAirportCode;

    @Nonnull
    private String type;

    @Nonnull
    private String country;

    @Nonnull
    @JsonProperty("geo_position")
    private GeoPosition geoPosition;

    @Nonnull
    @JsonProperty("inEurope")
    private boolean inEurope;

    @Nonnull
    @JsonProperty("countryCode")
    private String countryCode;

    @Nonnull
    @JsonProperty("coreCountry")
    private boolean coreCountry;

    @Nullable
    private String distance;

}
