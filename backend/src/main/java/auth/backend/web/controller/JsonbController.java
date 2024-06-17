package auth.backend.web.controller;

import auth.backend.web.model.Data;
import auth.backend.web.service.JsonbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jsonb")
public class JsonbController {
    private final JsonbService jsonbService;

    public JsonbController(JsonbService jsonbService) {
        this.jsonbService = jsonbService;
    }

    @PostMapping
    public ResponseEntity<?> addData(@RequestBody Data data) {
        jsonbService.addData(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Data> getAll() {
        return jsonbService.getAll();
    }

    @GetMapping("/{name}")
    public List<Data> getDataByName(@PathVariable String name) {
        return jsonbService.getDataByName(name);
    }

    @PutMapping
    public void updateData(@RequestBody Data data) {
        jsonbService.updateData(data);
    }

    @DeleteMapping("/{name}")
    public void deleteDataByName(@PathVariable String name) {
        jsonbService.deleteDataByName(name);
    }
}
