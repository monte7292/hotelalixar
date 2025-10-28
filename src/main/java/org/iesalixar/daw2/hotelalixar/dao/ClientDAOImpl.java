package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class ClientDAOImpl implements ClientDAO{

    private static final Logger logger = LoggerFactory.getLogger(ClientDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ClientDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Client> listAllClients() throws SQLException {
        logger.info("Listing all clients from the database.");
        String sql = "SELECT * FROM clients";
        List<Client> clients = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client.class));
        logger.info("Retrieved {} clients from the database.", clients.size());
        return clients;
    }

    @Override
    public void insertClients(Client client) throws SQLException {
        logger.info("Inserting client with full_name: {} and email: {} and phone: {}", client.getFull_name(), client.getEmail(), client.getPhone());
        String sql = "INSERT INTO clients (full_name, email, phone, room_id) VALUES (?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(
                sql,
                client.getFull_name(),
                client.getEmail(),
                client.getPhone(),
                client.getRoom_id() != null ? client.getRoom_id().getRoom_id() : null
        );
        logger.info("Inserted client. Rows affected: {}", rowsAffected);
    }

    @Override
    public void updateClients(Client client) throws SQLException {
        logger.info("Updating client with client_id: {}", client.getClient_id());
        String sql = "UPDATE clients SET full_name = ?, email = ?, phone = ?, room_id = ? WHERE client_id = ?";
        int rowsAffected = jdbcTemplate.update(
                sql,
                client.getFull_name(),
                client.getEmail(),
                client.getPhone(),
                client.getRoom_id() != null ? client.getRoom_id().getRoom_id() : null,
                client.getClient_id()
        );
        logger.info("Updated client. Rows affected: {}", rowsAffected);
    }

    @Override
    public void deleteClients(Long client_id) throws SQLException {
        logger.info("Deleting client with client_id: {}", client_id);
        String sql = "DELETE FROM clients WHERE client_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, client_id);
        logger.info("Deleted client. Rows affected: {}", rowsAffected);
    }

    @Override
    public Client getClientById(Long client_id) throws SQLException {
        logger.info("Retrieving client by client_id: {}", client_id);
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        try {
            Client client = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Client.class), client_id);
            logger.info("Client retrieved: {} - {} - {}", client.getFull_name(), client.getEmail(), client.getPhone());
            return client;
        } catch (Exception e) {
            logger.warn("No client found with client_id: {}", client_id);
            return null;
        }
    }
}
