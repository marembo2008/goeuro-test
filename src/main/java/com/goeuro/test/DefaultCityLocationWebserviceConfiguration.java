package com.goeuro.test;

import com.google.common.primitives.Ints;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.System.getProperty;

/**
 *
 * @author marembo (marembo2008@gmail.com)
 * @since Mar 23, 2016, 7:34:55 PM
 */
public class DefaultCityLocationWebserviceConfiguration implements CityLocationWebserviceConfiguration {

    @Nonnull
    @Override
    public String getEndpointUrl() {
        return checkNotNull(getProperty("webservice.endpoint.url"),
                            "You must define an endpoint url for webservice at the following system property: webservice.endpoint.url");
    }

    @Nonnull
    @Override
    public int getConnectionTimeout() {
        final String definedConnectionTimeoutProperty = getProperty("webservice.endpoint.connectionTimeout", "100");
        final Integer connectionTimeout = Ints.tryParse(definedConnectionTimeoutProperty);
        return checkNotNull(connectionTimeout,
                            "Please define a correct integral timeout for: (webservice.endpoint.connectionTimeout = %s)",
                            definedConnectionTimeoutProperty);
    }

    @Nonnull
    @Override
    public int getReadTimeout() {
        final String definedReadTimeoutProperty = getProperty("webservice.endpoint.readTimeout", "100");
        final Integer readTimeout = Ints.tryParse(definedReadTimeoutProperty);
        return checkNotNull(readTimeout,
                            "Please define a correct integral timeout for: (webservice.endpoint.readTimeout = %s)",
                            definedReadTimeoutProperty);
    }
}
