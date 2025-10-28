package org.iesalixar.daw2.hotelalixar.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Service {
    private Long service_id;
    private String service_name;
    private String description;
    private double price;

    @OneToMany
    @JoinColumn(name = "employee_id") // FK hacia la tabla rooms
    private Employee employee_id;

    public Service(String service_name, String description, double price, Employee employee_id) {
        this.service_name = service_name;
        this.description = description;
        this.price = price;
        this.employee_id = employee_id;
    }
}
