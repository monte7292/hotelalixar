package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Client;
import java.sql.SQLException;
import java.util.List;

public interface ClientDAO {
    List<Client> listAllClients() throws SQLException;
    void insertClients(Client client) throws SQLException;
    void updateClients(Client client) throws SQLException;
    void deleteClients(Long client_id) throws SQLException;
}
