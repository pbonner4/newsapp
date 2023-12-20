package newsApp.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Repository
public class JdbcLiveSportsDao implements LiveSportsDataDao {

    @Value("${nbaSportsApi}")
    private String nbaSportsApi;
    @Value("${nflSportsApi}")
    private String nflSportsApi;
    @Value("${nhlSportsApi}")
    private String nhlSportsApi;
    @Value("${mmaSportsApi}")
    private String mmaSportsApi;
    @Value("${nbaBoxScoreApi}")
    private String nbaBoxScoreApi;

    @Override
    public String getNbaScores() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                nbaSportsApi,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>(){});

        Map<String, Object> responseMap = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = "";
        try {
            responseJson = objectMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    @Override
    public String getNhlScores() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                nhlSportsApi,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>(){});

        Map<String, Object> responseMap = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = "";
        try {
            responseJson = objectMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    @Override
    public String getMmaScores() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                mmaSportsApi,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>(){});

        Map<String, Object> responseMap = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = "";
        try {
            responseJson = objectMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    @Override
    public String getNflScores() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                nflSportsApi,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>(){});

        Map<String, Object> responseMap = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = "";
        try {
            responseJson = objectMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    @Override
    public String getNbaBoxScores(String eventId) {
            RestTemplate restTemplate = new RestTemplate();


        System.out.println(eventId);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(nbaBoxScoreApi)
                    .queryParam("event", eventId);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>(){});

            Map<String, Object> responseMap = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = "";
            try {
                responseJson = objectMapper.writeValueAsString(responseMap);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return responseJson;
    }


}

