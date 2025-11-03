package org.iesalixar.daw2.hotelalixar.controllers;

import org.iesalixar.daw2.hotelalixar.dao.Client_serviceDAO;
import org.iesalixar.daw2.hotelalixar.entities.Client_service;
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
@RequestMapping("/clients_services")
public class Client_serviceController {

    private static final Logger logger = LoggerFactory.getLogger(Client_serviceController.class);
    // DAO para gestionar las operaciones de los servicios de los clientes en la base de datos

    @Autowired
    private Client_serviceDAO clientServiceDAO;

    @GetMapping
    public String listAllClient_Services(Model model) {
        logger.info("Solicitando la lista de clients_services");
        List<Client_service> listClients_services = null;
        try {
            listClients_services = clientServiceDAO.listAllClient_Services();
            logger.info("Se han cargado {} clients_services", listClients_services.size());
        } catch (SQLException e) {
            logger.error("Error al listar los clients_services: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al listar los clients_services");
        }
        model.addAttribute("listClients_services", listClients_services); // Pasar la lista de servicios de clientes al modelo
        return "client_service";
    }
    @GetMapping("/new")
    public String showNewForm(Model model) {
        Client_service client_service=new Client_service();
        logger.info("Mostrando formulario para nuevo servicio de cliente.");
        client_service.setClient_id(null);
        model.addAttribute("client_service", client_service); // Crear un nuevo objeto Client_service
        return "client_service-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("client_id") Long client_id, @RequestParam("service_id") Long service_id, Model model) {
        logger.info("Mostrando formulario de edición para los servicios de clientes con ID {}, {}", client_id, service_id);
        Client_service client_service = null;
        try {
            client_service = clientServiceDAO.getClient_serviceById(client_id, service_id);
            if (client_service == null) {
                logger.warn("No se encontró el servicio del cliente con ID {}, {}", client_id, service_id);
            }
        } catch (SQLException e) {
            logger.error("Error al obtener el servicio del cliente con ID {}, {}: {}", client_id, service_id, e.getMessage());
            model.addAttribute("errorMessage", "Error al obtener el client_service.");
        }
        model.addAttribute("client_service", client_service);
        return "client_service-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }
    @PostMapping("/insert")
    public String insertClient_services(@ModelAttribute("client_service") Client_service client_service, RedirectAttributes redirectAttributes) {
        logger.info("Insertando nuevo servicio de cliente con id {}, {}", client_service.getClient_id(), client_service.getService_id());
        try {
            clientServiceDAO.insertClient_services(client_service);
            logger.info("Client_service {} {} insertada con éxito.", client_service.getClient_id(), client_service.getService_id());
            redirectAttributes.addFlashAttribute("successMessage", "Servicio de Cliente añadido exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al insertar el client_service {}, {}: {}", client_service.getClient_id(), client_service.getService_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al insertar el client_service.");
        }
        return "redirect:/clients_services"; // Redirigir a la lista de clients_services
    }

    @PostMapping("/update")
    public String updateClient_services(@ModelAttribute("client_service") Client_service client_service, RedirectAttributes redirectAttributes) {
        logger.info("Actualizando client_service con ID {}, {}", client_service.getClient_id(), client_service.getService_id());
        try {
            clientServiceDAO.updateClient_services(client_service);
            logger.info("Client_service con ID {}, {} actualizada con éxito.", client_service.getClient_id(), client_service.getService_date());
            redirectAttributes.addFlashAttribute("successMessage", "Servicio de Cliente actualizado exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al actualizar el client_service con ID {}, {}: {}", client_service.getClient_id(), client_service.getService_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el client_service.");
        }
        return "redirect:/clients_services"; // Redirigir a la lista de clients_services
    }

    @PostMapping("/delete")
    public String deleteClient_services(@RequestParam("client_id") Long client_id, @RequestParam("service_id") Long service_id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando client_service con ID {}, {}", client_id, service_id);
        try {
            clientServiceDAO.deleteClient_services(client_id, service_id);
            logger.info("Client_service con ID {}, {} eliminada con éxito.", client_id, service_id);
            redirectAttributes.addFlashAttribute("errorMessage", "Servicio de Cliente eliminado exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al eliminar la Client_service con ID {}, {}: {}", client_id, service_id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el client_service.");
        }
        return "redirect:/clients_services";
    }
}
