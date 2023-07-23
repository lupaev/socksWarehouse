package com.skypro.sockswarehouse.service;


import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.exception.QuantityNotEnoughException;
import java.util.List;

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
  SockDTO add(SockDTO sockDTO);

  /**
   * Отгрузка носков со склада
   *
   * @param sockDTO
   * @throws QuantityNotEnoughException
   */
  void outcomeSocks(SockDTO sockDTO) throws QuantityNotEnoughException;

  List<SockDTO> findAllByKeyWord(String word);

  SockDTO findById(Long id);

  void deleteById(Long id);

  void updateById(SockDTO sockDTO);


}
