package org.iesalixar.daw2.hotelalixar.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long client_id;
    private String full_name;
    private String email;
    private String phone;

    @OneToOne
    @JoinColumn(name = "room_id") // FK hacia la tabla rooms
    private Room room_id;

    public Client(String full_name, String email, String phone, Room room_id){
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.room_id = room_id;
    }
}
