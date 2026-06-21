package com.sendhrresume.repo;

import com.sendhrresume.entity.FileSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepo extends JpaRepository<FileSource, Integer> {

    Optional<FileSource> findByFileName(String fileName);
}
