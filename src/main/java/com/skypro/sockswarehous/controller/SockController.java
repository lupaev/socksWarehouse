package com.skypro.sockswarehous.controller;

import com.skypro.sockswarehous.dto.SockDTO;
import com.skypro.sockswarehous.exception.QuantityNotEnoughException;
import com.skypro.sockswarehous.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
@Tag(name = "Склад носков")
@Slf4j
public class SockController {

    private final SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

  @Operation(summary = "Добавить приход носков на склад")
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
          description = "bad request"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal Server Error"
      ),
  })
  @PostMapping(value = "/income")
  public ResponseEntity<SockDTO> incomeSocks(@RequestBody SockDTO sockDTO){
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
                    description = "bad request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            ),
    })
    @PostMapping(value = "/outcome")
    public ResponseEntity<?> outcomeSocks(@RequestParam (name = "color") String color,
                                         @RequestParam (name = "cotton") Integer cottonPart,
                                         @RequestParam (name = "quantity") Integer quantity) throws QuantityNotEnoughException {
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
                    description = "bad request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            ),
    })
    @GetMapping
    public ResponseEntity<Integer> getSocks(@RequestParam (name = "color") String color,
                                          @RequestParam (name = "cotton") Integer cottonPart,
                                          @RequestParam (name = "operation") String operation) throws QuantityNotEnoughException {
        return ResponseEntity.ok().body(sockService.getSocks(color, cottonPart, operation));
    }


}
