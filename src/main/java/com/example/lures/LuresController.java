package com.example.lures;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LuresController {

    private final LuresService luresService;

    public LuresController(LuresService luresService) {
        this.luresService = luresService;
    }

    @GetMapping("/lures/all")
    public List<Lures> findAll() {
        return luresService.findAll();
    }

    @GetMapping("/lures")
    public List<Lures> findByLures(LuresSearchRequest luresSearchRequest) {
        List<Lures> lures = luresService.findLures(luresSearchRequest.getContains());
        return lures;
    }

}
