package com.skypro.sockswarehous.service.impl;

import com.skypro.sockswarehous.dto.SockDTO;
import com.skypro.sockswarehous.entity.Sock;
import com.skypro.sockswarehous.exception.QuantityNotEnoughException;
import com.skypro.sockswarehous.mapper.SockMapper;
import com.skypro.sockswarehous.repository.SockRepository;
import com.skypro.sockswarehous.service.SockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Сервис склада
 */

@Service
@Slf4j
public class SockServiceImpl implements SockService {

    private SockRepository sockRepository;

    private SockMapper sockMapper;

    public SockServiceImpl(SockRepository sockRepository, SockMapper sockMapper) {
        this.sockRepository = sockRepository;
        this.sockMapper = sockMapper;
    }

    /**
     * Поступление носков на склад
     * @param sockDTO
     * @return
     */
    @Override
    public SockDTO incomeSocks(SockDTO sockDTO) {
        log.info("Приход партии носков на склад в количестве {} пар", sockDTO.getQuantity());
        sockRepository.save(sockMapper.toEntity(sockDTO));
        return sockDTO;
    }

    /**
     * Отгрузка носков со склада
     * @param color
     * @param cottonPart
     * @param quantity
     * @throws QuantityNotEnoughException
     */
    @Override
    @Transactional
    public void outcomeSocks(String color, Integer cottonPart, Integer quantity) throws QuantityNotEnoughException {
        log.info("Отгрузка партии носков со склада в количестве {} пар", quantity);
        Collection<Sock> socks = sockRepository.findByColorAndCottonPart(color, cottonPart); // Находим общее количество имеющееся на складе по заданым параметрам
        Integer quantityStock = socks.stream().mapToInt(Sock::getQuantity).sum();
        if (quantityStock > quantity) { //сравниваем количество в запросе на отгрузку и имеющееся на складе
            log.info("В наличие на складе находится {} пар носков", quantity);
            Integer result = quantityStock - quantity;
            sockRepository.deleteAllByColorAndCottonPart(color, cottonPart);// удаляем партии со склада
            Sock sock = new Sock();
            sock.setColor(color);
            sock.setQuantity(result);
            sock.setCottonPart(cottonPart);
            sockRepository.save(sock); // сохраняем остатки после отгрузки
            log.info("Остаток на складе после отгрузки составляет {} пар", result);
        } else {
            log.error("Недостаточно товара на складе для отгрузки");
            throw new QuantityNotEnoughException();
        }

    }

    /**
     * Данные о количестве товара на складе
     * @param color
     * @param cottonPart
     * @param operation
     * @return
     */
    @Override
    public Integer getSocks(String color, Integer cottonPart, String operation) {
        log.info("Выборка товара в соответствии с заданными параметрами");
        switch (operation) {
            case "moreThan": {
                Collection<Sock> socks = sockRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
                return socks.stream().mapToInt(Sock::getQuantity).sum();
            }
            case "lessThan": {
                Collection<Sock> socks = sockRepository.findByColorAndCottonPartLessThan(color, cottonPart);
                return socks.stream().mapToInt(Sock::getQuantity).sum();
            }
            case "equal": {
                Collection<Sock> socks = sockRepository.findByColorAndCottonPartEquals(color, cottonPart);
                return socks.stream().mapToInt(Sock::getQuantity).sum();
            }
            default:
                throw new RuntimeException("Товара соответствуещего заданным параметрам нет на складе");
        }

    }

}
