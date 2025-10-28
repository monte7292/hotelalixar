package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Client_service;
import java.sql.SQLException;
import java.util.List;

public interface Client_serviceDAO {
    List<Client_service> listAllClient_Services() throws SQLException;
    void insertClient_services(Client_service client_service) throws SQLException;
    void updateClient_services(Client_service client_service) throws SQLException;
    void deleteClient_services(Long client_id, Long service_id) throws SQLException;
    Client_service getClient_serviceById(Long client_id, Long service_id) throws SQLException;
}
