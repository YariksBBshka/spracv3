package com.example.dust.controller;

import com.example.dust.dto.DoctorDTO;
import com.example.dust.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/all")
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

    @PutMapping("/update")
    public DoctorDTO updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.update(doctorDTO);
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
