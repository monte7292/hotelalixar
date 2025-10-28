package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        logger.info("Inserting service with service_name: {} ,description: {}, and price {}", service.getService_name(), service.getDescription(), service.getPrice());
        String sql = "INSERT INTO services (service_name, description, price) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, service.getService_name(), service.getDescription(), service.getPrice());
        logger.info("Inserted services. Rows affected: {}", rowsAffected);
    }

    @Override
    public void updateService(Service service) {
        logger.info("Updating service with id: {}", service.getService_id());
        String sql = "UPDATE services SET service_name = ?, description = ? , price = ? , WHERE service_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, service.getService_name(), service.getDescription(), service.getPrice());
        logger.info("Updated service. Rows affected: {}", rowsAffected);
    }

    @Override
    public void deleteService(Long id) {
        logger.info("Deleting services with id: {}", id);
        String sql = "DELETE FROM services WHERE service_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        logger.info("Deleted service. Rows affected: {}", rowsAffected);
    }
}
