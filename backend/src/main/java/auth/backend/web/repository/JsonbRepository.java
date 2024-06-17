package auth.backend.web.repository;

import auth.backend.web.model.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JsonbRepository {
    private final JdbcOperations jdbcOperations;
    private final ObjectMapper objectMapper;

    public JsonbRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.objectMapper = new ObjectMapper();
    }

    public void addData(Data data) {
        try {
            String sql = "insert into data_jsonb (data) values (? ::jsonb)";
            Map<String, Object> raw = objectMapper.convertValue(data, new TypeReference<>() {
            });
            String json = objectMapper.writeValueAsString(raw);
            jdbcOperations.update(sql, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Data> getAll() {
        String sql = "select * from data_jsonb";
        List<Data> dataList = new ArrayList<>();
        jdbcOperations.query(sql, resultSet -> {
            try {
                String json = resultSet.getString("data");
                Map<String, Object> map = objectMapper.readValue(json, new TypeReference<>() {
                });
                Data data = objectMapper.convertValue(map, Data.class);
                dataList.add(data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return dataList;
    }

    public List<Data> getDataByName(String name) {
        String sql = "select * from data_jsonb where data->>'name' = ?";
        List<Data> dataList = new ArrayList<>();
        jdbcOperations.query(sql, resultSet -> {
            try {
                Data data = new ObjectMapper().readValue(resultSet.getString("data"), Data.class);
                dataList.add(data);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return dataList;
    }

    public void updateData(Data data) {
        try {
            String sql = "update data_jsonb set data = ? ::jsonb where data->>'name' = ?";
            Map<String, Object> raw = objectMapper.convertValue(data, new TypeReference<>() {
            });
            String json = objectMapper.writeValueAsString(raw);
            jdbcOperations.update(sql, json, data.getName());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDataByName(String name) {
        String sql = "delete from data_jsonb where data->>'name' = ?";
        jdbcOperations.update(sql, name);
    }
}
