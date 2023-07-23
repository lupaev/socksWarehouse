package com.skypro.sockswarehouse.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

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
    @NotBlank
    @NotNull
    @NotEmpty
    Long id;

    @Column(name = "color")
    @NotBlank
    @NotNull
    @NotEmpty
    String color;

    @Column(name = "cotton_part")
    @NotBlank
    @NotNull
    @NotEmpty
    Integer cottonPart;

    @Column(name = "quantity")
    @NotBlank
    @NotNull
    @NotEmpty
    Integer quantity;

}
