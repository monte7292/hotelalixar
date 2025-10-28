package org.iesalixar.daw2.hotelalixar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
    private Long employee_id;
    private String full_name;

    public Employee(String full_name) {
        this.full_name = full_name;
    }
}
