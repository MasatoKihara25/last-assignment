package com.example.lures;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/lures/{id}")
    public Lure findlure(@PathVariable("id") Integer id) {
        return lureService.findLure(id);
    }

    @PostMapping("/lures")
    public ResponseEntity<LureResponse> insert(@RequestBody LureRequest lureRequest, UriComponentsBuilder uriBuilder) {
        Lure lure = lureService.insert(lureRequest.getProduct(), lureRequest.getCompany(), lureRequest.getSize(), lureRequest.getWeight());
        URI location = uriBuilder.path("/lures/{id}").buildAndExpand(lure.getId()).toUri();
        LureResponse body = new LureResponse("created");
        return ResponseEntity.created(location).body(body);
    }
}
