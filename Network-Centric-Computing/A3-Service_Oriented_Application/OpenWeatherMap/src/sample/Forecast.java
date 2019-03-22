package sample;

import org.json.JSONArray;
import org.json.JSONObject;

public class Forecast {

    private String date;            // (Convert to time -> unix, UTC)
    private String humidity;        // %
    private String icon;
    private String lattitude;
    private String longitude;
    private String pressure;        // hPa
    private String sunrise;         // (Convert to time -> unix, UTC)
    private String sunset;          // (Convert to time -> unix, UTC)
    private String tempMin;
    private String tempMax;
    private String temperature;     // °C (units=metric) - °F(units=imperial)
    private String text;
    private String visibility;
    private String windDirection;   // degress
    private String windSpeed;       // °C (units=metric) = meter/sec - °F(units=imperial) = miles/hour


    /**
     * Contruct for a current weather conditions
     *
     * @param json JSONObject with all date from OpenWeatherMap
     */
    public Forecast(JSONObject json) {
        JSONArray weather = json.getJSONArray("weather");

        date = json.get("dt").toString();
        humidity = json.getJSONObject("main").get("humidity").toString();
        icon = weather.getJSONObject(0).get("icon").toString();
        lattitude = json.getJSONObject("coord").get("lat").toString();
        longitude = json.getJSONObject("coord").get("lon").toString();
        pressure = json.getJSONObject("main").get("pressure").toString();
        sunrise = json.getJSONObject("sys").get("sunrise").toString();
        sunset = json.getJSONObject("sys").get("sunset").toString();
        temperature = String.valueOf(Math.round(Float.parseFloat(json.getJSONObject("main").get("temp").toString())));
        tempMin = json.getJSONObject("main").get("temp_min").toString();
        tempMax = json.getJSONObject("main").get("temp_max").toString();
        text = weather.getJSONObject(0).get("description").toString();
        visibility = json.get("visibility").toString();
        windSpeed = json.getJSONObject("wind").get("speed").toString();
        try {
            windDirection = json.getJSONObject("wind").get("deg").toString();
        } catch (Exception e) {
            System.out.println("\tException: windDirection doesn't exist");
            windDirection = "-";
        }
    }

    /**
     * Contruct for forecast 5_3 weather conditions
     *
     * @param json JSONObject with all date from OpenWeatherMap
     * @param forecast5_3 control variable
     */
    public Forecast(JSONObject json, String forecast5_3) {
        JSONArray weather = json.getJSONArray("weather");

        date = json.get("dt").toString();
        icon = weather.getJSONObject(0).get("icon").toString();
        temperature = String.valueOf(json.getJSONObject("main").get("temp").toString());
        windSpeed = json.getJSONObject("wind").get("speed").toString();
        pressure = json.getJSONObject("main").get("pressure").toString();
    }


    public String getDate() {
        return date;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getIcon() {
        return icon;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPressure() {
        return pressure;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getText() {
        return text;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }


}
