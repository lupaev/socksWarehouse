package com.skypro.sockswarehouse.controller;

import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.ComparisonOperation;
import com.skypro.sockswarehouse.exception.QuantityNotEnoughException;
import com.skypro.sockswarehouse.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


/**
 * Контроллер
 */

@RestController
@RequestMapping("/socks")
@Tag(name = "Склад носков")
@Slf4j
public class SockController {

  private final SockService sockService;

  public SockController(SockService sockService) {
    this.sockService = sockService;
  }

  @Operation(summary = "Поступление носков на склад")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "OK",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = SockDTO.class)))
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "bad request",
          content = @Content(schema = @Schema())
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal Server Error",
          content = @Content(schema = @Schema())
      ),
  })
  @PostMapping(value = "/income")
  public ResponseEntity<SockDTO> incomeSocks(
      @Valid @Parameter(name = "Носки") @RequestBody SockDTO sockDTO) {
    return ResponseEntity.ok(sockService.incomeSocks(sockDTO));
  }

  @Operation(summary = "Отгрузка носков со склад")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "OK"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "bad request",
          content = @Content(schema = @Schema())
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal Server Error",
          content = @Content(schema = @Schema())
      ),
  })
  @PostMapping(value = "/outcome")
  public ResponseEntity<?> outcomeSocks(@NotBlank(message = "Поле обязательное для заполнения")
  @Parameter(description = "Цвет носков", example = "red")
  @RequestParam(name = "color") String color,
      @NotBlank(message = "Поле обязательное для заполнения")
      @Min(value = 1, message = "Минимальное значение 1")
      @Max(value = 100, message = "Максимальное значение 100")
      @Parameter(description = "Процентное содержание хлопка")
      @RequestParam(name = "cotton") Integer cottonPart,
      @NotBlank(message = "Поле обязательное для заполнения")
      @Min(value = 1, message = "Минимальное значение 1")
      @Parameter(description = "Количество пар в партии")
      @RequestParam(name = "quantity") Integer quantity)
      throws QuantityNotEnoughException {
    sockService.outcomeSocks(color, cottonPart, quantity);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Данные о количестве товара на складе")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "OK"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "bad request",
          content = @Content(schema = @Schema())
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal Server Error",
          content = @Content(schema = @Schema())
      ),
  })
  @GetMapping
  public ResponseEntity<Integer> getAllSocks(@NotBlank(message = "Поле обязательное для заполнения")
  @Parameter(description = "Цвет носков", example = "red")
  @RequestParam(name = "color") String color,
      @NotBlank(message = "Поле обязательное для заполнения")
      @Min(value = 1, message = "Минимальное значение 1")
      @Max(value = 100, message = "Максимальное значение 100")
      @Parameter(description = "Процентное содержание хлопка")
      @RequestParam(name = "cotton") Integer cottonPart,
      @NotBlank(message = "Поле обязательное для заполнения")
      @Parameter(description = "Операция сравнения")
      @RequestParam(name = "comparisonOperation") ComparisonOperation comparisonOperation)
      throws QuantityNotEnoughException {
    return ResponseEntity.ok().body(sockService.getSocks(color, cottonPart, comparisonOperation));
  }


}
