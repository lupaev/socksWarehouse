package com.skypro.sockswarehouse.controller;

import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.service.SockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Контроллер
 */

//@RestController
@Controller
@RequestMapping("/socks")
@Tag(name = "Склад носков")
@Slf4j
public class SockController {

  private final SockService sockService;

  public SockController(SockService sockService) {
    this.sockService = sockService;
  }

//  @Operation(summary = "Поступление носков на склад")
//  @ApiResponses({
//      @ApiResponse(
//          responseCode = "200",
//          description = "OK",
//          content = {
//              @Content(
//                  mediaType = "application/json",
//                  array = @ArraySchema(schema = @Schema(implementation = SockDTO.class)))
//          }
//      ),
//      @ApiResponse(
//          responseCode = "400",
//          description = "bad request",
//          content = @Content(schema = @Schema())
//      ),
//      @ApiResponse(
//          responseCode = "500",
//          description = "Internal Server Error",
//          content = @Content(schema = @Schema())
//      ),
//  })
//  @PostMapping(value = "/income")
//  public ResponseEntity<SockDTO> incomeSocks(
//      @Valid @Parameter(name = "Носки") @RequestBody SockDTO sockDTO) {
//    return ResponseEntity.ok(sockService.incomeSocks(sockDTO));
//  }
//
//  @Operation(summary = "Отгрузка носков со склад")
//  @ApiResponses({
//      @ApiResponse(
//          responseCode = "200",
//          description = "OK"
//      ),
//      @ApiResponse(
//          responseCode = "400",
//          description = "bad request",
//          content = @Content(schema = @Schema())
//      ),
//      @ApiResponse(
//          responseCode = "500",
//          description = "Internal Server Error",
//          content = @Content(schema = @Schema())
//      ),
//  })
//  @PostMapping(value = "/outcome")
//  public ResponseEntity<?> outcomeSocks(@NotBlank(message = "Поле обязательное для заполнения")
//  @Parameter(description = "Цвет носков", example = "red")
//  @RequestParam(name = "color") String color,
//      @NotBlank(message = "Поле обязательное для заполнения")
//      @Min(value = 1, message = "Минимальное значение 1")
//      @Max(value = 100, message = "Максимальное значение 100")
//      @Parameter(description = "Процентное содержание хлопка")
//      @RequestParam(name = "cotton") Integer cottonPart,
//      @NotBlank(message = "Поле обязательное для заполнения")
//      @Min(value = 1, message = "Минимальное значение 1")
//      @Parameter(description = "Количество пар в партии")
//      @RequestParam(name = "quantity") Integer quantity)
//      throws QuantityNotEnoughException {
//    sockService.outcomeSocks(color, cottonPart, quantity);
//    return ResponseEntity.ok().build();
//  }
//
//    @Operation(summary = "Данные о количестве товара на складе")
//  @ApiResponses({
//      @ApiResponse(
//          responseCode = "200",
//          description = "OK"
//      ),
//      @ApiResponse(
//          responseCode = "400",
//          description = "bad request",
//          content = @Content(schema = @Schema())
//      ),
//      @ApiResponse(
//          responseCode = "500",
//          description = "Internal Server Error",
//          content = @Content(schema = @Schema())
//      ),
//  })
//  @GetMapping
//  public ResponseEntity<Page<SockDTO>> getAllSocks(@QuerydslPredicate(root = Sock.class,
//      bindings = SockRepository.class) Predicate predicate, Pageable pageable) {
//    return ResponseEntity.ok(sockService.getAll(predicate, pageable));

  @GetMapping
  public String showAllSocksList(Model model) {
    SockDTO sockDTO = new SockDTO();
    model.addAttribute("socks", sockService.findAll());
    model.addAttribute("sockDTO", sockDTO);
    return "socks";
  }


  @GetMapping(value = "/add")
  public String showAddForm(Model model, SockDTO sockDTO) {
    model.addAttribute("sockDTO", sockDTO);
    return "sock-add_form";
  }

  @PostMapping(value = "/add")
  public String add(@ModelAttribute(value = "sockDTO") SockDTO sockDTO) {
    sockService.add(sockDTO);
    return "redirect:/socks";
  }

  @GetMapping(value = "/{id}")
  public String showSockById(Model model, @PathVariable(value = "id") Long id) throws Exception {
    SockDTO sockDTO = sockService.findById(id);
    model.addAttribute("sockDTO", sockDTO);
    return "sock-page";
  }

  @GetMapping(value = "/del/{id}")
  public String deleteById(@PathVariable(name = "id") Long id) {
    sockService.deleteById(id);
    return "redirect:/socks";
  }

  @GetMapping(value = "/sale")
  public String showFormDelete(Model model, SockDTO sockDTO) {
    model.addAttribute("sockDTO", sockDTO);
    return "sock-del_form";
  }

  @PostMapping(value = "/sale")
  public String saleSocks(@ModelAttribute(value = "sockDTO") SockDTO sockDTO) {
    sockService.outcomeSocks(sockDTO);
    return "redirect:/socks";
  }


}
