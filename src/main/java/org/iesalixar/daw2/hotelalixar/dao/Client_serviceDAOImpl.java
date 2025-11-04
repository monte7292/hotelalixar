package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Client_service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class Client_serviceDAOImpl implements Client_serviceDAO{

    private static final Logger logger = LoggerFactory.getLogger(Client_serviceDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public Client_serviceDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Client_service> listAllClient_Services() throws SQLException {
        logger.info("Listing all client_services relations from the database.");
        String sql = """
            SELECT cs.client_id, cs.service_id, cs.service_date, 
                   clientes.full_name AS client_name,
                   services.service_name AS service_name
            FROM client_services cs
            JOIN clients clientes ON cs.client_id = clientes.client_id
            JOIN services services ON cs.service_id = services.service_id
        """;

        List<Client_service> client_services = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client_service.class));
        logger.info("Retrieved {} client_services from the database.", client_services.size());
        return client_services;
    }

    @Override
    public void insertClient_services(Client_service client_service) throws SQLException {
        logger.info("Inserting client_service relation: client_id={}, service_id={}, service_date={}",
                client_service.getClient_id(),
                client_service.getService_id(),
                client_service.getService_date());
        String sql = "INSERT INTO client_services (client_id, service_id, service_date) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(
                sql,
                client_service.getClient_id(),
                client_service.getService_id(),
                client_service.getService_date());
        logger.info("Inserted client_service relation. Rows affected: {}", rowsAffected);
    }

    @Override
    public void updateClient_services(Client_service client_service) throws SQLException {
        logger.info("Updating client_service relation: client_id={}, service_id={}",
                client_service.getClient_id(),
                client_service.getService_id());
        String sql = "UPDATE client_services SET service_date = ? WHERE client_id = ? AND service_id = ?";
        int rowsAffected = jdbcTemplate.update(
                sql,
                client_service.getService_date(),
                client_service.getClient_id(),
                client_service.getService_id());
        logger.info("Updated client_service relation. Rows affected: {}", rowsAffected);
    }

    @Override
    public void deleteClient_services(Long client_id, Long service_id) throws SQLException {
        logger.info("Deleting client_service relation: client_id={}, service_id={}", client_id, service_id);
        String sql = "DELETE FROM client_services WHERE client_id = ? AND service_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, client_id, service_id);
        logger.info("Deleted client_service relation. Rows affected: {}", rowsAffected);
    }

    @Override
    public Client_service getClient_serviceById(Long client_id, Long service_id) throws SQLException {
        logger.info("Retrieving client_service relation by client_id={} and service_id={}", client_id, service_id);
        String sql = "SELECT * FROM client_services WHERE client_id = ? AND service_id = ?";
        try {
            Client_service client_service = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Client_service.class), client_id, service_id);
            logger.info("Client_service retrieved: client_id={}, service_id={}, service_date={}", client_service.getClient_id(), client_service.getService_id(), client_service.getService_date());
            return client_service;
        } catch (Exception e) {
            logger.warn("No client_service found for client_id={} and service_id={}", client_id, service_id);
            return null;
        }
    }
}
