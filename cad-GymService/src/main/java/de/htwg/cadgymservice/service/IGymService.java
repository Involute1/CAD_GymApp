package de.htwg.cadgymservice.service;

import de.htwg.cadgymservice.model.FirestoreGym;

import java.util.List;

public interface IGymService {
    FirestoreGym saveGym(FirestoreGym gym);

    FirestoreGym getGymByTenantId(String tenantId);

    FirestoreGym updateGym(FirestoreGym updatedGym);

    boolean deleteGym(String firebaseId);

    List<FirestoreGym> getGyms();
}
