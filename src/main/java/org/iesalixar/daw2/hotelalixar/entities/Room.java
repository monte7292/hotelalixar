package org.iesalixar.daw2.hotelalixar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Room {
    private Long room_id;
    private String room_number;
    private String room_type;

    public Room(String room_number, String room_type) {
        this.room_number = room_number;
        this.room_type = room_type;
    }
}
