package com.codegym.controller;

import com.codegym.model.Smartphone;
import com.codegym.service.ISmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api/smartphones")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class SmartPhoneController {

    @Autowired
    private ISmartphoneService smartphoneService;

    @GetMapping
    public ResponseEntity<Iterable<Smartphone>> getAllSmartphones() {
        Iterable<Smartphone> smartphones = smartphoneService.findAll();
        if (!smartphones.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(smartphones, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Smartphone> createSmartphone(@RequestBody Smartphone smartphone) {
        return new ResponseEntity<>(smartphoneService.save(smartphone), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Smartphone> deleteSmartphone(@PathVariable Long id) {
        Optional<Smartphone> smartphoneOptional = smartphoneService.findById(id);
        if (!smartphoneOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartphoneService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Smartphone> updateSmartPhone(@RequestBody Smartphone smartphone, @PathVariable Long id) {
        Optional<Smartphone> optionalSmartphone = smartphoneService.findById(id);
        if (!optionalSmartphone.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartphone.setId(id);
        return new ResponseEntity<>(smartphoneService.save(smartphone), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Iterable<Smartphone>> searchSmartPhone(@RequestParam("search") Optional<String> search) {
        Iterable<Smartphone> smartphones;
        if (search.isPresent()) {
            smartphones = smartphoneService.findByModelContaining(search.get());
            if (!smartphones.iterator().hasNext()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            smartphones = smartphoneService.findAll();
        }
        return new ResponseEntity<>(smartphones, HttpStatus.OK);
    }
}
