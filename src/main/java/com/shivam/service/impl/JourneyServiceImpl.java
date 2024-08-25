package com.shivam.service.impl;

import com.shivam.entity.Journey;
import com.shivam.entity.JourneyStatus;
import com.shivam.repository.JourneyRepository;
import com.shivam.service.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyServiceImpl implements JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    @Override
    public Journey createJourney(Journey journey) {
        return journeyRepository.save(journey);
    }

    @Override
    public Journey updateJourney(Long journeyId, Journey updatedJourney) {
        if (journeyRepository.existsById(journeyId)) {
            updatedJourney.setId(journeyId);
            return journeyRepository.save(updatedJourney);
        }
        throw new RuntimeException("Journey not found with ID: " + journeyId);
    }

    @Override
    public Optional<Journey> getJourneyById(Long journeyId) {
        return journeyRepository.findById(journeyId);
    }

    @Override
    public List<Journey> getJourneysByStatus(JourneyStatus status) {
        return journeyRepository.findByStatus(status);
    }

    @Override
    public List<Journey> getJourneysByCarOwner(Long carOwnerId) {
        return journeyRepository.findAllByCarOwnerId(carOwnerId);
    }

    @Override
    public List<Journey> getAllAvailableJourneys() {
        return journeyRepository.findByStatus(JourneyStatus.NOT_STARTED);
    }

    @Override
    public Journey updateJourneyStatus(Long journeyId, JourneyStatus status) {
        Journey journey = journeyRepository.findById(journeyId)
                .orElseThrow(() -> new RuntimeException("Journey not found with ID: " + journeyId));
        journey.setStatus(status);
        return journeyRepository.save(journey);
    }

    @Override
    public Journey updateAvailableSeats(Long journeyId, Integer availableSeats) {
        Journey journey = journeyRepository.findById(journeyId)
                .orElseThrow(() -> new RuntimeException("Journey not found with ID: " + journeyId));
        journey.setAvailableSeats(availableSeats);
        return journeyRepository.save(journey);
    }

    @Override
    public List<Journey> searchJourneys(String startPoint, String endPoint) {
        return journeyRepository.findByStartPointAndEndPoint(startPoint, endPoint);
    }

    @Override
    public boolean isJourneyWithinProximity(Long journeyId, String location, double maxDistance) {
        Journey journey = journeyRepository.findById(journeyId)
                .orElseThrow(() -> new RuntimeException("Journey not found with ID: " + journeyId));

        // Example implementation for proximity check
        // Assuming location is in format "lat,long" and journey start/end points are in a similar format
        String[] journeyStart = journey.getStartPoint().split(",");
        String[] journeyEnd = journey.getEndPoint().split(",");
        String[] loc = location.split(",");

        double journeyStartLat = Double.parseDouble(journeyStart[0]);
        double journeyStartLong = Double.parseDouble(journeyStart[1]);
        double journeyEndLat = Double.parseDouble(journeyEnd[0]);
        double journeyEndLong = Double.parseDouble(journeyEnd[1]);
        double locationLat = Double.parseDouble(loc[0]);
        double locationLong = Double.parseDouble(loc[1]);

        double distanceToStart = calculateDistance(locationLat, locationLong, journeyStartLat, journeyStartLong);
        double distanceToEnd = calculateDistance(locationLat, locationLong, journeyEndLat, journeyEndLong);

        return distanceToStart <= maxDistance || distanceToEnd <= maxDistance;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }
}
