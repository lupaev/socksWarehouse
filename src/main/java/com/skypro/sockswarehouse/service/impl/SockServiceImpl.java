package com.skypro.sockswarehouse.service.impl;

import com.querydsl.core.types.Predicate;
import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import com.skypro.sockswarehouse.exception.ElemNotFound;
import com.skypro.sockswarehouse.exception.QuantityNotEnoughException;
import com.skypro.sockswarehouse.loger.FormLogInfo;
import com.skypro.sockswarehouse.mapper.SockMapper;
import com.skypro.sockswarehouse.repository.SockRepository;
import com.skypro.sockswarehouse.service.SockService;
import java.util.ArrayList;
import java.util.List;
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
  public SockDTO add(SockDTO sockDTO) {
    log.info(FormLogInfo.getInfo());
    sockRepository.save(sockMapper.toEntity(sockDTO));
    return sockDTO;
  }

  /**
   * Отгрузка носков со склада
   *
   * @param sockDTO
   * @throws QuantityNotEnoughException
   */
  @Override
  @Transactional
  public void outcomeSocks(SockDTO sockDTO) throws QuantityNotEnoughException {
    log.info(FormLogInfo.getInfo());
    Collection<Sock> socks = sockRepository.findByColorAndCottonPart(sockDTO.getColor(),
        sockDTO.getCottonPart()); // Находим общее количество имеющееся на складе по заданым параметрам
    Integer quantityStock = socks.stream().mapToInt(Sock::getQuantity).sum();
    if (quantityStock
        > sockDTO.getQuantity()) { //сравниваем количество в запросе на отгрузку и имеющееся на складе
      log.info("В наличие на складе находится {} пар носков", quantityStock);
      Integer result = quantityStock - sockDTO.getQuantity();
      sockRepository.deleteAllByColorAndCottonPart(sockDTO.getColor(),
          sockDTO.getCottonPart());// удаляем партии со склада для отгрузки
      Sock sock = new Sock();
      sock.setColor(sockDTO.getColor());
      sock.setQuantity(result);
      sock.setCottonPart(sockDTO.getCottonPart());
      sockRepository.save(sock); // сохраняем остатки после отгрузки
      log.info("Остаток на складе после отгрузки составляет {} пар", result);
    } else if (quantityStock.equals(sockDTO.getQuantity())) {
      log.info("В наличие на складе находится {} пар носков", quantityStock);
      Integer result = quantityStock - sockDTO.getQuantity();
      sockRepository.deleteAllByColorAndCottonPart(sockDTO.getColor(), sockDTO.getCottonPart());
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

  @Override
  public List<Sock> findAll() {
    return new ArrayList<>(sockRepository.findAll());
  }

  @Override
  public SockDTO findById(Long id) {
    return sockMapper.toDTO(sockRepository.findById(id).orElseThrow(ElemNotFound::new));
  }

  @Override
  public void deleteById(Long id) {
    log.info(FormLogInfo.getInfo());
    sockRepository.deleteById(id);
  }

  @Override
  public void updateById(SockDTO sockDTO) {
    log.info(FormLogInfo.getInfo());
    Sock sock = sockRepository.findById(sockDTO.getId())
        .orElseThrow(() -> new ElemNotFound("партия не найдена"));
    sockMapper.updateEntity(sockDTO, sock);
    sockRepository.save(sock);
  }

  @Override
  public SockDTO updateById(SockDTO sockDTO, Long id) {
    log.info(FormLogInfo.getInfo());
    Sock sock = sockRepository.findById(id)
        .orElseThrow(() -> new ElemNotFound("партия не найдена"));
    sockMapper.updateEntity(sockDTO, sock);
    sockRepository.save(sock);
    return sockMapper.toDTO(sock);
  }

}
