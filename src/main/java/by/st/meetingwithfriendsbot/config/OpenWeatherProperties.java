package by.st.meetingwithfriendsbot.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "weather")
@Getter
public class OpenWeatherProperties {
    private String openWeatherUrlPartOne;
    private String openWeatherUrlPartTwo;
}
