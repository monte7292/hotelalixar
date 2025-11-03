package org.iesalixar.daw2.hotelalixar.controllers;

import org.iesalixar.daw2.hotelalixar.dao.RoomDAO;
import org.iesalixar.daw2.hotelalixar.entities.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    // DAO para gestionar las operaciones de las regiones en la base de datos
    @Autowired
    private RoomDAO roomDAO;

    @GetMapping
    public String listRooms(Model model) {
        logger.info("Solicitando la lista de todos las habitaciones...");
        List<Room> listRooms = null;
        try {
            listRooms = roomDAO.listAllRoom();
            logger.info("Se han cargado {} habitaciones.", listRooms.size());
        } catch (SQLException e) {
            logger.error("Error al listar las habitaciones: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al listar las habitaciones.");
        }
        model.addAttribute("listRooms", listRooms); // Pasar la lista de regiones al modelo
        return "room"; // Nombre de la plantilla Thymeleaf a renderizar
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva habitacion...");
        // Cambiado a 'province' para coincidir con la plantilla Thymeleaf
        model.addAttribute("room", new Room());
        return "room-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("room_id") Long room_id, Model model) {
        logger.info("Mostrando formulario de edición para la habitacion con ID {}", room_id);
        Room room = null;
        try {
            room = roomDAO.getRoomById(room_id);
            if (room == null) {
                logger.warn("No se encontró la habitacion con ID {}", room_id);
            }
        } catch (SQLException e) {
            logger.error("Error al obtener la habitacion con ID {}: {}", room_id, e.getMessage());
            model.addAttribute("errorMessage", "Error al obtener la habitacion.");
        }
        // Cambiado a 'province' para coincidir con la plantilla Thymeleaf
        model.addAttribute("room", room);

        return "room-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    @PostMapping("/insert")
    public String insertRoom(@ModelAttribute("room") Room room, RedirectAttributes redirectAttributes) {
        logger.info("Insertando nueva habitacion con número {}", room.getRoom_number());
        try {
            // Validar si el número de habitación ya existe
            if (roomDAO.existsByRoomNumber(room.getRoom_number())) {
                logger.warn("El número de habitación {} ya existe", room.getRoom_number());
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "El número de habitación " + room.getRoom_number() + " ya existe. Por favor, elija otro número.");
                return "redirect:/rooms/new";
            }
            
            roomDAO.insertRoom(room);
            logger.info("Room {} insertada con éxito.", room.getRoom_number());
            redirectAttributes.addFlashAttribute("successMessage", "Habitación creada exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al insertar la habitacion {}: {}", room.getRoom_number(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al insertar la habitacion.");
            return "redirect:/rooms/new";
        }

        return "redirect:/rooms"; // Redirigir a la lista de habitaciones
    }

    @PostMapping("/update")
    public String updateRoom(@ModelAttribute("room") Room room, RedirectAttributes redirectAttributes) {
        logger.info("Actualizando habitacion con ID {} y número {}", room.getRoom_id(), room.getRoom_number());
        try {
            // Validar si el número de habitación ya existe (excluyendo la habitación actual)
            if (roomDAO.existsByRoomNumberExcludingId(room.getRoom_number(), room.getRoom_id())) {
                logger.warn("El número de habitación {} ya existe para otra habitación", room.getRoom_number());
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "El número de habitación " + room.getRoom_number() + " ya existe. Por favor, elija otro número.");
                return "redirect:/rooms/edit?room_id=" + room.getRoom_id();
            }
            
            roomDAO.updateRoom(room);
            logger.info("Habitacion con ID {} actualizada con éxito.", room.getRoom_id());
            redirectAttributes.addFlashAttribute("successMessage", "Habitación actualizada exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al actualizar la habitacion con ID {}: {}", room.getRoom_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar la habitacion.");
            return "redirect:/rooms/edit?room_id=" + room.getRoom_id();
        }
        return "redirect:/rooms"; // Redirigir a la lista de habitaciones
    }

    @PostMapping("/delete")
    public String deleteRoom(@RequestParam("room_id") Long room_id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando habiatacion con ID {}", room_id);
        try {
            roomDAO.deleteRoom(room_id);
            logger.info("Habitacion con ID {} eliminada con éxito.", room_id);
            redirectAttributes.addFlashAttribute("successMessage", "Habitación borrada exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al eliminar la habitacion con ID {}: {}", room_id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar la habitacion.");
        }
        return "redirect:/rooms"; // Redirigir a la lista de regiones
    }
}

