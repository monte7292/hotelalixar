package org.iesalixar.daw2.hotelalixar.entities;

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
    private int employee_id;

    public Service(String service_name, String description, double price, int employee_id) {
        this.service_name = service_name;
        this.description = description;
        this.price = price;
        this.employee_id = employee_id;
    }
}
