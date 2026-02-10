package com.app.webdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.app.webdemo.model.RegistrationForm;
import com.app.webdemo.model.RegistrationEntity;
import com.app.webdemo.repository.RegistrationRepository;

import jakarta.validation.Valid;

@Controller
public class WebdemoController {

    @Autowired
    private RegistrationRepository registrationRepository;

    // =========================
    // หน้า Home
    // =========================
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // =========================
    // หน้า Registration
    // =========================
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    // =========================
    // POST : Register (บันทึกจริง)
    // =========================
    @PostMapping("/register")
    public String handleRegistration(
            @Valid RegistrationForm form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        // แปลง Form → Entity
        RegistrationEntity entity = new RegistrationEntity();
        entity.setFirstName(form.getFirstName());
        entity.setLastName(form.getLastName());
        entity.setCountry(form.getCountry());
        entity.setDob(form.getDob());
        entity.setEmail(form.getEmail());

        // ✅ save ลง DB
        registrationRepository.save(entity);

        model.addAttribute("user", entity);
        return "success";
    }

    // =========================
    // PUT : update ข้อมูลจริง
    // =========================
    @PutMapping("/register/{id}")
    @ResponseBody
    public String updateRegistration(
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
                    return "UPDATE OK : id = " + id;
                })
                .orElse("UPDATE FAIL : id not found");
    }

    // =========================
    // DELETE : ลบจริงจาก DB
    // =========================
    @DeleteMapping("/register/{id}")
    @ResponseBody
    public String deleteRegistration(@PathVariable Long id) {

        if (!registrationRepository.existsById(id)) {
            return "DELETE FAIL : id not found";
        }

        registrationRepository.deleteById(id);
        return "DELETE OK : id = " + id;
    }

    // =========================
    // GET : ดูข้อมูลทั้งหมด (เช็กผล)
    // =========================
    @GetMapping("/register")
    @ResponseBody
    public Iterable<RegistrationEntity> getAll() {
        return registrationRepository.findAll();
    }
}
