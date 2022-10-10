package com.assignment.nasaapi.entityservice;

import com.assignment.nasaapi.constants.Constants;
import com.assignment.nasaapi.model.Asteroid;
import com.assignment.nasaapi.repository.AsteroidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

import static com.assignment.nasaapi.constants.Constants.*;

@Service
public class AsteroidService {

    @Autowired
    private AsteroidRepository asteroidRepository;

    @Autowired
    private EntityManager entityManager;

    public Asteroid createAsteroid(Asteroid asteroid) {
        return asteroidRepository.save(asteroid);
    }

    public List<Asteroid> getAsteroids() {
        return asteroidRepository.findAll();
    }

    public void deleteAsteroid(String objectId) {
        asteroidRepository.deleteById(objectId);
    }

    public List<String> findTop10ByFromAndToDate(String fromDate, String toDate){
        String queryWithPlaceHolder = "SELECT Cast(data as varchar) FROM %s, json_array_elements(%s.data -> 'close_approach_data') AS cad WHERE (cad ->> 'close_approach_date') between '%s' AND '%s'";
        String query = String.format(queryWithPlaceHolder, TBL_SPACE_OBJECTS, TBL_SPACE_OBJECTS, fromDate, toDate);
        return entityManager.createNativeQuery(query).getResultList();
    }
}
