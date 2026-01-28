package com.geotracker.backend.repositories;

import com.geotracker.backend.model.Geofence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GeofenceRepository extends MongoRepository<Geofence, String> {}

