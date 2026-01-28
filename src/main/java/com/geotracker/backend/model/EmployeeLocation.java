package com.geotracker.backend.model;


public class EmployeeLocation {

    private String empId;
    private double lat;
    private double lon;

    public EmployeeLocation() {
    }

    public EmployeeLocation(String empId, double lat, double lon) {
        this.empId = empId;
        this.lat = lat;
        this.lon = lon;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
