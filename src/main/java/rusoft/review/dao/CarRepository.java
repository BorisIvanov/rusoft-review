package rusoft.review.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import rusoft.review.dto.Car;
import rusoft.review.dto.CarEntity;

import java.util.List;

@Repository
public class CarRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CarRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Car car) {
        jdbcTemplate.update("INSERT INTO car(model, prod_date, name) VALUES (:model, :prodDate, :name)",
                new BeanPropertySqlParameterSource(car));
    }

    public void clientRemove(String model) {
        jdbcTemplate.update("UPDATE car SET name = NULL WHERE model = :model",
                new MapSqlParameterSource("model", model));
    }

    public void clientUpdate(Car car) {
        jdbcTemplate.update("UPDATE car SET name = :name WHERE model = :model",
                new BeanPropertySqlParameterSource(car));
    }

    public List<CarEntity> selectByModel(String model) {
        return jdbcTemplate.query("SELECT * FROM car WHERE model = :model",
                new MapSqlParameterSource("model", model),
                new BeanPropertyRowMapper<>(CarEntity.class));
    }
}
