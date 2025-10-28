package org.iesalixar.daw2.hotelalixar.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client_service {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client_id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service_id;
    private Date service_date;
}
