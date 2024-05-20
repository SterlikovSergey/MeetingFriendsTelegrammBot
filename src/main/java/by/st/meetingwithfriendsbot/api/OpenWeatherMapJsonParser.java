package by.st.meetingwithfriendsbot.api;

import by.st.meetingwithfriendsbot.utils.WeatherUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class OpenWeatherMapJsonParser implements WeatherParser {
    private final static String USER_AGENT = "Mozilla/5.0";

    public static String downloadJsonRawData(String latitude, String longitude) throws Exception {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=7f20b84b1fdaac62d65b2d84fca4a5be";
        log.info("urlString: " + urlString);
        URL urlObject = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new IllegalArgumentException();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        log.info("response" + response);
        return response.toString();
    }

    public static String parseForecastDataFromList(String jsonData) throws Exception {
        final StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode weatherArray = rootNode.get("weather");
        if (weatherArray.isArray()) {
            for (final JsonNode weatherNode : weatherArray) {
                String main = weatherNode.get("main").asText();
                JsonNode mainNode = rootNode.get("main");
                double temperature = mainNode.get("temp").asDouble(); // Температура уже в Цельсиях, так как мы использовали параметр units=metric в URL.
                sb.append(formatForecastData(main, temperature));
            }
        }
        return sb.toString();
    }

    private static String formatForecastData(String main, double temperature) {
        String formattedTemperature = String.format("%+.2f", temperature);
        String weatherIconCode = WeatherUtils.weatherIconsCodes.get(main);

        return String.format("%s %s %s%s", main, formattedTemperature, weatherIconCode, System.lineSeparator());
    }

    @Override
    public String getReadyForecast(String latitude, String longitude) {
        String result;
        try {
            String jsonRawData = downloadJsonRawData(latitude, longitude);
            result = parseForecastDataFromList(jsonRawData);
        } catch (IllegalArgumentException e) {
            return "Can't find location";
        } catch (Exception e) {
            e.printStackTrace();
            return "The service is not available, please try later";
        }
        return result;
    }
}
