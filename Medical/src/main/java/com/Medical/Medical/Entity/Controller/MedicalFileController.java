package com.Medical.Medical.Entity.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Medical.Medical.Entity.MedicalFile;
import com.Medical.Medical.Entity.Service.MedicalFileService;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class MedicalFileController {

	@Autowired
	private MedicalFileService service;

	// ✅ Normal Upload (URL se)
	@PostMapping
	public MedicalFile upload(@RequestBody MedicalFile file) {
		return service.save(file);
	}

	// ✅ Get All
	@GetMapping
	public List<MedicalFile> getAll() {
		return service.getAll();
	}

	// ✅ Delete
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		service.delete(id);
	}

	// ✅ FILE UPLOAD (IMPORTANT)
	@PostMapping("/upload")
	public MedicalFile uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
			@RequestParam("category") String category) throws Exception {

		// 👉 folder create
		java.io.File dir = new java.io.File("uploads");
		if (!dir.exists()) {
			dir.mkdir();
		}

		// 👉 file name
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

		// 👉 save path
		String path = "uploads/" + fileName;

		// 👉 file save
		java.nio.file.Files.copy(file.getInputStream(), java.nio.file.Paths.get(path),
				java.nio.file.StandardCopyOption.REPLACE_EXISTING);

		// 👉 URL generate
		String fileUrl = "http://localhost:8080/uploads/" + fileName;

		// 👉 DB save
		MedicalFile m = new MedicalFile();
		m.setTitle(title);
		m.setCategory(category);
		m.setFileUrl(fileUrl);

		return service.save(m);
	}
}