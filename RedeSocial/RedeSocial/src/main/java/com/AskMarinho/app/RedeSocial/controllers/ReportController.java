package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Report;
import com.AskMarinho.app.RedeSocial.repositories.ReportRepository;

@RestController
@RequestMapping("/denuncias")
public class ReportController {
	@Autowired
	private ReportRepository repositoryR;

	@GetMapping("/todas")
	public ResponseEntity<List<Report>> getAll() {
		return ResponseEntity.status(200).body(repositoryR.findAll());
	}

	@GetMapping("/id/{idReport}")
	public ResponseEntity<Report> getById(@PathVariable(value = "idReport") Long idReport) {
		Optional<Report> existingReport = repositoryR.findById(idReport);

		if (existingReport.isPresent()) {
			return ResponseEntity.status(200).body(existingReport.get());
		}
		return ResponseEntity.status(404).build();
	}
}
