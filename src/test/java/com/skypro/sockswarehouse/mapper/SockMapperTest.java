package com.skypro.sockswarehouse.mapper;

import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SockMapperTest {

    @Autowired
    private SockMapper sockMapper;

    private SockDTO sockDTO;
    private Sock sock;

    @BeforeEach
    void setUp() {
        sockDTO = new SockDTO(1L, "red", 20, 100);
        sock = new Sock(1L, "red", 20, 100);
    }

    @Test
    void testMapper() {
        assertNotNull(sock);
        assertNotNull(sockDTO);
        Sock sock1 = sockMapper.toEntity(sockDTO);
        SockDTO sockDTO1 = sockMapper.toDTO(sock);
        assertNotNull(sock1);
        assertNotNull(sockDTO1);
        assertEquals(sock, sock1);
        assertEquals(sockDTO, sockDTO1);
        assertEquals(sock.getId(), sock1.getId());
        assertEquals(sockDTO.getId(), sockDTO1.getId());
        assertEquals(sock.getColor(), sock1.getColor());
        assertEquals(sockDTO.getColor(), sockDTO1.getColor());
        assertEquals(sock.getCottonPart(), sock1.getCottonPart());
        assertEquals(sockDTO.getCottonPart(), sockDTO1.getCottonPart());
        assertEquals(sock.getQuantity(), sock1.getQuantity());
        assertEquals(sockDTO.getQuantity(), sockDTO1.getQuantity());


    }

}