package com.example.lures;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuresService {

    private final LuresMapper luresMapper;

    public LuresService(LuresMapper luresMapper) {
        this.luresMapper = luresMapper;
    }

    public List<Lures> findLures(String keyword) {
        if (keyword != null) {
            return luresMapper.findLures(keyword);
        } else {
            return luresMapper.findAll();
        }
    }
}
