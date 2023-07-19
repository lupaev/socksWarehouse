package com.skypro.sockswarehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping
  public String index() {
    return "index";
  }

//  @GetMapping
//  public String showFacultyList(Model model) {
//    FacultyDTO facultyDTO = new FacultyDTO();
//    model.addAttribute("faculties", service.getAll());
//    model.addAttribute("facultyDTO", facultyDTO);
//    return "faculties";
//  }

}
