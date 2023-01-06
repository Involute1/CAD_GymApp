package de.htwg.cadgymservice.controller;

import de.htwg.cadgymservice.model.Gym;
import de.htwg.cadgymservice.service.GymBucketServiceImpl;
import de.htwg.cadgymservice.service.GymServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GymController {

    private final GymServiceImpl gymService;
    private final GymBucketServiceImpl gymBucketService;

    public GymController(@Autowired GymServiceImpl gymService, @Autowired GymBucketServiceImpl gymBucketService) {
        this.gymService = gymService;
        this.gymBucketService = gymBucketService;
    }

    @PostMapping("/")
    public void insertGym(@RequestBody Gym gym) {

    }

    @GetMapping("/{gymId}")
    public Gym getGymById(@PathVariable String gymId) {
        return null;
    }

    @GetMapping("/all")
    public List<Gym> getAll() {
        return null;
    }

    @DeleteMapping("/{gymId}")
    public void deleteGym(@PathVariable String gymId) {

    }

    @PatchMapping("/{gymId}")
    public void updateGym(@PathVariable String gymId) {

    }

}
