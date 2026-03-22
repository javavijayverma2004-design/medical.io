package com.Medical.Medical.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medical.Medical.Entity.MedicalFile;

@Repository
public interface MedicalFileRepository extends JpaRepository<MedicalFile, Integer> {
}