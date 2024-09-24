package com.example.dust.controller;

import com.example.dust.dto.DoctorDTO;
import com.example.dust.services.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;

    public DoctorController(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAll();
    }

    @PostMapping("/create")
    public DoctorDTO createDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.create(doctorDTO);
    }

    @GetMapping("/{id}")
    public DoctorDTO getDoctorById(@PathVariable Integer id) {
        return doctorService.getById(id);
    }

    @PutMapping("/update/{id}")
    public DoctorDTO updateDoctor(@PathVariable Integer id, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO existingDoctor = doctorService.getById(id);
        if (existingDoctor != null) {
            return doctorService.update(doctorDTO);
        } else {
            throw new RuntimeException("Doctor not found");
        }
    }

    @GetMapping("/search/speciality")
    public List<DoctorDTO> findDoctorsBySpeciality(@RequestParam String speciality) {
        return doctorService.findBySpeciality(speciality);
    }

    @GetMapping("/search")
    public List<DoctorDTO> findDoctorsByFirstnameAndLastname(@RequestParam String firstname, @RequestParam String lastname) {
        return doctorService.findByFirstnameAndLastname(firstname, lastname);
    }

}
