package com.skypro.sockswarehouse.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;

/**
 * Сущность Носков
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "socks")
@Entity
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "color")
    String color;

    @Column(name = "cotton_part")
    Integer cottonPart;

    @Column(name = "quantity")
    Integer quantity;

}
