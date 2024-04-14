package com.example.lures;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LureController {

    private final LureService lureService;

    public LureController(LureService lureService) {
        this.lureService = lureService;
    }

    @GetMapping("/lures")
    public List<Lure> findByLures(LureSearchRequest lureSearchRequest) {
        List<Lure> lures = lureService.findLure(lureSearchRequest.getContains());
        return lures;
    }
}
