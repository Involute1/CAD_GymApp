package de.htwg.cadgymservice.controller;

import com.google.cloud.storage.Blob;
import de.htwg.cadgymservice.model.FirebaseGym;
import de.htwg.cadgymservice.model.FirebaseInvoice;
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
    public Gym insertGym(@RequestBody Gym gym) {
        FirebaseGym firebaseGym = gymService.saveGym(new FirebaseGym());
        Blob blob = gymBucketService.saveLogo(firebaseGym.getFirebaseId(), gym.getLogo());
        return new Gym(firebaseGym.getFirebaseId(), gym.getName(), gym.getTenantId(), gym.getDescription(), firebaseGym.getBillingModel(), blob.getContent());
    }

    @GetMapping("/{firebaseId}")
    public Gym getGymById(@PathVariable String firebaseId) {
        FirebaseGym firebaseGym = gymService.getGym(firebaseId);
        Blob blob = gymBucketService.getLogo(firebaseId);
        return new Gym(firebaseGym.getFirebaseId(), firebaseGym.getName(), firebaseGym.getTenantId(), firebaseGym.getDescription(), firebaseGym.getBillingModel(), blob.getContent());
    }

    @DeleteMapping("/{firebaseId}")
    public boolean deleteGym(@PathVariable String firebaseId) {
        gymService.deleteGym(firebaseId);
        gymBucketService.deleteLogo(firebaseId);
        return true;
    }

    @PatchMapping("/{firebaseId}")
    public Gym updateGym(@PathVariable String firebaseId, @RequestBody Gym updatedGym) {
        FirebaseGym firebaseGymToUpdate = gymService.getGym(updatedGym.getFirebaseId());
        FirebaseGym firebaseGym = gymService.updateGym(new FirebaseGym(updatedGym.getFirebaseId(), updatedGym.getName(), updatedGym.getTenantId(), updatedGym.getDescription(), updatedGym.getBillingModel(), firebaseGymToUpdate.getInvoices()));
        Blob blob = gymBucketService.updateLogo(firebaseId, updatedGym.getLogo());
        return new Gym(firebaseGym.getFirebaseId(), firebaseGym.getName(), firebaseGym.getTenantId(), firebaseGym.getDescription(), updatedGym.getBillingModel(), blob.getContent());
    }

    @GetMapping("/{firebaseId}/invoice")
    public List<Invoice> getGymInvoices(@PathVariable String firebaseId) {
        FirebaseGym firebaseGym = gymService.getGym(firebaseId);
        return firebaseGym.getInvoices().stream().map(fbInvoice -> new Invoice(fbInvoice.getFirebaseId(), fbInvoice.getBillingDate(), fbInvoice.getAmount(), fbInvoice.getDueDate(), firebaseGym.getFirebaseId())).toList();
    }

    @GetMapping("/")
    public List<Gym> getGyms() {
        List<FirebaseGym> firebaseGyms = gymService.getGyms();
        return firebaseGyms.stream().map(fbGym -> new Gym(fbGym.getFirebaseId(), fbGym.getName(), fbGym.getTenantId(), fbGym.getDescription(), fbGym.getBillingModel(), null)).toList();
    }

    @PostMapping("/invoices")
    public boolean insertInvoices(@RequestBody List<Invoice> newInvoices) {
        newInvoices.forEach(invoice -> {
            FirebaseGym gym = gymService.getGym(invoice.getGymId());
            List<FirebaseInvoice> invoices = gym.getInvoices();
            invoices.add(new FirebaseInvoice(null, invoice.getBillingDate(), invoice.getAmount(), invoice.getDueDate()));
            gym.setInvoices(invoices);
            gymService.updateGym(gym);
        });
        return true;
    }
}
