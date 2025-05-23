package com.example.kos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kos.models.Kos;
import com.example.kos.repository.KosRepository;

@Service
public class KosService {

    @Autowired
    private KosRepository kosRepository;

    public List<Kos> getAllKos() {
        return kosRepository.findAll();
    }

    public Kos addKos(Kos kos) {
        return kosRepository.save(kos);
    }

    public void deleteKos(Long id) {
        Optional<Kos> kos = kosRepository.findById(id);

        if (kos.isPresent()) {
            kosRepository.delete(kos.get());
        } else {
            throw new RuntimeException("Task not found with id " + id);
        }
    }

    public Optional<Kos> getKosById(Long id) {
        return kosRepository.findById(id);
    }

    public void updateKos(Kos kos) {
        kosRepository.save(kos); // save akan otomatis update jika ID sudah ada
    }

}
