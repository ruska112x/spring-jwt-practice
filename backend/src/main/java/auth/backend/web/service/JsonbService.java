package auth.backend.web.service;

import auth.backend.web.model.Data;
import auth.backend.web.repository.JsonbRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonbService {
    private final JsonbRepository jsonbRepository;

    public JsonbService(JsonbRepository jsonbRepository) {
        this.jsonbRepository = jsonbRepository;
    }

    public void addData(Data data) {
        jsonbRepository.addData(data);
    }

    public List<Data> getAll() {
        return jsonbRepository.getAll();
    }

    public List<Data> getDataByName(String name) {
        return jsonbRepository.getDataByName(name);
    }

    public void updateData(Data data) {
        jsonbRepository.updateData(data);
    }

    public void deleteDataByName(String name) {
        jsonbRepository.deleteDataByName(name);
    }
}
