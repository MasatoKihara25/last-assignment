package com.example.lures;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LureService {

    private final LureMapper lureMapper;

    public LureService(LureMapper lureMapper) {
        this.lureMapper = lureMapper;
    }

    public List<Lure> findLure(String keyword) {
        if (keyword != null) {
            return lureMapper.findLures(keyword);
        } else {
            return lureMapper.findAll();
        }
    }

    public Lure findLure(Integer id) {
        Optional<Lure> lure = lureMapper.findById(id);
        if (lure.isPresent()) {
            return lure.get();
        } else {
            throw new LureNotFoundException("Lure not found");
        }
    }
}
