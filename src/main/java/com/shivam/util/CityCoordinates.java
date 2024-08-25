package com.shivam.util;

import java.util.HashMap;
import java.util.Map;

public class CityCoordinates {
    private static final Map<String, Coordinates> cities = new HashMap<>();

    static {
        // New Delhi at (0, 0)
        cities.put("New Delhi", new Coordinates(0, 0));
        cities.put("Mumbai", new Coordinates(-15, 25));
        cities.put("Bangalore", new Coordinates(-10, -15));
        cities.put("Kolkata", new Coordinates(20, 20));
        cities.put("Chennai", new Coordinates(-10, -20));
        cities.put("Hyderabad", new Coordinates(-15, -10));
        cities.put("Ahmedabad", new Coordinates(-5, 10));
        cities.put("Pune", new Coordinates(-15, 20));
        cities.put("Jaipur", new Coordinates(5, 15));
        cities.put("Surat", new Coordinates(-10, 5));
        cities.put("Lucknow", new Coordinates(10, 10));
        cities.put("Kanpur", new Coordinates(15, 10));
        cities.put("Nagpur", new Coordinates(-10, 0));
        cities.put("Indore", new Coordinates(-10, 10));
        cities.put("Vadodara", new Coordinates(-10, 10));
        cities.put("Coimbatore", new Coordinates(-20, -15));
        cities.put("Patna", new Coordinates(15, 25));
        cities.put("Agra", new Coordinates(0, 5));
        cities.put("Meerut", new Coordinates(0, 10));
        cities.put("Ranchi", new Coordinates(20, 15));
        cities.put("Bhopal", new Coordinates(10, 5));
        cities.put("Thane", new Coordinates(-20, 20));
        cities.put("Noida", new Coordinates(0, 10));
        cities.put("Faridabad", new Coordinates(0, 10));
        cities.put("Gurgaon", new Coordinates(0, 10));
        cities.put("Vijayawada", new Coordinates(-15, -15));
        cities.put("Mysore", new Coordinates(-10, -20));
        cities.put("Jabalpur", new Coordinates(15, 0));
        cities.put("Jodhpur", new Coordinates(10, 10));
        cities.put("Udaipur", new Coordinates(15, 5));
        cities.put("Shimla", new Coordinates(-5, 20));
        cities.put("Dehradun", new Coordinates(0, 15));
        cities.put("Haridwar", new Coordinates(0, 20));
        cities.put("Amritsar", new Coordinates(10, 20));
        cities.put("Chandigarh", new Coordinates(10, 10));
        cities.put("Kota", new Coordinates(10, 10));
        cities.put("Siliguri", new Coordinates(20, 30));
        cities.put("Jalandhar", new Coordinates(15, 15));
        cities.put("Bhubaneswar", new Coordinates(20, 10));
        cities.put("Guwahati", new Coordinates(25, 20));
        cities.put("Itanagar", new Coordinates(30, 25));
        cities.put("Aizawl", new Coordinates(30, 20));
        cities.put("Shillong", new Coordinates(25, 15));
        cities.put("Imphal", new Coordinates(30, 15));
        cities.put("Kohima", new Coordinates(30, 10));
        cities.put("Agartala", new Coordinates(25, 15));
        cities.put("Port Blair", new Coordinates(40, -20));
        cities.put("Dibrugarh", new Coordinates(30, 20));
    }

    public static Coordinates getCoordinates(String city) {
        return cities.get(city);
    }

    public static Map<String, Coordinates> getCities() {
        return cities;
    }

    // Define a class for coordinates
        public record Coordinates(int x, int y) {

        @Override
            public String toString() {
                return "Coordinates{" +
                        "x=" + x +
                        ", y=" + y +
                        '}';
            }
        }
}
