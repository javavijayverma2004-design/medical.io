package com.Medical.Medical.Entity.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medical.Medical.Entity.MedicalFile;
import com.Medical.Medical.Entity.Repository.MedicalFileRepository;

@Service
public class MedicalFileService {

	@Autowired
	private MedicalFileRepository repo;

	public MedicalFile save(MedicalFile file) {
		return repo.save(file);
	}

	public List<MedicalFile> getAll() {
		return repo.findAll();
	}

	public void delete(int id) {
		repo.deleteById(id);
	}
}