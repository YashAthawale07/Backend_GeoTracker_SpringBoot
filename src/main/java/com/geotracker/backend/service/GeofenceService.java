//package com.geotracker.backend.service;
//
//import  com.geotracker.backend.model.Geofence;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GeofenceService {
//
////    public Geofence getOfficeGeofence() {
////        Geofence g = new Geofence();
////        g.setLatitude(21.0077);   // example
////        g.setLongitude(75.5626);
////        g.setRadius(150);         // 100 meters
////        return g;
////    }
//}

package com.geotracker.backend.service;

import com.geotracker.backend.model.Geofence;
import com.geotracker.backend.repositories.GeofenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeofenceService {

    private final GeofenceRepository geofenceRepository;

    public GeofenceService(GeofenceRepository geofenceRepository) {
        this.geofenceRepository = geofenceRepository;
    }

    // Fetch the single office geofence from DB
    public Geofence getOfficeGeofence() {
        List<Geofence> all = geofenceRepository.findAll();
        if (all.isEmpty()) {
            throw new RuntimeException("Office location not set. Please ask admin to configure it.");
        }
        return all.get(0);
    }

    // Admin: set or update the office geofence (upsert — only one ever exists)
    public Geofence setOfficeGeofence(double latitude, double longitude, double radius) {
        List<Geofence> all = geofenceRepository.findAll();

        Geofence geofence;
        if (all.isEmpty()) {
            geofence = new Geofence(); // create new
        } else {
            geofence = all.get(0);    // update existing
        }

        geofence.setLatitude(latitude);
        geofence.setLongitude(longitude);
        geofence.setRadius(radius);

        return geofenceRepository.save(geofence);
    }
}