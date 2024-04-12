package com.example.lures;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuresService {

    private final LuresMapper luresMapper;

    public LuresService(LuresMapper luresMapper) {
        this.luresMapper = luresMapper;
    }

    public List<Lures> findAll() {

        return luresMapper.findAll();
    }

    public List<Lures> findLures(String keyword) {
        return luresMapper.findLures(keyword);
    }

}
