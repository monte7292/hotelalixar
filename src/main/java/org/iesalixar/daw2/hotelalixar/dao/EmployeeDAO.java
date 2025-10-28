package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Employee;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {
    List<Employee> listAllEmployee() throws SQLException;
    void insertEmployee(Employee employee) throws SQLException;
    void updateEmployee(Employee employee) throws SQLException ;
    void deleteEmployee(Long employee_id) throws SQLException;
    Employee getEmployeeById(Long employee_id) throws SQLException;
}


