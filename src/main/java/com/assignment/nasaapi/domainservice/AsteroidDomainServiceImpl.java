package com.assignment.nasaapi.domainservice;

import com.assignment.nasaapi.entityservice.AsteroidService;
import com.assignment.nasaapi.errorhandling.AsteroidError;
import com.assignment.nasaapi.model.Asteroid;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AsteroidDomainServiceImpl implements AsteroidDomainService{

    Logger logger = LoggerFactory.getLogger(AsteroidDomainServiceImpl.class);

    @Autowired
    private AsteroidService asteroidService;

    @Value("${nasa.api_key}")
    private String apiKey;

    @Value("${nasa.asteroid_resource}")
    private String asteroidEndpoint;

    private RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<String> getAsteroidsFromAndToDate(String fromDate, String toDate) {
        //Check the cache or Call The nasa API
        //Update the database and cache.
        String resourceUrl = String.format(asteroidEndpoint, fromDate, toDate, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        return toAsteroidList(response.getBody(), fromDate, toDate);
    }

    private List<String> toAsteroidList(String response, String fromDate, String toDate){
        try {
            //Try retrieving from cache or DB first.
            List<String> asteroidsInRange = asteroidService.findTop10ByFromAndToDate(fromDate, toDate);

            if (asteroidsInRange == null || asteroidsInRange.isEmpty()){
                logger.debug("No data found in DB. Query the NASA api.");
                JsonNode jsonNode = objectMapper.readTree(IOUtils.toInputStream(response, StandardCharsets.UTF_8));

                //Not a good way but lack of time.
                ObjectNode nearEarthObjects = (ObjectNode) jsonNode.path("near_earth_objects");
                for (JsonNode node : nearEarthObjects){
                    Iterator<JsonNode> dateIterator = node.elements();
                    while(dateIterator.hasNext()){
                        ObjectNode dataNode = (ObjectNode) dateIterator.next();
                        String id = dataNode.path("id").asText();
                        Asteroid asteroid = Asteroid.builder()
                                .objectId(id)
                                .data(dataNode.toString())
                                .build();

                        //DB can be updated as we already have  asynchronously.
                        asteroidService.createAsteroid(asteroid);

                    }
                }

                asteroidsInRange = asteroidService.findTop10ByFromAndToDate(fromDate, toDate);
            }

            return asteroidsInRange;
        }catch (Exception e){
            logger.error("Error during processing: ", e);
            throw new AsteroidError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", null);
        }
    }
}
