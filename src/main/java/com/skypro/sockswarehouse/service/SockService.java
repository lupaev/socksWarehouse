package com.skypro.sockswarehouse.service;


import com.querydsl.core.types.Predicate;
import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.exception.QuantityNotEnoughException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис склада
 */
public interface SockService {

  /**
   * Поступление носков на склад
   *
   * @param sockDTO
   * @return
   */
  SockDTO incomeSocks(SockDTO sockDTO);

  /**
   * Отгрузка носков со склада
   *
   * @param color
   * @param cottonPart
   * @param quantity
   * @throws QuantityNotEnoughException
   */
  void outcomeSocks(String color, Integer cottonPart, Integer quantity)
      throws QuantityNotEnoughException;


  /**
   * Данные о количестве товара на складе
   *
   * @param predicate
   * @param pageable
   * @return
   */
  Page<SockDTO> getAll(Predicate predicate, Pageable pageable);
}
