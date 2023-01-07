package de.htwg.cadgymservice.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GymBucketServiceImpl implements IGymBucketService {
    private static final Log LOGGER = LogFactory.getLog(GymBucketServiceImpl.class);
    @Value("${spring.cloud.gcp.project-id}")
    private String googleProjectId;
    @Value("${spring.cloud.gcp.bucket.name}")
    private String bucketId;
    private Storage storage;
    private Bucket bucket;

    @PostConstruct
    public void init() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("tf_service_account_key.json");
        assert serviceAccount != null;
        this.storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId(googleProjectId)
                .build().getService();
        this.bucket = storage.get(bucketId);
    }

    @Override
    public Blob saveLogo(String firebaseId, byte[] data) {
        this.bucket.reload();
        return this.bucket.create(firebaseId, data, Bucket.BlobTargetOption.doesNotExist());
    }

    @Override
    public Blob getLogo(String firebaseId) {
        this.bucket.reload();
        return this.bucket.get(firebaseId);
    }

    @Override
    public Blob updateLogo(String firebaseId, byte[] updatedData) {
        this.bucket.reload();
        Blob oldBlob = this.bucket.get(firebaseId);
        oldBlob.delete();
        return saveLogo(firebaseId, updatedData);
    }

    @Override
    public boolean deleteLogo(String firebaseId) {
        this.bucket.reload();
        Blob oldBlob = this.bucket.get(firebaseId);
        return oldBlob.delete();
    }
}
