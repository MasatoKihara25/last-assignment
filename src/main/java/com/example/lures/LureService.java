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
        Optional<Lure> lure = this.lureMapper.findById(id);
        return lure.orElseThrow(() -> new LureNotFoundException("lure not found"));
    }

    public Lure insert(Integer id, String product, String company, double size, double weight) {
        Lure lure = new Lure(id, product, company, size, weight);
        lureMapper.insert(lure);
        return lure;
    }
}
