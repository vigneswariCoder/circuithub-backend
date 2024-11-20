package com.ecom.zestcart.repository;

import com.ecom.zestcart.model.FileMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<FileMaster, String> {
    Optional<FileMaster> findByFileCategory(String fileId);
}
