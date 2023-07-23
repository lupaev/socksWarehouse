package com.skypro.sockswarehouse.controller;

import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.service.SockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

  @GetMapping
  public String showAllSocksList(Model model, @RequestParam(value = "color", required = false) String color) {
    SockDTO sockDTO = new SockDTO();
    model.addAttribute("socks", sockService.findAllByKeyWord(color));
    model.addAttribute("sockDTO", sockDTO);
    model.addAttribute("color", color);
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


  @PostMapping(value = "/upd/{id}")
  public String updateById(@Valid @ModelAttribute(value = "sockDTO") SockDTO sockDTO) {
    sockService.updateById(sockDTO);
    return "redirect:/socks";
  }

  @GetMapping(value = "/upd/{id}")
  public String showFormUpdateById(Model model, SockDTO sockDTO) {
    model.addAttribute("sockDTO", sockService.findById(sockDTO.getId()));
    return "sock-upd_form";
  }

}
