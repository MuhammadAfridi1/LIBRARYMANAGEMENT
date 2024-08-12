import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {

    public static JSONObject getWeatherData(String locationName) {
        JSONArray locationData = fetchLocationData(locationName);
        if (locationData == null || locationData.isEmpty()) {
            System.out.println("Error: Location data not found");
            return null;
        }

        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        String weatherApiUrl = buildWeatherApiUrl(latitude, longitude);

        JSONObject weatherData = fetchWeatherDetails(weatherApiUrl);
        if (weatherData == null) {
            System.out.println("Error: Weather data not found");
            return null;
        }

        return extractWeatherData(weatherData);
    }

    private static JSONArray fetchLocationData(String locationName) {
        String formattedLocationName = locationName.replace(" ", "+");
        String locationApiUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                formattedLocationName + "&count=10&language=en&format=json";

        JSONObject locationResponse = fetchApiResponse(locationApiUrl);
        if (locationResponse == null) {
            System.out.println("Error: Could not retrieve location data");
            return null;
        }

        return (JSONArray) locationResponse.get("results");
    }

    private static String buildWeatherApiUrl(double latitude, double longitude) {
        return "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&timezone=America%2FLos_Angeles";
    }

    private static JSONObject fetchWeatherDetails(String apiUrl) {
        return fetchApiResponse(apiUrl);
    }

    private static JSONObject extractWeatherData(JSONObject weatherResponse) {
        JSONObject hourlyData = (JSONObject) weatherResponse.get("hourly");
        int currentTimeIndex = findCurrentTimeIndex((JSONArray) hourlyData.get("time"));

        double temperature = getValueFromJsonArray(hourlyData, "temperature_2m", currentTimeIndex);
        long humidity = (long) getValueFromJsonArray(hourlyData, "relativehumidity_2m", currentTimeIndex);
        double windspeed = getValueFromJsonArray(hourlyData, "windspeed_10m", currentTimeIndex);
        String weatherCondition = convertWeatherCode(getValueFromJsonArray(hourlyData, "weathercode", currentTimeIndex));

        JSONObject weatherData = new JSONObject();
        weatherData.put("temperature", temperature);
        weatherData.put("weather_condition", weatherCondition);
        weatherData.put("humidity", humidity);
        weatherData.put("windspeed", windspeed);

        return weatherData;
    }

    private static JSONObject fetchApiResponse(String urlString) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                return null;
            }

            String response = readApiResponse(conn);
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readApiResponse(HttpURLConnection conn) throws IOException {
        StringBuilder response = new StringBuilder();
        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
        }
        return response.toString();
    }

    private static int findCurrentTimeIndex(JSONArray timeArray) {
        String currentTime = getCurrentFormattedTime();
        for (int i = 0; i < timeArray.size(); i++) {
            if (currentTime.equalsIgnoreCase((String) timeArray.get(i))) {
                return i;
            }
        }
        return 0;
    }

    private static String getCurrentFormattedTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        return now.format(formatter);
    }

    private static double getValueFromJsonArray(JSONObject jsonObject, String key, int index) {
        JSONArray array = (JSONArray) jsonObject.get(key);
        return Double.parseDouble(array.get(index).toString());
    }

    private static String convertWeatherCode(double weatherCode) {
        if (weatherCode == 0L) {
            return "Clear";
        } else if (weatherCode <= 3L) {
            return "Cloudy";
        } else if ((weatherCode >= 51L && weatherCode <= 67L)
                || (weatherCode >= 80L && weatherCode <= 99L)) {
            return "Rain";
        } else if (weatherCode >= 71L && weatherCode <= 77L) {
            return "Snow";
        }
        return "Unknown";
    }
}
