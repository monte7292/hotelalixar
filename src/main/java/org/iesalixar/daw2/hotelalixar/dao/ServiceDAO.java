package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Service;
import java.sql.SQLException;
import java.util.List;
public interface ServiceDAO {
    //Hacemos el listado de las entidades de provicna
    List<Service> listAllService() throws SQLException;
    //Generamos del contructor Service
    void insertService(Service service) throws SQLException;
    void updateService(Service service) throws SQLException;
    void deleteService(int id) throws SQLException;
}
