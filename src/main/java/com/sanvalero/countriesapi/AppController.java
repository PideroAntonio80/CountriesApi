package com.sanvalero.countriesapi;

import com.sanvalero.countriesapi.domain.Country;
import com.sanvalero.countriesapi.service.CountriesService;
import com.sanvalero.countriesapi.util.AlertUtils;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * Creado por @ author: Pedro Orós
 * el 05/03/2021
 */

public class AppController implements Initializable {

    public TableView<Country> tvData;
    public WebView wvFlag;
    public Label lStatus;
    public ComboBox<String> cbContinent;

    private WebEngine engine;

    private CountriesService countriesService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fijarColumnasTabla();

        String[] continents = new String[]{"<Todos>", "Africa", "Asia", "America", "Europa", "Oceania", "Antartida"};
        cbContinent.setValue("<Todos>");
        cbContinent.setItems(FXCollections.observableArrayList(continents));

        engine = wvFlag.getEngine();
        countriesService = new CountriesService();
    }                                                   //  TODO Ajustar tamaño bandera y Meter más filtros de Búsqueda

    @FXML
    public void allCountries() {
        String whatToShow = cbContinent.getValue();

        switch (whatToShow) {
            case "<Todos>":
                CompletableFuture.runAsync(this::loadingCountries);
                loadingWarning();

                break;

            case "Africa":
                CompletableFuture.runAsync(() -> loadingCountriesByContinent("Africa"));
                loadingWarning();

                break;

            case "Asia":
                CompletableFuture.runAsync(() -> loadingCountriesByContinent("Asia"));
                loadingWarning();

                break;

            case "America":
                CompletableFuture.runAsync(() -> loadingCountriesByContinent("Americas"));
                loadingWarning();

                break;

            case "Europa":
                CompletableFuture.runAsync(() -> loadingCountriesByContinent("Europe"));
                loadingWarning();

                break;

            case "Oceania":
                CompletableFuture.runAsync(() -> loadingCountriesByContinent("Oceania"));
                loadingWarning();

                break;

            case "Antartida":
                CompletableFuture.runAsync(() -> loadingCountriesByContinent("Polar"));
                loadingWarning();

                break;

            default:
                AlertUtils.mostrarInformacion("Debes elegir una opción");

                break;
        }
    }

    @FXML
    public void dataDetail() {
        Country country = tvData.getSelectionModel().getSelectedItem();
        String flag = country.getFlag();
        engine.load(flag);
    }

    public void fijarColumnasTabla() {
        Field[] fields = Country.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("flag"))
                continue;

            TableColumn<Country, String> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tvData.getColumns().add(column);
        }
        tvData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void loadingCountries() {
        List<Country> countries = countriesService.getAllCountries();

        tvData.setItems(FXCollections.observableArrayList(countries));
    }

    public void loadingCountriesByContinent(String continent) {
        List<Country> countries = countriesService.getCountriesByRegion(continent);

        tvData.setItems(FXCollections.observableArrayList(countries));
    }

    public void transicionLabelAviso(int segundos) {
        lStatus.setVisible(true);
        PauseTransition visiblePause = new PauseTransition((Duration.seconds(segundos)));
        visiblePause.setOnFinished(event -> lStatus.setVisible(false));
        visiblePause.play();
    }

    public void loadingWarning() {
        lStatus.setText("Cargando Paises...");
        transicionLabelAviso(2);
    }
}
