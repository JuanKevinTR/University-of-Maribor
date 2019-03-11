package sample;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Global variables
    private int town_id;
    private String town_lon;
    private String town_lat;
    private String unit;
    private Forecast forecast;
    private String display;
    private boolean errorFound;
    private String PATH_TO_IMAGES = "src/sample/images/";


    // Variables of views from fxml file
    @FXML
    private AnchorPane cloudsMapDisplay;
    @FXML
    private AnchorPane currentDisplay;
    @FXML
    private ScrollPane forecast5Day3HourDisplay;
    @FXML
    private AnchorPane headerDisplay;
    @FXML
    private AnchorPane mainDisplay;
    @FXML
    private AnchorPane precipitationMapDisplay;
    @FXML
    private AnchorPane pressureMapDisplay;
    @FXML
    private AnchorPane temperatureMapDisplay;
    @FXML
    private AnchorPane windSpeedMapDisplay;


    @FXML
    private ImageView currentImage;

    @FXML
    private Label currentCloudinessLabel;
    @FXML
    private Label currentDateLabel;
    @FXML
    private Label currentHumidityLabel;
    @FXML
    private Label currentLattitudeLabel;
    @FXML
    private Label currentLongitudeLabel;
    @FXML
    private Label currentPressureLabel;
    @FXML
    private Label currentSunriseLabel;
    @FXML
    private Label currentSunsetLabel;
    @FXML
    private Label currentTemperatureLabel;
    @FXML
    private Label currentTempMaxLabel;
    @FXML
    private Label currentTempMinLabel;
    @FXML
    private Label currentTownLabel;
    @FXML
    private Label currentVisibilityLabel;
    @FXML
    private Label currentWindDirectionLabel;
    @FXML
    private Label currentWindSpeedLabel;
    @FXML
    private Label mainDisplayLabel;


    @FXML
    private JFXButton cloudsMapButton;
    @FXML
    private JFXButton currentButton;
    @FXML
    private JFXButton forecast5Day3HourButton;
    @FXML
    private JFXButton laspalmasdegcButton;
    @FXML
    private JFXButton mariborButton;
    @FXML
    private JFXButton precipitationMapButton;
    @FXML
    private JFXButton pressureMapButton;
    @FXML
    private JFXButton temperatureMapButton;
    @FXML
    private JFXButton windSpeedMapButton;


    @FXML
    private JFXRadioButton radioCelsius;
    @FXML
    private JFXRadioButton radioFahrenheit;


    @FXML
    private WebView cloudsWebView;
    @FXML
    private WebView precipitationWebView;
    @FXML
    private WebView pressureWebView;
    @FXML
    private WebView temperatureWebView;
    @FXML
    private WebView windSpeedWebView;

    /*
    *
    * VARIABLES FOR FORECAST 5-3
    *
    * */
    @FXML
    private Label FDate0;
    @FXML
    private Label FHour0;
    @FXML
    private ImageView FImage0;
    @FXML
    private Label FTemp0;
    @FXML
    private Label FWind0;
    @FXML
    private Label FPress0;

    @FXML
    private Label FDate1;
    @FXML
    private Label FHour1;
    @FXML
    private ImageView FImage1;
    @FXML
    private Label FTemp1;
    @FXML
    private Label FWind1;
    @FXML
    private Label FPress1;

    @FXML
    private Label FDate2;
    @FXML
    private Label FHour2;
    @FXML
    private ImageView FImage2;
    @FXML
    private Label FTemp2;
    @FXML
    private Label FWind2;
    @FXML
    private Label FPress2;

    @FXML
    private Label FDate3;
    @FXML
    private Label FHour3;
    @FXML
    private ImageView FImage3;
    @FXML
    private Label FTemp3;
    @FXML
    private Label FWind3;
    @FXML
    private Label FPress3;

      @FXML
    private Label FDate4;
    @FXML
    private Label FHour4;
    @FXML
    private ImageView FImage4;
    @FXML
    private Label FTemp4;
    @FXML
    private Label FWind4;
    @FXML
    private Label FPress4;

    
    @FXML
    private Label FHour5;
    @FXML
    private ImageView FImage5;
    @FXML
    private Label FTemp5;
    @FXML
    private Label FWind5;
    @FXML
    private Label FPress5;

    @FXML
    private Label FHour6;
    @FXML
    private ImageView FImage6;
    @FXML
    private Label FTemp6;
    @FXML
    private Label FWind6;
    @FXML
    private Label FPress6;

    @FXML
    private Label FHour7;
    @FXML
    private ImageView FImage7;
    @FXML
    private Label FTemp7;
    @FXML
    private Label FWind7;
    @FXML
    private Label FPress7;

    @FXML
    private ImageView FImage8;
    @FXML
    private Label FTemp8;
    @FXML
    private Label FWind8;
    @FXML
    private Label FPress8;

    @FXML
    private ImageView FImage9;
    @FXML
    private Label FTemp9;
    @FXML
    private Label FWind9;
    @FXML
    private Label FPress9;

    @FXML
    private ImageView FImage10;
    @FXML
    private Label FTemp10;
    @FXML
    private Label FWind10;
    @FXML
    private Label FPress10;

    @FXML
    private ImageView FImage11;
    @FXML
    private Label FTemp11;
    @FXML
    private Label FWind11;
    @FXML
    private Label FPress11;

    @FXML
    private ImageView FImage12;
    @FXML
    private Label FTemp12;
    @FXML
    private Label FWind12;
    @FXML
    private Label FPress12;

    @FXML
    private ImageView FImage13;
    @FXML
    private Label FTemp13;
    @FXML
    private Label FWind13;
    @FXML
    private Label FPress13;

    @FXML
    private ImageView FImage14;
    @FXML
    private Label FTemp14;
    @FXML
    private Label FWind14;
    @FXML
    private Label FPress14;

    @FXML
    private ImageView FImage15;
    @FXML
    private Label FTemp15;
    @FXML
    private Label FWind15;
    @FXML
    private Label FPress15;

    @FXML
    private ImageView FImage16;
    @FXML
    private Label FTemp16;
    @FXML
    private Label FWind16;
    @FXML
    private Label FPress16;

    @FXML
    private ImageView FImage17;
    @FXML
    private Label FTemp17;
    @FXML
    private Label FWind17;
    @FXML
    private Label FPress17;

    @FXML
    private ImageView FImage18;
    @FXML
    private Label FTemp18;
    @FXML
    private Label FWind18;
    @FXML
    private Label FPress18;

    @FXML
    private ImageView FImage19;
    @FXML
    private Label FTemp19;
    @FXML
    private Label FWind19;
    @FXML
    private Label FPress19;

    @FXML
    private ImageView FImage20;
    @FXML
    private Label FTemp20;
    @FXML
    private Label FWind20;
    @FXML
    private Label FPress20;

    @FXML
    private ImageView FImage21;
    @FXML
    private Label FTemp21;
    @FXML
    private Label FWind21;
    @FXML
    private Label FPress21;

    @FXML
    private ImageView FImage22;
    @FXML
    private Label FTemp22;
    @FXML
    private Label FWind22;
    @FXML
    private Label FPress22;

    @FXML
    private ImageView FImage23;
    @FXML
    private Label FTemp23;
    @FXML
    private Label FWind23;
    @FXML
    private Label FPress23;

    @FXML
    private ImageView FImage24;
    @FXML
    private Label FTemp24;
    @FXML
    private Label FWind24;
    @FXML
    private Label FPress24;

    @FXML
    private ImageView FImage25;
    @FXML
    private Label FTemp25;
    @FXML
    private Label FWind25;
    @FXML
    private Label FPress25;

    @FXML
    private ImageView FImage26;
    @FXML
    private Label FTemp26;
    @FXML
    private Label FWind26;
    @FXML
    private Label FPress26;

    @FXML
    private ImageView FImage27;
    @FXML
    private Label FTemp27;
    @FXML
    private Label FWind27;
    @FXML
    private Label FPress27;

    @FXML
    private ImageView FImage28;
    @FXML
    private Label FTemp28;
    @FXML
    private Label FWind28;
    @FXML
    private Label FPress28;

    @FXML
    private ImageView FImage29;
    @FXML
    private Label FTemp29;
    @FXML
    private Label FWind29;
    @FXML
    private Label FPress29;

    @FXML
    private ImageView FImage30;
    @FXML
    private Label FTemp30;
    @FXML
    private Label FWind30;
    @FXML
    private Label FPress30;

    @FXML
    private ImageView FImage31;
    @FXML
    private Label FTemp31;
    @FXML
    private Label FWind31;
    @FXML
    private Label FPress31;

    @FXML
    private ImageView FImage32;
    @FXML
    private Label FTemp32;
    @FXML
    private Label FWind32;
    @FXML
    private Label FPress32;

    @FXML
    private ImageView FImage33;
    @FXML
    private Label FTemp33;
    @FXML
    private Label FWind33;
    @FXML
    private Label FPress33;

    @FXML
    private ImageView FImage34;
    @FXML
    private Label FTemp34;
    @FXML
    private Label FWind34;
    @FXML
    private Label FPress34;

    @FXML
    private ImageView FImage35;
    @FXML
    private Label FTemp35;
    @FXML
    private Label FWind35;
    @FXML
    private Label FPress35;

    @FXML
    private ImageView FImage36;
    @FXML
    private Label FTemp36;
    @FXML
    private Label FWind36;
    @FXML
    private Label FPress36;

    @FXML
    private ImageView FImage37;
    @FXML
    private Label FTemp37;
    @FXML
    private Label FWind37;
    @FXML
    private Label FPress37;

    @FXML
    private ImageView FImage38;
    @FXML
    private Label FTemp38;
    @FXML
    private Label FWind38;
    @FXML
    private Label FPress38;

    @FXML
    private ImageView FImage39;
    @FXML
    private Label FTemp39;
    @FXML
    private Label FWind39;
    @FXML
    private Label FPress39;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        display = "main";
        unit = "metric";
        radioCelsius.setSelected(true);
        errorFound = false;
    }



    /*
     *
     * Methods for on Mouse Click Action
     *
     * */
    public void onMariborButtonClicked() {
        town_id = 3195506;
        town_lon = "15.65";
        town_lat = "46.55";
        mariborButton.setDisable(true);
        laspalmasdegcButton.setDisable(false);
        currentTownLabel.setText("Weather in Maribor, SI");
        if (display.equals("main")) {
            currentCondition();
        } else {
            whichDisplay();
        }
    }

    public void onLasPalmasDeGCButtonClicked() {
        // My home town
        town_id = 2515270;
        town_lon = "-15.41";
        town_lat = "28.1";
        mariborButton.setDisable(false);
        laspalmasdegcButton.setDisable(true);
        currentTownLabel.setText("Weather in Las Palmas de Gran Canaria, ES");
        if (display.equals("main")) {
            currentCondition();
        } else {
            whichDisplay();
        }
    }

    public void onCelsiusClicked() {
        radioCelsius.setSelected(true);
        radioFahrenheit.setSelected(false);
        radioCelsius.setDisable(true);
        radioFahrenheit.setDisable(false);
        unit = "metric";
        whichDisplay();
    }

    public void onFahrenheitClicked() {
        radioCelsius.setSelected(false);
        radioFahrenheit.setSelected(true);
        radioCelsius.setDisable(false);
        radioFahrenheit.setDisable(true);
        unit = "imperial";
        whichDisplay();
    }

    public void onExitClicked() {
        Platform.exit();
        System.exit(0);
    }



    /*
     *
     * Methods for manage each display and on Mouse Click Action
     *
     * */
    public void currentCondition() {
        display = "current";

        if (readJsonFromUrl(getURL(display)) != null) {
            JSONObject json = readJsonFromUrl(getURL(display));
            assert json != null;
            forecast = new Forecast(json);

            //Setting the view with data from Json Object
            currentDateLabel.setText(formatDateHour(forecast.getDate()));

            currentImage.setImage(new Image(new File(PATH_TO_IMAGES + forecast.getIcon() + ".png").toURI().toString()));
            if (unit.equals("metric")) {
                currentTemperatureLabel.setText(forecast.getTemperature() + " °C");
                currentTempMinLabel.setText(forecast.getTempMin() + " °C");
                currentTempMaxLabel.setText(forecast.getTempMax() + " °C");
                currentWindSpeedLabel.setText(forecast.getWindSpeed() + " meter/sec");
            } else {
                currentTemperatureLabel.setText(forecast.getTemperature() + " °F");
                currentTempMinLabel.setText(forecast.getTempMin() + " °F");
                currentTempMaxLabel.setText(forecast.getTempMax() + " °F");
                currentWindSpeedLabel.setText(forecast.getWindSpeed() + " miles/hour");
            }

            if (!forecast.getWindDirection().equals("-")) {
                currentWindDirectionLabel.setText(forecast.getWindDirection() + " degrees");
            } else {
                currentWindDirectionLabel.setText("");
            }

            currentSunriseLabel.setText(formatHour(forecast.getSunrise()));
            currentSunsetLabel.setText(formatHour(forecast.getSunset()));
            currentHumidityLabel.setText(forecast.getHumidity() + " %");
            currentVisibilityLabel.setText(forecast.getVisibility() + " meters");
            currentPressureLabel.setText(forecast.getPressure() + " hPa");
            currentLattitudeLabel.setText(forecast.getLattitude());
            currentLongitudeLabel.setText(forecast.getLongitude());
            currentCloudinessLabel.setText(forecast.getText());

        } else {
            errorFound = true;
        }

        enableDisable();
    }

    public void forecast5Day3Hour() {
        display = "forecast5Day3Hour";

        if (readJsonFromUrl(getURL(display)) != null) {
            JSONObject json = readJsonFromUrl(getURL(display));

            // We get Array where it's all data from JSON
            assert json != null;
            JSONArray list = json.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {

                // we get every single position from array, and we convert in a JsonObject to get data from Constructor
                // in Forecast Class
                JSONObject data = list.getJSONObject(i);
                forecast = new Forecast(data, "forecast5_3");

                // We pass data which we want to show in the right position
                initForecast5_3(i, forecast.getDate(), forecast.getIcon(), forecast.getTemperature(), forecast.getWindSpeed(), forecast.getPressure());

            }

        } else {
            errorFound = true;
        }

        enableDisable();
    }

    private void initForecast5_3(int position, String date, String img, String temp, String wind, String press) {
        switch (position) {
            case 0:
                FDate0.setText(formatDate(date));
                FHour0.setText(formatHour(date));
                FImage0.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp0.setText(temp + " °C");
                    FWind0.setText(wind + " m/s");
                } else {
                    FTemp0.setText(temp + " °F");
                    FWind0.setText(wind + " m/h");
                }

                FPress0.setText(press);
                break;
            case 1:
                FHour1.setText(formatHour(date));
                FImage1.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp1.setText(temp + " °C");
                    FWind1.setText(wind + " m/s");
                } else {
                    FTemp1.setText(temp + " °F");
                    FWind1.setText(wind + " m/h");
                }

                FPress1.setText(press);
                break;
            case 2:
                FHour2.setText(formatHour(date));
                FImage2.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp2.setText(temp + " °C");
                    FWind2.setText(wind + " m/s");
                } else {
                    FTemp2.setText(temp + " °F");
                    FWind2.setText(wind + " m/h");
                }

                FPress2.setText(press);
                break;
            case 3:
                FHour3.setText(formatHour(date));
                FImage3.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp3.setText(temp + " °C");
                    FWind3.setText(wind + " m/s");
                } else {
                    FTemp3.setText(temp + " °F");
                    FWind3.setText(wind + " m/h");
                }

                FPress3.setText(press);
                break;
            case 4:
                FHour4.setText(formatHour(date));
                FImage4.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp4.setText(temp + " °C");
                    FWind4.setText(wind + " m/s");
                } else {
                    FTemp4.setText(temp + " °F");
                    FWind4.setText(wind + " m/h");
                }

                FPress4.setText(press);
                break;
            case 5:
                FHour5.setText(formatHour(date));
                FImage5.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp5.setText(temp + " °C");
                    FWind5.setText(wind + " m/s");
                } else {
                    FTemp5.setText(temp + " °F");
                    FWind5.setText(wind + " m/h");
                }

                FPress5.setText(press);
                break;
            case 6:
                FHour6.setText(formatHour(date));
                FImage6.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp6.setText(temp + " °C");
                    FWind6.setText(wind + " m/s");
                } else {
                    FTemp6.setText(temp + " °F");
                    FWind6.setText(wind + " m/h");
                }

                FPress6.setText(press);
                break;
            case 7:
                FHour7.setText(formatHour(date));
                FImage7.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp7.setText(temp + " °C");
                    FWind7.setText(wind + " m/s");
                } else {
                    FTemp7.setText(temp + " °F");
                    FWind7.setText(wind + " m/h");
                }

                FPress7.setText(press);
                break;
            case 8:
                FDate1.setText(formatDate(date));
                FImage8.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp8.setText(temp + " °C");
                    FWind8.setText(wind + " m/s");
                } else {
                    FTemp8.setText(temp + " °F");
                    FWind8.setText(wind + " m/h");
                }

                FPress8.setText(press);
                break;
            case 9:
                FImage9.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp9.setText(temp + " °C");
                    FWind9.setText(wind + " m/s");
                } else {
                    FTemp9.setText(temp + " °F");
                    FWind9.setText(wind + " m/h");
                }

                FPress9.setText(press);
                break;
            case 10:
                FImage10.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp10.setText(temp + " °C");
                    FWind10.setText(wind + " m/s");
                } else {
                    FTemp10.setText(temp + " °F");
                    FWind10.setText(wind + " m/h");
                }

                FPress10.setText(press);
                break;
            case 11:
                FImage11.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp11.setText(temp + " °C");
                    FWind11.setText(wind + " m/s");
                } else {
                    FTemp11.setText(temp + " °F");
                    FWind11.setText(wind + " m/h");
                }

                FPress11.setText(press);
                break;
            case 12:
                FImage12.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp12.setText(temp + " °C");
                    FWind12.setText(wind + " m/s");
                } else {
                    FTemp12.setText(temp + " °F");
                    FWind12.setText(wind + " m/h");
                }

                FPress12.setText(press);
                break;
            case 13:
                FImage13.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp13.setText(temp + " °C");
                    FWind13.setText(wind + " m/s");
                } else {
                    FTemp13.setText(temp + " °F");
                    FWind13.setText(wind + " m/h");
                }

                FPress13.setText(press);
                break;
            case 14:
                FImage14.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp14.setText(temp + " °C");
                    FWind14.setText(wind + " m/s");
                } else {
                    FTemp14.setText(temp + " °F");
                    FWind14.setText(wind + " m/h");
                }

                FPress14.setText(press);
                break;
            case 15:
                FImage15.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp15.setText(temp + " °C");
                    FWind15.setText(wind + " m/s");
                } else {
                    FTemp15.setText(temp + " °F");
                    FWind15.setText(wind + " m/h");
                }

                FPress15.setText(press);
                break;
            case 16:
                FDate2.setText(formatDate(date));
                FImage16.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp16.setText(temp + " °C");
                    FWind16.setText(wind + " m/s");
                } else {
                    FTemp16.setText(temp + " °F");
                    FWind16.setText(wind + " m/h");
                }

                FPress16.setText(press);
                break;
            case 17:
                FImage17.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp17.setText(temp + " °C");
                    FWind17.setText(wind + " m/s");
                } else {
                    FTemp17.setText(temp + " °F");
                    FWind17.setText(wind + " m/h");
                }

                FPress17.setText(press);
                break;
            case 18:
                FImage18.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp18.setText(temp + " °C");
                    FWind18.setText(wind + " m/s");
                } else {
                    FTemp18.setText(temp + " °F");
                    FWind18.setText(wind + " m/h");
                }

                FPress18.setText(press);
                break;
            case 19:
                FImage19.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp19.setText(temp + " °C");
                    FWind19.setText(wind + " m/s");
                } else {
                    FTemp19.setText(temp + " °F");
                    FWind19.setText(wind + " m/h");
                }

                FPress19.setText(press);
                break;
            case 20:
                FImage20.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp20.setText(temp + " °C");
                    FWind20.setText(wind + " m/s");
                } else {
                    FTemp20.setText(temp + " °F");
                    FWind20.setText(wind + " m/h");
                }

                FPress20.setText(press);
                break;
            case 21:
                FImage21.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp21.setText(temp + " °C");
                    FWind21.setText(wind + " m/s");
                } else {
                    FTemp21.setText(temp + " °F");
                    FWind21.setText(wind + " m/h");
                }

                FPress21.setText(press);
                break;
            case 22:
                FImage22.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp22.setText(temp + " °C");
                    FWind22.setText(wind + " m/s");
                } else {
                    FTemp22.setText(temp + " °F");
                    FWind22.setText(wind + " m/h");
                }

                FPress22.setText(press);
                break;
            case 23:
                FImage23.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp23.setText(temp + " °C");
                    FWind23.setText(wind + " m/s");
                } else {
                    FTemp23.setText(temp + " °F");
                    FWind23.setText(wind + " m/h");
                }

                FPress23.setText(press);
                break;
            case 24:
                FDate3.setText(formatDate(date));
                FImage24.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp24.setText(temp + " °C");
                    FWind24.setText(wind + " m/s");
                } else {
                    FTemp24.setText(temp + " °F");
                    FWind24.setText(wind + " m/h");
                }

                FPress24.setText(press);
                break;
            case 25:
                FImage25.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp25.setText(temp + " °C");
                    FWind25.setText(wind + " m/s");
                } else {
                    FTemp25.setText(temp + " °F");
                    FWind25.setText(wind + " m/h");
                }

                FPress25.setText(press);
                break;
            case 26:
                FImage26.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp26.setText(temp + " °C");
                    FWind26.setText(wind + " m/s");
                } else {
                    FTemp26.setText(temp + " °F");
                    FWind26.setText(wind + " m/h");
                }

                FPress26.setText(press);
                break;
            case 27:
                FImage27.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp27.setText(temp + " °C");
                    FWind27.setText(wind + " m/s");
                } else {
                    FTemp27.setText(temp + " °F");
                    FWind27.setText(wind + " m/h");
                }

                FPress27.setText(press);
                break;
            case 28:
                FImage28.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp28.setText(temp + " °C");
                    FWind28.setText(wind + " m/s");
                } else {
                    FTemp28.setText(temp + " °F");
                    FWind28.setText(wind + " m/h");
                }

                FPress28.setText(press);
                break;
            case 29:
                FImage29.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp29.setText(temp + " °C");
                    FWind29.setText(wind + " m/s");
                } else {
                    FTemp29.setText(temp + " °F");
                    FWind29.setText(wind + " m/h");
                }

                FPress29.setText(press);
                break;
            case 30:
                FImage30.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp30.setText(temp + " °C");
                    FWind30.setText(wind + " m/s");
                } else {
                    FTemp30.setText(temp + " °F");
                    FWind30.setText(wind + " m/h");
                }

                FPress30.setText(press);
                break;
            case 31:
                FImage31.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp31.setText(temp + " °C");
                    FWind31.setText(wind + " m/s");
                } else {
                    FTemp31.setText(temp + " °F");
                    FWind31.setText(wind + " m/h");
                }

                FPress31.setText(press);
                break;
            case 32:
                FDate4.setText(formatDate(date));
                FImage32.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp32.setText(temp + " °C");
                    FWind32.setText(wind + " m/s");
                } else {
                    FTemp32.setText(temp + " °F");
                    FWind32.setText(wind + " m/h");
                }

                FPress32.setText(press);
                break;
            case 33:
                FImage33.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp33.setText(temp + " °C");
                    FWind33.setText(wind + " m/s");
                } else {
                    FTemp33.setText(temp + " °F");
                    FWind33.setText(wind + " m/h");
                }

                FPress33.setText(press);
                break;
            case 34:
                FImage34.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp34.setText(temp + " °C");
                    FWind34.setText(wind + " m/s");
                } else {
                    FTemp34.setText(temp + " °F");
                    FWind34.setText(wind + " m/h");
                }

                FPress34.setText(press);
                break;
            case 35:
                FImage35.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp35.setText(temp + " °C");
                    FWind35.setText(wind + " m/s");
                } else {
                    FTemp35.setText(temp + " °F");
                    FWind35.setText(wind + " m/h");
                }

                FPress35.setText(press);
                break;
            case 36:
                FImage36.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp36.setText(temp + " °C");
                    FWind36.setText(wind + " m/s");
                } else {
                    FTemp36.setText(temp + " °F");
                    FWind36.setText(wind + " m/h");
                }

                FPress36.setText(press);
                break;
            case 37:
                FImage37.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp37.setText(temp + " °C");
                    FWind37.setText(wind + " m/s");
                } else {
                    FTemp37.setText(temp + " °F");
                    FWind37.setText(wind + " m/h");
                }

                FPress37.setText(press);
                break;
            case 38:
                FImage38.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp38.setText(temp + " °C");
                    FWind38.setText(wind + " m/s");
                } else {
                    FTemp38.setText(temp + " °F");
                    FWind38.setText(wind + " m/h");
                }

                FPress38.setText(press);
                break;
            case 39:
                FImage39.setImage(new Image(new File(PATH_TO_IMAGES + img + ".png").toURI().toString()));

                if (unit.equals("metric")) {
                    FTemp39.setText(temp + " °C");
                    FWind39.setText(wind + " m/s");
                } else {
                    FTemp39.setText(temp + " °F");
                    FWind39.setText(wind + " m/h");
                }

                FPress39.setText(press);
                break;

        }
    }

    public void precipitationMap() {
        display = "precipitationMap";

        WebEngine web = precipitationWebView.getEngine();
        web.load(getURL(display));

        enableDisable();
    }

    public void temperatureMap() {
        display = "temperatureMap";

        WebEngine web = temperatureWebView.getEngine();
        web.load(getURL(display));

        enableDisable();
    }

    public void pressureMap() {
        display = "pressureMap";

        WebEngine web = pressureWebView.getEngine();
        web.load(getURL(display));

        enableDisable();
    }

    public void windSpeedMap() {
        display = "windSpeedMap";

        WebEngine web = windSpeedWebView.getEngine();
        web.load(getURL(display));

        enableDisable();
    }

    public void cloudsMap() {
        display = "cloudsMap";

        WebEngine web = cloudsWebView.getEngine();
        web.load(getURL(display));

        enableDisable();
    }



    /*
     *
     * Methods for data convertions
     *
     * */
    private String formatDate(String dateInUTC) {
        long unixSeconds = Long.parseLong(dateInUTC);

        // Convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds * 1000L);

        // The format of date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.YYYY");

        // Give a timezone reference for formatting
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));

        return sdf.format(date);
    }

    private String formatHour(String dateInUTC) {
        long unixSeconds = Long.parseLong(dateInUTC);

        // Convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds * 1000L);

        // The format of date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");

        // Give a timezone reference for formatting
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));

        return sdf.format(date);
    }

    private String formatDateHour(String dateInUTC) {
        long unixSeconds = Long.parseLong(dateInUTC);

        // Convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds * 1000L);

        // The format of date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm MMM dd");

        // Give a timezone reference for formatting
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));

        return sdf.format(date);
    }



    /*
     *
     * Methods for many things
     *
     * */
    private String getURL(String search) {
        String URL = "";

        String API_KEY = "58062035e959fe86fc16b28f386c14be";

        switch (search) {
            case "current":
                URL = "http://api.openweathermap.org/data/2.5/weather?id=" + town_id + "&units=" + unit + "&APPID=" + API_KEY;
                System.out.println("current: " + URL);
                break;
            case "forecast5Day3Hour":
                URL = "http://api.openweathermap.org/data/2.5/forecast?id=" + town_id + "&units=" + unit + "&APPID=" + API_KEY;
                System.out.println("forecast5Day3Hour: " + URL);
                break;
            case "precipitationMap":
                URL = "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=precipitation&lat=" + town_lat + "&lon=" + town_lon + "&zoom=9";
                System.out.println("precipitationMap: " + URL);
                break;
            case "temperatureMap":
                URL = "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=temperature&lat=" + town_lat + "&lon=" + town_lon + "&zoom=9";
                System.out.println("temperatureMap: " + URL);
                break;
            case "pressureMap":
                URL = "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=pressure&lat=" + town_lat + "&lon=" + town_lon + "&zoom=9";
                System.out.println("pressureMap: " + URL);
                break;
            case "windSpeedMap":
                URL = "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=windspeed&lat=" + town_lat + "&lon=" + town_lon + "&zoom=9";
                System.out.println("windSpeedMap: " + URL);
                break;
            case "cloudsMap":
                URL = "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=clouds&lat=" + town_lat + "&lon=" + town_lon + "&zoom=9";
                System.out.println("cloudsMap: " + URL);
                break;
        }
        return URL;
    }

    private JSONObject readJsonFromUrl(String url) throws JSONException {
        StringBuilder sb = new StringBuilder();
        int cp;

        try (InputStream jsonReceived = new URL(url).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonReceived, Charset.forName("UTF-8")));

            while ((cp = reader.read()) != -1) {
                sb.append((char) cp); // cp = ASCII Decimal number
            }
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            System.out.println("\tError: we cannot get JSON FILE from OpenWeatherMap");
        }
        return null;
    }

    private void enableDisable() {
        if (!errorFound) {
            switch (display) {
                case "current":
                    mainDisplay.setVisible(false);

                    headerDisplay.setVisible(true);
                    currentDisplay.setVisible(true);
                    forecast5Day3HourDisplay.setVisible(false);
                    precipitationMapDisplay.setVisible(false);
                    temperatureMapDisplay.setVisible(false);
                    pressureMapDisplay.setVisible(false);
                    windSpeedMapDisplay.setVisible(false);
                    cloudsMapDisplay.setVisible(false);

                    currentButton.setDisable(true);
                    forecast5Day3HourButton.setDisable(false);
                    precipitationMapButton.setDisable(false);
                    temperatureMapButton.setDisable(false);
                    pressureMapButton.setDisable(false);
                    windSpeedMapButton.setDisable(false);
                    cloudsMapButton.setDisable(false);
                    break;
                case "forecast5Day3Hour":
                    mainDisplay.setVisible(false);

                    headerDisplay.setVisible(true);
                    currentDisplay.setVisible(false);
                    forecast5Day3HourDisplay.setVisible(true);
                    precipitationMapDisplay.setVisible(false);
                    temperatureMapDisplay.setVisible(false);
                    pressureMapDisplay.setVisible(false);
                    windSpeedMapDisplay.setVisible(false);
                    cloudsMapDisplay.setVisible(false);

                    currentButton.setDisable(false);
                    forecast5Day3HourButton.setDisable(true);
                    precipitationMapButton.setDisable(false);
                    temperatureMapButton.setDisable(false);
                    pressureMapButton.setDisable(false);
                    windSpeedMapButton.setDisable(false);
                    cloudsMapButton.setDisable(false);
                    break;
                case "precipitationMap":
                    mainDisplay.setVisible(false);

                    headerDisplay.setVisible(false);
                    currentDisplay.setVisible(false);
                    forecast5Day3HourDisplay.setVisible(false);
                    precipitationMapDisplay.setVisible(true);
                    temperatureMapDisplay.setVisible(false);
                    pressureMapDisplay.setVisible(false);
                    windSpeedMapDisplay.setVisible(false);
                    cloudsMapDisplay.setVisible(false);

                    currentButton.setDisable(false);
                    forecast5Day3HourButton.setDisable(false);
                    precipitationMapButton.setDisable(true);
                    temperatureMapButton.setDisable(false);
                    pressureMapButton.setDisable(false);
                    windSpeedMapButton.setDisable(false);
                    cloudsMapButton.setDisable(false);
                    break;
                case "temperatureMap":
                    mainDisplay.setVisible(false);

                    headerDisplay.setVisible(false);
                    currentDisplay.setVisible(false);
                    forecast5Day3HourDisplay.setVisible(false);
                    precipitationMapDisplay.setVisible(false);
                    temperatureMapDisplay.setVisible(true);
                    pressureMapDisplay.setVisible(false);
                    windSpeedMapDisplay.setVisible(false);
                    cloudsMapDisplay.setVisible(false);

                    currentButton.setDisable(false);
                    forecast5Day3HourButton.setDisable(false);
                    precipitationMapButton.setDisable(false);
                    temperatureMapButton.setDisable(true);
                    pressureMapButton.setDisable(false);
                    windSpeedMapButton.setDisable(false);
                    cloudsMapButton.setDisable(false);
                    break;
                case "pressureMap":
                    mainDisplay.setVisible(false);

                    headerDisplay.setVisible(false);
                    currentDisplay.setVisible(false);
                    forecast5Day3HourDisplay.setVisible(false);
                    precipitationMapDisplay.setVisible(false);
                    temperatureMapDisplay.setVisible(false);
                    pressureMapDisplay.setVisible(true);
                    windSpeedMapDisplay.setVisible(false);
                    cloudsMapDisplay.setVisible(false);

                    currentButton.setDisable(false);
                    forecast5Day3HourButton.setDisable(false);
                    precipitationMapButton.setDisable(false);
                    temperatureMapButton.setDisable(false);
                    pressureMapButton.setDisable(true);
                    windSpeedMapButton.setDisable(false);
                    cloudsMapButton.setDisable(false);
                    break;
                case "windSpeedMap":
                    mainDisplay.setVisible(false);

                    headerDisplay.setVisible(false);
                    currentDisplay.setVisible(false);
                    forecast5Day3HourDisplay.setVisible(false);
                    precipitationMapDisplay.setVisible(false);
                    temperatureMapDisplay.setVisible(false);
                    pressureMapDisplay.setVisible(false);
                    windSpeedMapDisplay.setVisible(true);
                    cloudsMapDisplay.setVisible(false);

                    currentButton.setDisable(false);
                    forecast5Day3HourButton.setDisable(false);
                    precipitationMapButton.setDisable(false);
                    temperatureMapButton.setDisable(false);
                    pressureMapButton.setDisable(false);
                    windSpeedMapButton.setDisable(true);
                    cloudsMapButton.setDisable(false);
                    break;
                case "cloudsMap":
                    mainDisplay.setVisible(false);

                    headerDisplay.setVisible(false);
                    currentDisplay.setVisible(false);
                    forecast5Day3HourDisplay.setVisible(false);
                    precipitationMapDisplay.setVisible(false);
                    temperatureMapDisplay.setVisible(false);
                    pressureMapDisplay.setVisible(false);
                    windSpeedMapDisplay.setVisible(false);
                    cloudsMapDisplay.setVisible(true);

                    currentButton.setDisable(false);
                    forecast5Day3HourButton.setDisable(false);
                    precipitationMapButton.setDisable(false);
                    temperatureMapButton.setDisable(false);
                    pressureMapButton.setDisable(false);
                    windSpeedMapButton.setDisable(false);
                    cloudsMapButton.setDisable(true);
                    break;
            }
        } else {
            mainDisplayLabel.setFont(new Font("System", 12));
            mainDisplayLabel.setText("Error --> getting JSON File from OpenWeatherMap");

            mainDisplay.setVisible(true);

            headerDisplay.setVisible(false);
            currentDisplay.setVisible(false);
            forecast5Day3HourDisplay.setVisible(false);
            precipitationMapDisplay.setVisible(false);
            temperatureMapDisplay.setVisible(false);
            pressureMapDisplay.setVisible(false);
            windSpeedMapDisplay.setVisible(false);
            cloudsMapDisplay.setVisible(false);

            errorFound = false;
        }

    }

    private void whichDisplay() {
        switch (display) {
            case "current":
                currentCondition();
                break;
            case "forecast5Day3Hour":
                forecast5Day3Hour();
                break;
            case "precipitationMap":
                precipitationMap();
                break;
            case "temperatureMap":
                temperatureMap();
                break;
            case "pressureMap":
                pressureMap();
                break;
            case "windSpeedMap":
                windSpeedMap();
                break;
            case "cloudsMap":
                cloudsMap();
                break;
        }
    }




}
















