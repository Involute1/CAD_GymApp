package de.htwg.cadgymservice.service;

import de.htwg.cadgymservice.model.FirebaseGym;

public interface IGymService {
    FirebaseGym saveGym(FirebaseGym gym);

    FirebaseGym getGym(String firebaseId);

    FirebaseGym updateGym(FirebaseGym updatedGym);

    boolean deleteGym(String firebaseId);
}
