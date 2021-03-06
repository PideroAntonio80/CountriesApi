package com.sanvalero.countriesapi.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

/**
 * Creado por @ author: Pedro Orós
 * el 05/03/2021
 */

@Data
@Builder
public class Country {

    @SerializedName("name")
    private String country;
    private String capital;
    @SerializedName("region")
    private String continent;
    private String flag;
    private int population;
}
