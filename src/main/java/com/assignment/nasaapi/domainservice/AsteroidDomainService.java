package com.assignment.nasaapi.domainservice;

import com.assignment.nasaapi.model.Asteroid;

import java.util.List;

public interface AsteroidDomainService {

    List<String> getAsteroidsFromAndToDate(String fromDate, String toDate);

}
