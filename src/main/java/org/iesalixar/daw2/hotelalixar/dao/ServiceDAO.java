package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Service;
import java.sql.SQLException;
import java.util.List;
public interface ServiceDAO {
    List<Service> listAllService() throws SQLException;
    void insertService(Service service) throws SQLException;
    void updateService(Service service) throws SQLException;
    void deleteService(Long service_id) throws SQLException;
    Service getServiceById(Long service_id) throws SQLException;
}
