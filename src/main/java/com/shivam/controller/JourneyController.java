package com.shivam.controller;

import com.shivam.entity.Journey;
import com.shivam.entity.JourneyStatus;
import com.shivam.service.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    @Autowired
    private JourneyService journeyService;

    @GetMapping("/{id}")
    public ResponseEntity<Journey> getJourneyById(@PathVariable Long id) {
        Optional<Journey> journey = journeyService.getJourneyById(id);
        return journey.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Journey>> getJourneysByStatus(@PathVariable JourneyStatus status) {
        List<Journey> journeys = journeyService.getJourneysByStatus(status);
        return ResponseEntity.ok(journeys);
    }

    @GetMapping("/carOwner/{carOwnerId}")
    public ResponseEntity<List<Journey>> getJourneysByCarOwner(@PathVariable Long carOwnerId) {
        List<Journey> journeys = journeyService.getJourneysByCarOwner(carOwnerId);
        return ResponseEntity.ok(journeys);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Journey>> getAllAvailableJourneys() {
        List<Journey> journeys = journeyService.getAllAvailableJourneys();
        return ResponseEntity.ok(journeys);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Journey> updateJourneyStatus(@PathVariable Long id, @RequestParam JourneyStatus status) {
        Journey updatedJourney = journeyService.updateJourneyStatus(id, status);
        return ResponseEntity.ok(updatedJourney);
    }

    @PatchMapping("/{id}/seats")
    public ResponseEntity<Journey> updateAvailableSeats(@PathVariable Long id, @RequestParam Integer seats) {
        Journey updatedJourney = journeyService.updateAvailableSeats(id, seats);
        return ResponseEntity.ok(updatedJourney);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Journey>> searchJourneys(@RequestParam String startPoint, @RequestParam String endPoint) {
        List<Journey> journeys = journeyService.searchJourneys(startPoint, endPoint);
        return ResponseEntity.ok(journeys);
    }

    @GetMapping("/{id}/proximity")
    public ResponseEntity<Boolean> isJourneyWithinProximity(@PathVariable Long id, @RequestParam String location, @RequestParam double maxDistance) {
        boolean isWithinProximity = journeyService.isJourneyWithinProximity(id, location, maxDistance);
        return ResponseEntity.ok(isWithinProximity);
    }
}
