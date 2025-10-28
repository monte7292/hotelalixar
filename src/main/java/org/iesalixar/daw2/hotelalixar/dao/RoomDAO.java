package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Room;
import java.sql.SQLException;
import java.util.List;
public interface RoomDAO {
    List<Room> listAllRoom() throws SQLException;
    void insertRoom(Room room) throws SQLException;
    void updateRoom(Room room) throws SQLException;
    void deleteRoom(Long room_id) throws SQLException;
    Room getRoomById(Long room_id) throws SQLException;
}