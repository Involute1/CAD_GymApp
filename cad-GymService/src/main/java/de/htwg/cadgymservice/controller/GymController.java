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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GymController {
    private static final Log LOGGER = LogFactory.getLog(GymController.class);
    private final GymServiceImpl gymService;
    private final GymBucketServiceImpl gymBucketService;

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

    @GetMapping("/{firebaseId}")
    public Gym getGymById(@PathVariable String firebaseId) {
        FirestoreGym firestoreGym = gymService.getGym(firebaseId);
        Blob blob = gymBucketService.getLogo(firebaseId);
        return new Gym(firestoreGym.getId(), firestoreGym.getName(), firestoreGym.getTenantId(), firestoreGym.getDescription(), firestoreGym.getBillingModel(), blob.getContent());
    }

    @DeleteMapping("/{firebaseId}")
    public boolean deleteGym(@PathVariable String firebaseId) {
        gymService.deleteGym(firebaseId);
        gymBucketService.deleteLogo(firebaseId);
        return true;
    }

    @PatchMapping("/{firebaseId}")
    public Gym updateGym(@PathVariable String firebaseId, @RequestBody Gym updatedGym) {
        FirestoreGym firestoreGymToUpdate = gymService.getGym(updatedGym.getFirebaseId());
        FirestoreGym firestoreGym = gymService.updateGym(new FirestoreGym(updatedGym.getFirebaseId(), updatedGym.getName(), updatedGym.getTenantId(), updatedGym.getDescription(), updatedGym.getBillingModel(), firestoreGymToUpdate.getInvoices()));
        Blob blob = gymBucketService.updateLogo(firebaseId, updatedGym.getLogo());
        return new Gym(firestoreGym.getId(), firestoreGym.getName(), firestoreGym.getTenantId(), firestoreGym.getDescription(), updatedGym.getBillingModel(), blob.getContent());
    }

    @GetMapping("/{firebaseId}/invoice")
    public List<Invoice> getGymInvoices(@PathVariable String firebaseId) {
        FirestoreGym firestoreGym = gymService.getGym(firebaseId);
        return firestoreGym.getInvoices().stream().map(fbInvoice -> new Invoice(fbInvoice.getFirestoreId(), fbInvoice.getBillingDate(), fbInvoice.getAmount(), fbInvoice.getDueDate(), firestoreGym.getId())).toList();
    }

    @GetMapping("/")
    public List<Gym> getGyms() {
        List<FirestoreGym> firestoreGyms = gymService.getGyms();
        return firestoreGyms.stream().map(fbGym -> new Gym(fbGym.getId(), fbGym.getName(), fbGym.getTenantId(), fbGym.getDescription(), fbGym.getBillingModel(), null)).toList();
    }

    @PostMapping("/invoices")
    public boolean insertInvoices(@RequestBody List<Invoice> newInvoices) {
        newInvoices.forEach(invoice -> {
            FirestoreGym gym = gymService.getGym(invoice.getGymId());
            List<FirestoreInvoice> invoices = gym.getInvoices();
            invoices.add(new FirestoreInvoice(null, invoice.getBillingDate(), invoice.getAmount(), invoice.getDueDate()));
            gym.setInvoices(invoices);
            gymService.updateGym(gym);
        });
        return true;
    }
}
