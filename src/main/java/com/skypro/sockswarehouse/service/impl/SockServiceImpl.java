package com.skypro.sockswarehouse.service.impl;

import com.querydsl.core.types.Predicate;
import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import com.skypro.sockswarehouse.exception.QuantityNotEnoughException;
import com.skypro.sockswarehouse.loger.FormLogInfo;
import com.skypro.sockswarehouse.mapper.SockMapper;
import com.skypro.sockswarehouse.repository.SockRepository;
import com.skypro.sockswarehouse.service.SockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Сервис склада
 */

@Service
@Slf4j
public class SockServiceImpl implements SockService {

  private final SockRepository sockRepository;

  private final SockMapper sockMapper;

  public SockServiceImpl(SockRepository sockRepository, SockMapper sockMapper) {
    this.sockRepository = sockRepository;
    this.sockMapper = sockMapper;
  }

  /**
   * Поступление носков на склад
   *
   * @param sockDTO
   * @return
   */
  @Override
  public SockDTO incomeSocks(SockDTO sockDTO) {
    log.info(FormLogInfo.getInfo());
    sockRepository.save(sockMapper.toEntity(sockDTO));
    return sockDTO;
  }

  /**
   * Отгрузка носков со склада
   *
   * @param color
   * @param cottonPart
   * @param quantity
   * @throws QuantityNotEnoughException
   */
  @Override
  @Transactional
  public void outcomeSocks(String color, Integer cottonPart, Integer quantity)
      throws QuantityNotEnoughException {
    log.info(FormLogInfo.getInfo());
    Collection<Sock> socks = sockRepository.findByColorAndCottonPart(color, cottonPart); // Находим общее количество имеющееся на складе по заданым параметрам
    Integer quantityStock = socks.stream().mapToInt(Sock::getQuantity).sum();
    if (quantityStock > quantity) { //сравниваем количество в запросе на отгрузку и имеющееся на складе
      log.info("В наличие на складе находится {} пар носков", quantity);
      Integer result = quantityStock - quantity;
      sockRepository.deleteAllByColorAndCottonPart(color, cottonPart);// удаляем партии со склада для отгрузки
      Sock sock = new Sock();
      sock.setColor(color);
      sock.setQuantity(result);
      sock.setCottonPart(cottonPart);
      sockRepository.save(sock); // сохраняем остатки после отгрузки
      log.info("Остаток на складе после отгрузки составляет {} пар", result);
    } else if (quantityStock.equals(quantity)) {
      log.info("В наличие на складе находится {} пар носков", quantity);
      Integer result = quantityStock - quantity;
      sockRepository.deleteAllByColorAndCottonPart(color, cottonPart);
      log.info("Остаток на складе после отгрузки составляет {} пар", result);
    } else {
      log.info(FormLogInfo.getInfo());
      throw new QuantityNotEnoughException("Недостаточно товара на складе для отгрузки");
    }

  }

  @Override
  public Page<SockDTO> getAll(Predicate predicate, Pageable pageable) {
    Page<Sock> entities = sockRepository.findAll(predicate, pageable);
    return entities.map(sockMapper::toDTO);
  }

}
