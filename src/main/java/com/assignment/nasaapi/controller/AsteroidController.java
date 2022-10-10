package com.assignment.nasaapi.controller;

import com.assignment.nasaapi.domainservice.AsteroidDomainService;
import com.assignment.nasaapi.model.Asteroid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/asteroids")
public class AsteroidController {

    @Autowired
    private AsteroidDomainService asteroidDomainService;

    @RequestMapping(method=RequestMethod.GET)
    public List<String> getAsteroidsFromAndToDate(@RequestParam String fromDate, @RequestParam String toDate) {
        return asteroidDomainService.getAsteroidsFromAndToDate(fromDate, toDate);
    }
}
