package com.assignment.nasaapi.repository;

import com.assignment.nasaapi.model.Asteroid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsteroidRepository extends JpaRepository<Asteroid, String> {
}
