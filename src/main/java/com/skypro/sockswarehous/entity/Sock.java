package com.skypro.sockswarehous.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "socks")
@Entity
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "color")
    String color;

    @Column(name = "cotton_part")
    Integer CottonPart;

    @Column(name = "quantity")
    Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sock sock = (Sock) o;

        if (!Objects.equals(id, sock.id)) return false;
        if (!Objects.equals(color, sock.color)) return false;
        if (!Objects.equals(CottonPart, sock.CottonPart)) return false;
        return Objects.equals(quantity, sock.quantity);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (CottonPart != null ? CottonPart.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
