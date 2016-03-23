package com.goeuro.test;

import javax.annotation.Nonnull;

/**
 *
 * @author marembo (marembo2008@gmail.com)
 * @since Mar 23, 2016, 7:24:56 PM
 */
public interface CityLocationWebserviceConfiguration {

    @Nonnull
    String getEndpointUrl();

    @Nonnull
    int getConnectionTimeout();

    @Nonnull
    int getReadTimeout();
}
