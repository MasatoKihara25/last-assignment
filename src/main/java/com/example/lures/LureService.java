package com.example.lures;

import org.springframework.stereotype.Service;

import java.util.List;

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
}
