package com.skypro.sockswarehouse.service;


import com.querydsl.core.types.Predicate;
import com.skypro.sockswarehouse.dto.SockAddDTO;
import com.skypro.sockswarehouse.dto.SockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис склада
 */
public interface SockService {

  /**
   * Поступление носков на склад
   */
  SockDTO addSocks(SockAddDTO sockAddDTO);

  /**
   * Отгрузка носков со склада
   */
  void out(SockAddDTO sockAddDTO);

  /**
   * Данные о количестве товара на складе
   */
  Page<SockDTO> getAll(Predicate predicate, Pageable pageable);
}
