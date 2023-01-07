package de.htwg.cadgymservice.controller;

import com.google.cloud.storage.Blob;
import de.htwg.cadgymservice.service.GymBucketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bucket")
public class GymBucketController {
    private final GymBucketServiceImpl gymBucketService;

    public GymBucketController(@Autowired GymBucketServiceImpl gymBucketService) {
        this.gymBucketService = gymBucketService;
    }

    @PostMapping("/{firebaseId}")
    public Blob insertGym(@PathVariable String firebaseId, @RequestBody byte[] data) {
        return gymBucketService.saveLogo(firebaseId, data);
    }

    @GetMapping("/{firebaseId}")
    public Blob getGymById(@PathVariable String firebaseId) {
        return gymBucketService.getLogo(firebaseId);
    }

    @DeleteMapping("/{firebaseId}")
    public boolean deleteGym(@PathVariable String firebaseId) {
        return gymBucketService.deleteLogo(firebaseId);
    }

    @PatchMapping("/{tenantId}")
    public Blob updateGym(@PathVariable String tenantId, @RequestBody byte[] data) {
        return gymBucketService.updateLogo(tenantId, data);
    }
}
