package com.skypro.sockswarehous.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO сущности Носков
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SockDTO {

    @JsonIgnore
    Long id;

    String color;

    Integer cottonPart;

    Integer quantity;

}
