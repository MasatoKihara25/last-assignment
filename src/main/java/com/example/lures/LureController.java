package com.example.lures;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<LureResponse> insert(@RequestBody @Validated LureRequest lureRequest, UriComponentsBuilder uriBuilder) {
        Lure lure = lureService.insert(lureRequest.getProduct(), lureRequest.getCompany(), lureRequest.getSize(), lureRequest.getWeight());
        URI location = uriBuilder.path("/lures/{id}").buildAndExpand(lure.getId()).toUri();
        LureResponse body = new LureResponse("created");
        return ResponseEntity.created(location).body(body);
    }

    @PatchMapping("/lures/{id}")
    public ResponseEntity<LureResponse> update(@PathVariable("id") Integer id, @RequestBody @Validated LureRequest lureRequest) {
        Lure lure = lureService.update(id, lureRequest.getProduct(), lureRequest.getCompany(), lureRequest.getSize(), lureRequest.getWeight());
        LureResponse body = new LureResponse("Lure updated");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/lures/{id}")
    public ResponseEntity<LureResponse> delete(@PathVariable("id") Integer id) {
        Lure lure = lureService.delete(id);
        LureResponse body = new LureResponse("music delete");
        return ResponseEntity.ok(body);
    }
}
