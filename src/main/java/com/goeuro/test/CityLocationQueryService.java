package com.goeuro.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author marembo (marembo2008@gmail.com)
 * @since Mar 23, 2016, 7:22:55 PM
 */
public class CityLocationQueryService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //Possible injection scenario and runtime selection
    private final CityLocationWebserviceConfiguration cityLocationWebserviceConfiguration;

    private final HttpClient httpClient;

    public CityLocationQueryService() {
        this.cityLocationWebserviceConfiguration = new DefaultCityLocationWebserviceConfiguration();

        //in a managed environment, ideally should go to @PostConstruct call, after proper initialization
        final HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        this.httpClient = HttpClients.createMinimal(connectionManager);
    }

    @Nonnull
    public List<CityLocation> getCityLocations(@Nonnull final String cityName) throws IOException {
        requireNonNull(cityName, "The cityName must not be null");

        final HttpResponse httpResponse = this.httpClient.execute(createRequest(cityName));
        final StatusLine statusLine = httpResponse.getStatusLine();
        if (statusLine.getStatusCode() != 200) {
            throw new IllegalArgumentException("Error reading city location: " + statusLine.getReasonPhrase());
        }

        final HttpEntity responseEntity = httpResponse.getEntity();
        final TypeReference<List<CityLocation>> cityLocationsTypeReference = new TypeReference<List<CityLocation>>() {
        };

        return OBJECT_MAPPER.readValue(responseEntity.getContent(), cityLocationsTypeReference);
    }

    @Nonnull
    private HttpUriRequest createRequest(@Nonnull final String cityName) {
        final HttpGet httpGet = new HttpGet(format("%s/%s", cityLocationWebserviceConfiguration.getEndpointUrl(),
                                                   cityName));
        final RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectTimeout(cityLocationWebserviceConfiguration.getConnectionTimeout())
                .setSocketTimeout(cityLocationWebserviceConfiguration.getReadTimeout())
                .build();
        httpGet.setConfig(requestConfig);

        return httpGet;
    }
}
