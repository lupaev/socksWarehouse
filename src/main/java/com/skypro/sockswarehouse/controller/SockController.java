package com.skypro.sockswarehouse.controller;

import com.querydsl.core.types.Predicate;
import com.skypro.sockswarehouse.dto.SockAddDTO;
import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import com.skypro.sockswarehouse.repository.SockRepository;
import com.skypro.sockswarehouse.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Контроллер
 */

@RestController
@RequestMapping("/socks")
@Tag(name = "Склад носков")
@Slf4j
@RequiredArgsConstructor
public class SockController {

    private final SockService sockService;

    @Operation(summary = "Поступление носков на склад")
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SockDTO.class)))
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "bad request",
            content = @Content(schema = @Schema())
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema())
    )

    @PostMapping(value = "/add")
    public ResponseEntity<SockDTO> addSocks(
            @Valid @Parameter(name = "Носки") @RequestBody SockAddDTO sockAddDTO) {
        return ResponseEntity.ok(sockService.addSocks(sockAddDTO));
    }

    @Operation(summary = "Отгрузка носков со склад")
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @ApiResponse(
            responseCode = "400",
            description = "bad request",
            content = @Content(schema = @Schema())
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema())
    )

    @PostMapping(value = "/out")
    public void outSocks(@Valid @RequestBody SockAddDTO sockAddDTO) {
        sockService.out(sockAddDTO);
    }

    @Operation(summary = "Данные о количестве товара на складе")
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @ApiResponse(
            responseCode = "400",
            description = "bad request",
            content = @Content(schema = @Schema())
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema())
    )
    @GetMapping(value = "/all")
    public ResponseEntity<Page<SockDTO>> getAllSocks(@QuerydslPredicate(root = Sock.class,
            bindings = SockRepository.class) Predicate predicate, Pageable pageable) {
        return ResponseEntity.ok(sockService.getAll(predicate, pageable));
    }


}
