package com.skypro.sockswarehouse.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Schema(description = "Сущность")
public class SockDTO {

    @Schema(description = "Идентификатор")
    Long id;

    @NotNull
    @NotBlank
    @Schema(description = "Цвет")
    String color;

    @Min(value = 1, message = "Минимальное значение 1")
    @Max(value = 100, message = "Максимальное значение 100")
    @Schema(description = "Процентное содержание хлопка")
    Integer cottonPart;

    @Min(value = 1, message = "Минимальное значение 1")
    @Schema(description = "Количество")
    Integer quantity;

}
