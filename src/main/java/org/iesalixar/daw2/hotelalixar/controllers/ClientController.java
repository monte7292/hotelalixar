package org.iesalixar.daw2.hotelalixar.controllers;

import org.iesalixar.daw2.hotelalixar.dao.ClientDAO;
import org.iesalixar.daw2.hotelalixar.entities.Client;
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
@RequestMapping("/clients")
public class ClientController {
    private static final Logger logger =
            LoggerFactory.getLogger(ClientController.class);
    // DAO para gestionar las operaciones de los clientes en la base de datos
    @Autowired
    private ClientDAO clientDAO;
    @GetMapping
    public String listClient(Model model) {
        logger.info("Solicitando la lista de clients");
        List<Client> listClients = null;
        try {
            listClients = clientDAO.listAllClients();
            logger.info("Se han cargado {} clients", listClients.size());
        } catch (SQLException e) {
            logger.error("Error al listar los clients: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al listar los clients");
        }
        model.addAttribute("listClients", listClients); // Pasar la lista de regiones al modelo
        return "client";
    }
    @GetMapping("/new")
    public String showNewForm(Model model) {
        Client client=new Client();
        logger.info("Mostrando formulario para nuevo cliente.");
        client.setClient_id(null);
        model.addAttribute("client", client); // Crear un nuevo objeto Client
        return "client-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("client_id") Long client_id, Model model) {
        logger.info("Mostrando formulario de edición para el cliente con ID {}", client_id);
        Client client = null;
        try {
            client = clientDAO.getClientById(client_id);
            if (client == null) {
                logger.warn("No se encontró el cliente con ID {}", client_id);
            }
        } catch (SQLException e) {
            logger.error("Error al obtener el cliente con ID {}: {}", client_id,
                    e.getMessage());
            model.addAttribute("errorMessage", "Error al obtener el client.");
        }
        model.addAttribute("client", client);
        return "client-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }
    @PostMapping("/insert")
    public String insertClient(@ModelAttribute("client") Client client,
                                 RedirectAttributes redirectAttributes) {
        logger.info("Insertando nuevo cliente con id {}", client.getClient_id());
        try {
            clientDAO.insertClients(client);
            logger.info("Client {} insertada con éxito.", client.getClient_id());
            redirectAttributes.addFlashAttribute("successMessage", "Cliente añadido exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al insertar el client {}: {}", client.getClient_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al insertar el client.");
        }
        return "redirect:/clients"; // Redirigir a la lista de clients
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute("client") Client client,
                                 RedirectAttributes redirectAttributes) {
        logger.info("Actualizando client con ID {}", client.getClient_id());
        try {
            clientDAO.updateClients(client);
            logger.info("Client con ID {} actualizada con éxito.", client.getClient_id());
            redirectAttributes.addFlashAttribute("successMessage", "Servicio actualizado exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al actualizar el client con ID {}: {}", client.getClient_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el client.");
        }
        return "redirect:/clients"; // Redirigir a la lista de clients
    }
    @PostMapping("/delete")
    public String deleteClient(@RequestParam("client_id") Long client_id, RedirectAttributes
            redirectAttributes) {
        logger.info("Eliminando client con ID {}", client_id);
        try {
            clientDAO.deleteClients(client_id);
            logger.info("Client con ID {} eliminada con éxito.", client_id);
        } catch (SQLException e) {
            logger.error("Error al eliminar la Client con ID {}: {}", client_id,
                    e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el Client.");
        }
        return "redirect:/clients"; // Redirigir a la lista de regiones
    }
}
