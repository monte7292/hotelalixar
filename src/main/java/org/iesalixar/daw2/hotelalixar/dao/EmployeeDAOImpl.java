package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    // Logger para registrar eventos importantes en el DAO
    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    // Inyección de JdbcTemplate
    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     * Listar todos los Empleados de la base de datos.
     * @return Lista de Employee
     */
    @Override
    public List<Employee> listAllEmployee() {
        logger.info("Listing all employee from the database.");
        String sql = "SELECT * FROM employees";
        List<Employee> employees = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
        logger.info("Retrieved {} employee from the database.", employees.size());
        return employees;
    }
    /**
     * Insertar un nuevo empleado en la base de datos.
     * @param employee Employee a insertar
     */
    @Override
    public void insertEmployee(Employee employee) {
        logger.info("Inserting Employee full_name: {}",
                employee.getFull_name());
        String sql = "INSERT INTO employees (full_name) VALUES (?, ?)";
        Long rowsAffected = (long) jdbcTemplate.update(sql, employee.getEmployee_id(), employee.getFull_name());
        logger.info("Inserted employee. Rows affected: {}", rowsAffected);
    }
    /**
     * Actualizar un empleado existente en la base de datos.
     * @param employee Empleado a actualizar
     */
    @Override
    public void updateEmployee(Employee employee) {
        logger.info("Updating employee with id: {}", employee.getEmployee_id());
        String sql = "UPDATE employees SET Employee_id = ?, full_name = ?";
        Long rowsAffected = (long) jdbcTemplate.update(sql, employee.getEmployee_id(), employee.getFull_name());
        logger.info("Updated employee. Rows affected: {}", rowsAffected);
    }
    /**
     * Eliminar un empleado de la base de datos.
     * @param employee_id ID del empleado a eliminar
     */
    @Override
    public void deleteEmployee(Long employee_id) {
        logger.info("Deleting employee with employee_id: {}", employee_id);
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        Long rowsAffected = (long) jdbcTemplate.update(sql, employee_id);
        logger.info("Deleted employee. Rows affected: {}", rowsAffected);
    }

    @Override
    public Employee getEmployeeById(Long employee_id) throws SQLException {
        logger.info("Retrieving employee by employee_id: {}", employee_id);
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        try {
            Employee employee = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), employee_id);
            logger.info("Employee retrieved: {}", employee.getFull_name());
            return employee;
        } catch (Exception e) {
            logger.warn("No employee found with employee_id: {}", employee_id);
            return null;
        }
    }
}
