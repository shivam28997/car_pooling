package com.shivam.service.impl;

import com.shivam.dtos.CarOwnerUpdateDTO;
import com.shivam.entity.*;
import com.shivam.repository.CarOwnerRepository;
import com.shivam.repository.JourneyRepository;
import com.shivam.repository.RideRequestRepository;
import com.shivam.service.CarOwnerService;
import com.shivam.util.CityCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {

    private final CarOwnerRepository carOwnerRepository;
    private final JourneyRepository journeyRepository;
    private final RideRequestRepository rideRequestRepository;

    @Autowired
    public CarOwnerServiceImpl(CarOwnerRepository carOwnerRepository, JourneyRepository journeyRepository, RideRequestRepository rideRequestRepository
    ) {
        this.carOwnerRepository = carOwnerRepository;
        this.journeyRepository = journeyRepository;
        this.rideRequestRepository = rideRequestRepository;
    }

    @Override
    public CarOwner registerCarOwner(CarOwner carOwner) {
        CarOwner newCarOwner = new CarOwner();
        newCarOwner.setName(carOwner.getName());
        newCarOwner.setEmail(carOwner.getEmail());
        newCarOwner.setPhoneNumber(carOwner.getPhoneNumber());
        newCarOwner.setVehicleModel(carOwner.getVehicleModel());
        newCarOwner.setLicensePlate(carOwner.getLicensePlate());
        return carOwnerRepository.save(newCarOwner);
    }

    @Override
    public CarOwner updateCarOwner(Long carOwnerId, CarOwnerUpdateDTO updatedDTO) {
        CarOwner existingCarOwner = carOwnerRepository.findById(carOwnerId).orElse(null);

        if (existingCarOwner == null) {
            return null;
        }

        if (updatedDTO.getName() != null) {
            existingCarOwner.setName(updatedDTO.getName());
        }
        if (updatedDTO.getEmail() != null) {
            existingCarOwner.setEmail(updatedDTO.getEmail());
        }
        if (updatedDTO.getPhoneNumber() != null) {
            existingCarOwner.setPhoneNumber(updatedDTO.getPhoneNumber());
        }
        if (updatedDTO.getVehicleModel() != null) {
            existingCarOwner.setVehicleModel(updatedDTO.getVehicleModel());
        }
        if (updatedDTO.getLicensePlate() != null) {
            existingCarOwner.setLicensePlate(updatedDTO.getLicensePlate());
        }

        return carOwnerRepository.save(existingCarOwner);
    }

    @Override
    public Optional<CarOwner> getCarOwnerById(Long carOwnerId) {
        return carOwnerRepository.findById(carOwnerId);
    }

    @Override
    public Journey createJourney(CarOwner carOwner, Journey journey) {
        List<Journey> overlappingJourneys = journeyRepository.findOverlappingJourneys(
                carOwner.getId(), journey.getStartTime(), journey.getEndTime());

        for (Journey j : overlappingJourneys) {
            if (j.getStatus() == JourneyStatus.IN_PROGRESS || j.getStatus() == JourneyStatus.NOT_STARTED) {
                if (j.getEndTime() == null || j.getEndTime().isAfter(journey.getStartTime())) {
                    throw new IllegalStateException("Cannot start a new journey before completing the previous one.");
                }
            }
        }

        journey.setCarOwner(carOwner);

        int distance = calculateDistance(journey.getStartPoint(), journey.getEndPoint());
        int avgSpeed = 40;
        int trafficFactor = 10;

        int estimatedDuration = ((distance * 60) / avgSpeed + trafficFactor);
        journey.setEstimatedDuration(estimatedDuration);
        journey.setDistance((double) distance);

        return journeyRepository.save(journey);
    }

    public static int calculateDistance(String city1, String city2) {
        CityCoordinates.Coordinates coord1 = CityCoordinates.getCoordinates(city1);
        CityCoordinates.Coordinates coord2 = CityCoordinates.getCoordinates(city2);

        if (coord1 == null || coord2 == null) {
            throw new IllegalArgumentException("One or both cities not found in the map.");
        }

        double xDistance = coord2.x() - coord1.x();
        double yDistance = coord2.y() - coord1.y();

        return (int) sqrt(pow(xDistance, 2) + pow(yDistance, 2));
    }

    @Override
    public Journey updateJourney(Long carOwnerId, Long journeyId, Journey updatedJourney) {
        Optional<Journey> existingJourneyOpt = journeyRepository.findById(journeyId);

        if (existingJourneyOpt.isPresent()) {
            Journey existingJourney = existingJourneyOpt.get();

            if (!existingJourney.getCarOwner().getId().equals(carOwnerId)) {
                return null;
            }

            if (updatedJourney.getStartPoint() != null) {
                existingJourney.setStartPoint(updatedJourney.getStartPoint());
            }
            if (updatedJourney.getEndPoint() != null) {
                existingJourney.setEndPoint(updatedJourney.getEndPoint());
            }
            if (updatedJourney.getAvailableSeats() != null) {
                existingJourney.setAvailableSeats(updatedJourney.getAvailableSeats());
            }
            if (updatedJourney.getStatus() != null) {
                existingJourney.setStatus(updatedJourney.getStatus());
            }

            return journeyRepository.save(existingJourney);
        }

        return null;
    }




    @Override
    public Optional<Journey> getJourneyById(Long journeyId) {
        return journeyRepository.findById(journeyId);
    }

    @Override
    public Journey getJourneyByCarOwnerId(Long CarOwnerId) {
        return journeyRepository.findByCarOwnerIdAndStatus(CarOwnerId, JourneyStatus.IN_PROGRESS);
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
        Optional<Journey> journeyOpt = journeyRepository.findById(journeyId);
        if (journeyOpt.isPresent()) {
            Journey journey = journeyOpt.get();
            journey.setStatus(status);
            return journeyRepository.save(journey);
        }
        return null;
    }

    @Override
    public Journey updateAvailableSeats(Long journeyId, Integer availableSeats) {
        Optional<Journey> journeyOpt = journeyRepository.findById(journeyId);
        if (journeyOpt.isPresent()) {
            Journey journey = journeyOpt.get();
            journey.setAvailableSeats(availableSeats);
            return journeyRepository.save(journey);
        }
        return null;
    }

    @Override
    public void respondToRideRequest(Long rideRequestId, RideRequestStatus requestStatus) {
        Optional<RideRequest> optionalRideRequest = rideRequestRepository.findById(rideRequestId);
        if (optionalRideRequest.isPresent()) {
            RideRequest rideRequest = optionalRideRequest.get();

            switch (requestStatus) {
                case ACCEPTED:
                    rideRequest.setStatus(RideRequestStatus.ACCEPTED);
                    Journey journey = rideRequest.getJourney();
                    int newAvailableSeats = journey.getAvailableSeats() - 1;
                    if (newAvailableSeats >= 0) {
                        journey.setAvailableSeats(newAvailableSeats);
                        journeyRepository.save(journey);
                    } else {
                        throw new RuntimeException("No available seats left for the journey.");
                    }
                    break;

                case REJECTED:
                    rideRequest.setStatus(RideRequestStatus.REJECTED);
                    break;

                case CANCELED:
                    rideRequest.setStatus(RideRequestStatus.CANCELED);
                    break;

                case PENDING:
                    rideRequest.setStatus(RideRequestStatus.PENDING);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid ride request status.");
            }

            rideRequestRepository.save(rideRequest);
        } else {
            throw new RuntimeException("Ride request not found.");
        }
    }

}

