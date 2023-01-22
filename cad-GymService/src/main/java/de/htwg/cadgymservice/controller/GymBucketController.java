package de.htwg.cadgymservice.controller;

import com.google.cloud.storage.Blob;
import de.htwg.cadgymservice.service.GymBucketServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bucket")
public class GymBucketController {
    private static final Log LOGGER = LogFactory.getLog(GymBucketController.class);
    private final GymBucketServiceImpl gymBucketService;

    public GymBucketController(@Autowired GymBucketServiceImpl gymBucketService) {
        this.gymBucketService = gymBucketService;
    }

    @PostMapping("/{firebaseId}")
    public Blob insertGymLogo(@PathVariable String firebaseId, @RequestBody byte[] data) {
        return gymBucketService.saveLogo(firebaseId, data);
    }

    @GetMapping("/{firebaseId}")
    public Blob getGymLogoById(@PathVariable String firebaseId) {
        return gymBucketService.getLogo(firebaseId);
    }

    @DeleteMapping("/{firebaseId}")
    public boolean deleteGymLogo(@PathVariable String firebaseId) {
        return gymBucketService.deleteLogo(firebaseId);
    }

    @PatchMapping("/{tenantId}")
    public Blob updateGymLogo(@PathVariable String tenantId, @RequestBody byte[] data) {
        return gymBucketService.updateLogo(tenantId, data);
    }
}
