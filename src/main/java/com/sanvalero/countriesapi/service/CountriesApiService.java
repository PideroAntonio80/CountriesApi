package com.sanvalero.countriesapi.service;

import com.sanvalero.countriesapi.domain.Country;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Creado por @ author: Pedro Orós
 * el 05/03/2021
 */
public interface CountriesApiService {

    @GET("/rest/v2/all")
    Call<List<Country>> getAllCountries();

    @GET("/rest/v2/name/{name}")
    Call<List<Country>> getCountry(@Path("name") String name);

    @GET("/rest/v2/region/{region}")
    Call<List<Country>> getCountriesByRegion(@Path("region") String region);
}
