package com.skypro.sockswarehouse.controller;

import com.skypro.sockswarehouse.dto.SockAddDTO;
import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import com.skypro.sockswarehouse.repository.SockRepository;
import com.skypro.sockswarehouse.service.SockService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SockController.class)
class SockControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private SockController sockController;
    @MockBean
    private SockService sockService;
    @MockBean
    private SockRepository sockRepository;


    @Test
    void contextLoads() {
        assertNotNull(sockController);
    }

    @Test
    void addSocks() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        String url = "/socks/add";
        SockDTO sockDTO = new SockDTO(1L, "red", 20, 100);
        SockAddDTO sockAddDTO = new SockAddDTO("red", 20, 100);
        Sock sock = new Sock(1L, "red", 20, 100);
        JSONObject sockDTOJSON = new JSONObject();
        sockDTOJSON.put("color", sockDTO.getColor());
        sockDTOJSON.put("cottonPart", sockDTO.getCottonPart());
        sockDTOJSON.put("quantity", sockDTO.getQuantity());

        when(sockService.addSocks(sockAddDTO)).thenReturn(sockDTO);
        when(sockRepository.save(sock)).thenReturn(sock);
        mockMvc.perform(post(url)
                        .content(String.valueOf(sockDTOJSON))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void outcomeSocks() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        String url = "/socks/out";
        Sock sock = new Sock(1L, "red", 20, 100);
        SockAddDTO sockAddDTO = new SockAddDTO("red", 20, 100);
        Sock sock1 = new Sock(1L, "red", 20, 20);
        Collection<Sock> socks = new ArrayList<>(List.of(sock));
        JSONObject sockDTOJSON = new JSONObject();
        sockDTOJSON.put("color", sockAddDTO.getColor());
        sockDTOJSON.put("cottonPart", sockAddDTO.getCottonPart());
        sockDTOJSON.put("quantity", sockAddDTO.getQuantity());

        lenient().doNothing().when(sockService).out(sockAddDTO);
        when(sockRepository.findByColorAndCottonPart(sockAddDTO.getColor(), sockAddDTO.getCottonPart()))
                .thenReturn(socks);
        lenient().doNothing().when(sockRepository)
                .deleteAllByColorAndCottonPart(sockAddDTO.getColor(), sockAddDTO.getCottonPart());
        when(sockRepository.save(sock1)).thenReturn(sock1);
        mockMvc.perform(post(url)
                        .content(String.valueOf(sockDTOJSON))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllSocks() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        String url = "/socks/all";
        Sock sock = new Sock(1L, "red", 20, 100);
        List<Sock> socks = new ArrayList<>(List.of(sock));
        when(sockRepository.findAll()).thenReturn(socks);
        mockMvc.perform(get(url)
                        .param("color", sock.getColor())
                        .param("cotton", String.valueOf(sock.getCottonPart()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}