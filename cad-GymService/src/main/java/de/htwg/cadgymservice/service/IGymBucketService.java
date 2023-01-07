package de.htwg.cadgymservice.service;

import com.google.cloud.storage.Blob;

public interface IGymBucketService {
    Blob saveLogo(String firebaseId, byte[] data);

    Blob getLogo(String firebaseId);

    Blob updateLogo(String firebaseId, byte[] updatedData);

    boolean deleteLogo(String firebaseId);
}
