package com.skypro.sockswarehous.service.impl;

import com.skypro.sockswarehous.entity.Sock;
import com.skypro.sockswarehous.mapper.SockMapper;
import com.skypro.sockswarehous.repository.SockRepository;
import com.skypro.sockswarehous.service.SockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SockServiceImpl implements SockService {

    private SockRepository sockRepository;

    private SockMapper sockMapper;

    public SockServiceImpl(SockRepository sockRepository, SockMapper sockMapper) {
        this.sockRepository = sockRepository;
        this.sockMapper = sockMapper;
    }


    @Override
    public Sock addSocks(String color, Integer cottonPart, Integer quantity) {
        Sock sock = new Sock(color, cottonPart, quantity);
        sockRepository.save(sock);
        return sock;
    }
}
