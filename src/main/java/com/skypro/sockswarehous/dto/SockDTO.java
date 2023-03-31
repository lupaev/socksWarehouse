package com.skypro.sockswarehous.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SockDTO {

    Long id;

    String color;

    Integer cottonPart;

    Integer quantity;

}
