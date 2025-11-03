package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ServiceDAOImpl implements ServiceDAO {
    // Logger para registrar eventos importantes en el DAO
    private static final Logger logger = LoggerFactory.getLogger(ServiceDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    // Inyección de JdbcTemplate
    public ServiceDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Service> listAllService() {
        logger.info("Listing all provinces from the database.");
        String sql = "SELECT * FROM services";
        List<Service> services = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Service.class));
        logger.info("Retrieved {} province from the database.", services.size());
        return services;
    }

    @Override
    public void insertService(Service service) {
        logger.info("Inserting service with service_name: {} , {}, {}, {} ", service.getService_name(), service.getDescription(), service.getPrice(), service.getEmployee_id());
        String sql = "INSERT INTO services (service_name, description, price, employee_id) VALUES (?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, service.getService_name(), service.getDescription(), service.getPrice(), service.getEmployee_id());
        logger.info("Inserted services. Rows affected: {}", rowsAffected);
    }

    @Override
    public void updateService(Service service) {
        logger.info("Updating service with service_id: {}", service.getService_id());
        String sql = "UPDATE services SET service_name = ?, description = ?, price = ?, employee_id = ? WHERE service_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, service.getService_name(), service.getDescription(), service.getPrice(), service.getEmployee_id() ,service.getService_id());
        logger.info("Updated service. Rows affected: {}", rowsAffected);
    }

    @Override
    public void deleteService(Long service_id) {
        logger.info("Deleting services with service_id: {}", service_id);
        String sql = "DELETE FROM services WHERE service_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, service_id);
        logger.info("Deleted service. Rows affected: {}", rowsAffected);
    }

    @Override
    public Service getServiceById(Long service_id) throws SQLException {
        logger.info("Retrieving service by service_id: {}", service_id);
        String sql = "SELECT * FROM services WHERE service_id = ?";
        try {
            Service service = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Service.class), service_id);
            logger.info("Service retrieved: {} - {} - {}", service.getService_name(), service.getDescription(), service.getPrice());
            return service;
        } catch (Exception e) {
            logger.warn("No service found with service_id: {}", service_id);
            return null;
        }
    }
}
