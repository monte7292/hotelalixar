package org.iesalixar.daw2.hotelalixar.dao;

import org.iesalixar.daw2.hotelalixar.entities.Room;
import org.iesalixar.daw2.hotelalixar.entities.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class RoomDAOImpl implements RoomDAO {
    // Logger para registrar eventos importantes en el DAO
    private static final Logger logger = LoggerFactory.getLogger(RoomDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    // Inyección de JdbcTemplate
    public RoomDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Room> listAllRoom() {
        logger.info("Listing all rooms from the database.");
        String sql = "SELECT * FROM rooms";
        List<Room> rooms = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Room.class));
        logger.info("Retrieved {} province from the database.", rooms.size());
        return rooms;
    }

    @Override
    public void insertRoom(Room room) {
        logger.info("Inserting room, {}, {}, {}", room.getRoom_number(), room.getRoom_type(), room.getPrice());
        String sql = "INSERT INTO rooms (room_number, room_type, price) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, room.getRoom_number(), room.getRoom_type(), room.getPrice());
        logger.info("Inserted services. Rows affected: {}", rowsAffected);
    }

    @Override
    public void updateRoom(Room room) {
        logger.info("Updating room {}", room.getRoom_id());
        String sql = "UPDATE rooms SET room_number = ?, room_type = ? , price = ? , WHERE room_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, room.getRoom_number(), room.getRoom_type(), room.getPrice());
        logger.info("Updated rooms. Rows affected: {}", rowsAffected);
    }

    @Override
    public void deleteRoom(Long id) {
        logger.info("Deleting room {}", id);
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        logger.info("Deleted room. Rows affected: {}", rowsAffected);
    }
}
