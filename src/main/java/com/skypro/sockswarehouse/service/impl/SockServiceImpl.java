package com.skypro.sockswarehouse.service.impl;

import com.querydsl.core.types.Predicate;
import com.skypro.sockswarehouse.dto.SockAddDTO;
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
   */
  @Override
  public SockDTO addSocks(SockAddDTO sockAddDTO) {
    log.info(FormLogInfo.getInfo());
    Sock sock = sockRepository.save(sockMapper.toEntity(sockAddDTO));
    return sockMapper.toDTO(sock);
  }

  /**
   * Отгрузка носков со склада
   */
  @Override
  @Transactional
  public void out(SockAddDTO sockAddDTO) {
    log.info(FormLogInfo.getInfo());
    Collection<Sock> socks = sockRepository.findByColorAndCottonPart(sockAddDTO.getColor(), sockAddDTO.getCottonPart()); // Находим общее количество имеющееся на складе по заданным параметрам
    Integer quantityStock = socks.stream().mapToInt(Sock::getQuantity).sum();
    Integer quantity = sockAddDTO.getQuantity();
    if (quantityStock > quantity) { //сравниваем количество в запросе на отгрузку и имеющееся на складе
      log.info("В наличие на складе находится {} пар носков", quantity);
      Integer result = quantityStock - quantity;
      sockRepository.deleteAllByColorAndCottonPart(sockAddDTO.getColor(), sockAddDTO.getCottonPart());// удаляем партии со склада для отгрузки
      Sock sock = new Sock();
      sock.setColor(sockAddDTO.getColor());
      sock.setQuantity(result);
      sock.setCottonPart(sockAddDTO.getCottonPart());
      sockRepository.save(sock); // сохраняем остатки после отгрузки
      log.info("Остаток на складе после отгрузки составляет {} пар", result);
    } else if (quantityStock.equals(quantity)) {
      log.info("В наличие на складе находится {} пар носков", quantity);
      Integer result = 0;
      sockRepository.deleteAllByColorAndCottonPart(sockAddDTO.getColor(), sockAddDTO.getCottonPart());
      log.info("Остаток на складе после отгрузки составляет {} пар", result);
    } else {
      log.info(FormLogInfo.getInfo());
      throw new QuantityNotEnoughException();
    }
  }

  @Override
  public Page<SockDTO> getAll(Predicate predicate, Pageable pageable) {
    Page<Sock> entities = sockRepository.findAll(predicate, pageable);
    return entities.map(sockMapper::toDTO);
  }

}
