package com.sanvalero.countriesapi.service;

import com.sanvalero.countriesapi.domain.Country;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

import static com.sanvalero.countriesapi.util.Constants.URL;

/**
 * Creado por @ author: Pedro Orós
 * el 05/03/2021
 */
public class CountriesService {

    private CountriesApiService api;

    public CountriesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(CountriesApiService.class);
    }
    public List<Country> getAllCountries() {
        Call<List<Country>> allCountriesCall = api.getAllCountries();
        try {
            Response<List<Country>> response = allCountriesCall.execute();
            return response.body();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public List<Country> getCountriesByRegion(String region) {
        Call<List<Country>> countryByRegionCall = api.getCountriesByRegion(region);
        try {
            Response<List<Country>> response = countryByRegionCall.execute();
            return response.body();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
