package de.htwg.cadgymservice.controller;

import com.google.cloud.storage.Blob;
import de.htwg.cadgymservice.model.FirestoreGym;
import de.htwg.cadgymservice.model.FirestoreInvoice;
import de.htwg.cadgymservice.model.request.Gym;
import de.htwg.cadgymservice.model.request.Invoice;
import de.htwg.cadgymservice.service.GymBucketServiceImpl;
import de.htwg.cadgymservice.service.GymServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
public class GymController {
    private static final Log LOGGER = LogFactory.getLog(GymController.class);
    private final GymServiceImpl gymService;
    private final GymBucketServiceImpl gymBucketService;
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public GymController(@Autowired GymServiceImpl gymService, @Autowired GymBucketServiceImpl gymBucketService) {
        this.gymService = gymService;
        this.gymBucketService = gymBucketService;
    }

    @PostMapping("/")
    public Gym insertGym(@RequestBody FirestoreGym firestoreGym) {
        FirestoreGym savedFirestoreGym = gymService.saveGym(firestoreGym);
        // Blob blob = gymBucketService.saveLogo(firebaseGym.getFirebaseId(), gym.getLogo());
        return new Gym(savedFirestoreGym.getId(), savedFirestoreGym.getName(), savedFirestoreGym.getTenantId(), savedFirestoreGym.getDescription(), savedFirestoreGym.getBillingModel(), new byte[0]);
    }

    @GetMapping("/{firestoreId}")
    public Gym getGymById(@PathVariable String firestoreId) {
        FirestoreGym firestoreGym = gymService.getGymByTenantId(firestoreId);
        Blob blob = gymBucketService.getLogo(firestoreId);
        byte[] content = blob != null ? blob.getContent() : new byte[0];
        return new Gym(firestoreGym.getId(), firestoreGym.getName(), firestoreGym.getTenantId(), firestoreGym.getDescription(), firestoreGym.getBillingModel(), content);
    }

    @DeleteMapping("/{firestoreId}")
    public boolean deleteGym(@PathVariable String firestoreId) {
        gymService.deleteGym(firestoreId);
        gymBucketService.deleteLogo(firestoreId);
        return true;
    }

    @PatchMapping("/{firestoreId}")
    public Gym updateGym(@PathVariable String firestoreId, @RequestBody Gym updatedGym) {
        FirestoreGym firestoreGymToUpdate = gymService.getGymByTenantId(updatedGym.getFirebaseId());
        FirestoreGym firestoreGym = gymService.updateGym(new FirestoreGym(updatedGym.getFirebaseId(), updatedGym.getName(), updatedGym.getTenantId(), updatedGym.getDescription(), updatedGym.getBillingModel(), firestoreGymToUpdate.getInvoices()));
        Blob blob = gymBucketService.updateLogo(firestoreId, updatedGym.getLogo());
        return new Gym(firestoreGym.getId(), firestoreGym.getName(), firestoreGym.getTenantId(), firestoreGym.getDescription(), updatedGym.getBillingModel(), blob.getContent());
    }

    @PostMapping("/{firestoreId}/logo")
    public Gym addLogo(@PathVariable String firestoreId, @RequestParam("image") MultipartFile file) throws IOException {
        FirestoreGym firestoreGym = gymService.getGymByTenantId(firestoreId);
        Blob blob = gymBucketService.updateLogo(firestoreId, file.getBytes());
        return new Gym(firestoreGym.getId(), firestoreGym.getName(), firestoreGym.getTenantId(), firestoreGym.getDescription(), firestoreGym.getBillingModel(), blob.getContent());
    }

    @GetMapping(path = {"/{firestoreId}/logo"})
    public ResponseEntity<byte[]> getImage(@PathVariable String firestoreId) throws IOException {

        Blob blob = gymBucketService.getLogo(firestoreId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(blob.getContent());
    }

    @GetMapping("/{tenantId}/invoice")
    public List<Invoice> getGymInvoices(@PathVariable String tenantId) {
        FirestoreGym firestoreGym = gymService.getGymByTenantId(tenantId);
        return firestoreGym.getInvoices().stream().map(fbInvoice -> new Invoice(fbInvoice.getFirestoreId(), LocalDateTime.ofEpochSecond(fbInvoice.getBillingDate(), 0, ZONE_OFFSET), fbInvoice.getAmount(), LocalDateTime.ofEpochSecond(fbInvoice.getDueDate(), 0, ZONE_OFFSET), firestoreGym.getId())).toList();
    }

    @GetMapping("/")
    public List<Gym> getGyms() {
        List<FirestoreGym> firestoreGyms = gymService.getGyms();
        return firestoreGyms.stream().map(fbGym -> new Gym(fbGym.getId(), fbGym.getName(), fbGym.getTenantId(), fbGym.getDescription(), fbGym.getBillingModel(), null)).toList();
    }

    @PostMapping("/invoices")
    public boolean insertInvoices(@RequestBody List<Invoice> newInvoices) {
        newInvoices.forEach(invoice -> {
            FirestoreGym gym = gymService.getGymByTenantId(invoice.getGymId());
            List<FirestoreInvoice> invoices = gym.getInvoices();
            invoices.add(new FirestoreInvoice(null, invoice.getBillingDate().toEpochSecond(ZONE_OFFSET), invoice.getAmount(), invoice.getDueDate().toEpochSecond(ZONE_OFFSET)));
            gym.setInvoices(invoices);
            gymService.updateGym(gym);
        });
        return true;
    }
}
