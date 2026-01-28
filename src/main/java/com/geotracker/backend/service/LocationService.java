package com.geotracker.backend.service;

import com.geotracker.backend.model.Geofence;
import com.geotracker.backend.util.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    public boolean isInside(double empLat, double empLon, Geofence g) {
        double dist = DistanceCalculator.distance(
                empLat, empLon,
                g.getLatitude(), g.getLongitude()
        );
        return dist <= g.getRadius();
    }
}