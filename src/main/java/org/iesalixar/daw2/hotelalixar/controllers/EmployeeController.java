package org.iesalixar.daw2.hotelalixar.controllers;

import org.iesalixar.daw2.hotelalixar.dao.EmployeeDAO;
import org.iesalixar.daw2.hotelalixar.entities.Employee;
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
@RequestMapping("/employees")
public class EmployeeController {
    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeController.class);
    // DAO para gestionar las operaciones de las regiones en la base de datos
    @Autowired
    private EmployeeDAO employeeDAO;
    @GetMapping
    public String listEmployees(Model model) {
        logger.info("Solicitando la lista de employees");
        List<Employee> listEmployees = null;
        try {
            listEmployees = employeeDAO.listAllEmployee();
            logger.info("Se han cargado {} employees", listEmployees.size());
        } catch (SQLException e) {
            logger.error("Error al listar los employees: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al listar los employees");
        }
        model.addAttribute("listEmployees", listEmployees); // Pasar la lista de regiones al modelo
        return "employee";
    }
    @GetMapping("/new")
    public String showNewForm(Model model) {
        Employee employee=new Employee();
        logger.info("Mostrando formulario para nuevo empleado.");
        employee.setEmployee_id(null);
        model.addAttribute("employee", employee); // Crear un nuevo objeto Employee
        return "employee-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("employee_id") Long employee_id, Model model) {
        logger.info("Mostrando formulario de edición para el empleado con ID {}", employee_id);
        Employee employee = null;
        try {
            employee = employeeDAO.getEmployeeById(employee_id);
            if (employee == null) {
                logger.warn("No se encontró el empleado con ID {}", employee_id);
            }
        } catch (SQLException e) {
            logger.error("Error al obtener el empleado con ID {}: {}", employee,
                    e.getMessage());
            model.addAttribute("errorMessage", "Error al obtener el employee.");
        }
        model.addAttribute("employee", employee);
        return "employee-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    @PostMapping("/insert")
    public String insertEmployee(@ModelAttribute("employee") Employee employee,
                               RedirectAttributes redirectAttributes) {
        logger.info("Insertando nuevo empleado con id {}", employee.getEmployee_id());
        try {

            employeeDAO.insertEmployee(employee);
            logger.info("Employee {} insertada con éxito.", employee.getEmployee_id());
            redirectAttributes.addFlashAttribute("successMessage", "Empleado añadido exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al insertar el employee {}: {}", employee.getEmployee_id(),
                    e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al insertar el employee.");
        }
        return "redirect:/employees"; // Redirigir a la lista de employees
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") Employee employee,
                               RedirectAttributes redirectAttributes) {
        logger.info("Actualizando employee con ID {}", employee.getEmployee_id());
        try {
            employeeDAO.updateEmployee(employee);
            logger.info("Employee con ID {} actualizada con éxito.", employee.getEmployee_id());
            redirectAttributes.addFlashAttribute("successMessage", "Empleado actualizado exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al actualizar el employee con ID {}: {}", employee.getEmployee_id(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el employee.");
        }
        return "redirect:/employees"; // Redirigir a la lista de employees
    }
    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam("employee_id") Long employee_id, RedirectAttributes
            redirectAttributes) {
        logger.info("Eliminando employee con ID {}", employee_id);
        try {
            employeeDAO.deleteEmployee(employee_id);
            logger.info("Employee con ID {} eliminada con éxito.", employee_id);
            redirectAttributes.addFlashAttribute("successMessage", "Empleado eliminado exitosamente.");
        } catch (SQLException e) {
            logger.error("Error al eliminar la Employee con ID {}: {}", employee_id,
                    e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el Employee.");
        }
        return "redirect:/employees"; // Redirigir a la lista de regiones
    }
}


