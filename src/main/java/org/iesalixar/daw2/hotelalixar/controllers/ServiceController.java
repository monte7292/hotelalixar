package org.iesalixar.daw2.hotelalixar.controllers;

import org.iesalixar.daw2.hotelalixar.dao.ServiceDAO;
import org.iesalixar.daw2.hotelalixar.entities.Service;
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
@RequestMapping("/services")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    // DAO para gestionar las operaciones de las regiones en la base de datos
    @Autowired
    private ServiceDAO serviceDAO;

    @GetMapping
    public String listServices(Model model) {
        logger.info("Solicitando la lista de todos los servicios...");
        List<Service> listServices = null;
        try {
            listServices = serviceDAO.listAllService();
            logger.info("Se han cargado {} servicios.", listServices.size());
        } catch (SQLException e) {
            logger.error("Error al listar los servicios: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al listar los servicios.");
        }
        model.addAttribute("listServices", listServices); // Pasar la lista de regiones al modelo
        return "service"; // Nombre de la plantilla Thymeleaf a renderizar
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo servicio.");
        // Cambiado a 'province' para coincidir con la plantilla Thymeleaf
        model.addAttribute("service", new Service());
        return "service-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("service_id") Long service_id, Model model) {
        logger.info("Mostrando formulario de edición para el servicio con ID {}", service_id);
        Service service = null;
        try {
            service = serviceDAO.getServiceById(service_id);
            if (service == null) {
                logger.warn("No se encontró el servicio con ID {}", service_id);
            }
        } catch (SQLException e) {
            logger.error("Error al obtener el servicio con ID {}: {}", service_id, e.getMessage());
            model.addAttribute("errorMessage", "Error al obtener la región.");
        }
        // Cambiado a 'province' para coincidir con la plantilla Thymeleaf
        model.addAttribute("service", service);

        return "service-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    @PostMapping("/insert")
    public String insertService(@ModelAttribute("service") Service service, RedirectAttributes redirectAttributes) {
        logger.info("Insertando nuevo servicio con código {}", service.getService_id());
        try {
            serviceDAO.insertService(service);
            logger.info("Servicio {} insertada con éxito.", service.getService_id());
        } catch (SQLException e) {
            logger.error("Error al insertar el servicio {}: {}", service.getService_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al insertar la región.");
        }

        return "redirect:/services"; // Redirigir a la lista de regiones
    }

    @PostMapping("/update")
    public String updateProvincia(@ModelAttribute("service") Service service, RedirectAttributes redirectAttributes) {
        logger.info("Actualizando service con ID {}", service.getService_id());
        try {
            serviceDAO.updateService(service);
            logger.info("Service con ID {} actualizada con éxito.", service.getService_id());
            redirectAttributes.addFlashAttribute("successMessage", "Servicio actualizado exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al actualizar el servicio con ID {}: {}", service.getService_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el servicio.");
        }
        return "redirect:/services"; // Redirigir a la lista de regiones
    }

    @PostMapping("/delete")
    public String deleteProvincia(@RequestParam("service_id") Long service_id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando servicio con ID {}", service_id);
        try {
            serviceDAO.deleteService(service_id);
            logger.info("Servicio con ID {} eliminada con éxito.", service_id);
        } catch (SQLException e) {
            logger.error("Error al eliminar el servicio con ID {}: {}", service_id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el servicio.");
        }
        return "redirect:/services"; // Redirigir a la lista de regiones
    }
}
