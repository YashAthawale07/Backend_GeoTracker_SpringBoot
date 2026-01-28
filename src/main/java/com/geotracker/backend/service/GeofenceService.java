package com.geotracker.backend.service;

import  com.geotracker.backend.model.Geofence;
import org.springframework.stereotype.Service;

@Service
public class GeofenceService {

    public Geofence getOfficeGeofence() {
        Geofence g = new Geofence();
        g.setLatitude(21.0077);   // example
        g.setLongitude(75.5626);
        g.setRadius(150);         // 100 meters
        return g;
    }
}