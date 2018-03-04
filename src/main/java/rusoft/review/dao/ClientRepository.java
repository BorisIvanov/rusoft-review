package rusoft.review.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import rusoft.review.dto.ClientEntity;

import java.util.List;

@Repository
public class ClientRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ClientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ClientEntity> selectByName(String name) {
        return jdbcTemplate.query("SELECT * FROM client WHERE name = :name",
                new MapSqlParameterSource("name", name),
                new BeanPropertyRowMapper<>(ClientEntity.class));
    }

    public void insert(ClientEntity client) {
        jdbcTemplate.update("INSERT INTO client(name, birth_date, model) VALUES (:name, :birthDate, :model)",
                new BeanPropertySqlParameterSource(client));
    }

    public void delete(String name) {
        jdbcTemplate.update("DELETE FROM client WHERE name = :name",
                new MapSqlParameterSource("name", name));
    }
}
