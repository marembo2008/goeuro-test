package com.goeuro.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author marembo (marembo2008@gmail.com)
 * @since Mar 23, 2016, 7:37:23 PM
 */
public class CityLocationCsvLoader {

    static {
        //to make our default configuration happy
        //of course could be replace by more robust configuration system
        System.setProperty("webservice.endpoint.url", "http://api.goeuro.com/api/v2/position/suggest/en/");
        System.setProperty("webservice.endpoint.connectionTimeout", "300");
        System.setProperty("webservice.endpoint.readTimeout", "300");
    }

    private final CityLocationQueryService cityLocationQueryService;

    public CityLocationCsvLoader() {
        this.cityLocationQueryService = new CityLocationQueryService();
    }

    public void writeOutToCsv(@Nonnull final String cityName) throws IOException {
        requireNonNull(cityName, "The cityName must not be null");

        final List<CityLocation> cityLocations = cityLocationQueryService.getCityLocations(cityName);

        //of course this is a little inefficient if the data is huge.
        //but for this, lets assume the memory is not an issue
        final String csvCommaDelimitedData = cityLocations
                .stream()
                .map(this::createCsvData)
                .collect(Collectors.joining("\n"));

        final File file = new File(cityName + ".csv");

        System.out.println("csv file: " + file.getAbsolutePath());

        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(csvCommaDelimitedData.getBytes());
        }
    }

    @Nonnull
    private String createCsvData(@Nonnull final CityLocation cityLocation) {
        final GeoPosition geoPosition = cityLocation.getGeoPosition();
        return format("%s,%s,%s,%s,%s", cityLocation.getId(), cityLocation.getName(), cityLocation.getType(),
                      geoPosition.getLatitude(), geoPosition.getLongitude());
    }

    public static void main(String[] args) throws IOException {
        checkState(args.length == 1, "Expected single argument call to the city location retriever app");

        final String cityName = args[0];
        final CityLocationCsvLoader cityLocationCsvLoader = new CityLocationCsvLoader();

        //We have propagated all errors up the chain. This is to enable debugging and to allow a fail fast mechanism
        cityLocationCsvLoader.writeOutToCsv(cityName);
    }
}
