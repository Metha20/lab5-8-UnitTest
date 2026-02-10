package com.app.webdemo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.app.webdemo.model.RegistrationEntity;
import com.app.webdemo.model.RegistrationForm;
import com.app.webdemo.repository.RegistrationRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegistrationApiController {

    @Autowired
    private RegistrationRepository registrationRepository;

    // =========================
    // POST : create (Swagger ใช้ได้)
    // =========================
    @PostMapping
    public RegistrationEntity create(@RequestBody @Valid RegistrationForm form) {

        RegistrationEntity entity = new RegistrationEntity();
        entity.setFirstName(form.getFirstName());
        entity.setLastName(form.getLastName());
        entity.setCountry(form.getCountry());
        entity.setDob(form.getDob());
        entity.setEmail(form.getEmail());

        return registrationRepository.save(entity);
    }

    // =========================
    // GET : list ทั้งหมด
    // =========================
    @GetMapping
    public Iterable<RegistrationEntity> getAll() {
        return registrationRepository.findAll();
    }

    // =========================
    // PUT : update จริง
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody RegistrationForm form) {

        return registrationRepository.findById(id)
                .map(entity -> {
                    entity.setFirstName(form.getFirstName());
                    entity.setLastName(form.getLastName());
                    entity.setCountry(form.getCountry());
                    entity.setDob(form.getDob());
                    entity.setEmail(form.getEmail());
                    registrationRepository.save(entity);
                    return ResponseEntity.ok(entity);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // DELETE : ลบจริง
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!registrationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        registrationRepository.deleteById(id);
        return ResponseEntity.ok("DELETE OK id=" + id);
    }
}
