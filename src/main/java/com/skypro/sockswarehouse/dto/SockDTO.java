package com.skypro.sockswarehouse.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

  @Schema(description = "Цвет")
  @NotBlank
  @NotNull
  @NotEmpty
  String color;

  @Min(value = 1, message = "Минимальное значение 1")
  @Max(value = 100, message = "Максимальное значение 100")
  @Schema(description = "Процентное содержание хлопка")
  @NotBlank
  @NotNull
  @NotEmpty
  Integer cottonPart;

  @Min(value = 1, message = "Минимальное значение 1")
  @Schema(description = "Количество")
  @NotBlank
  @NotNull
  @NotEmpty
  Integer quantity;

}
