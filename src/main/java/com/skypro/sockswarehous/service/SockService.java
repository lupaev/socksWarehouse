package com.skypro.sockswarehous.service;


import com.skypro.sockswarehous.dto.SockDTO;
import com.skypro.sockswarehous.entity.Sock;

/**
 * Сервис склада
 */
public interface SockService {


     SockDTO addSocks(SockDTO sockDTO);
}
