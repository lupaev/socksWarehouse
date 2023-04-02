package com.skypro.sockswarehous.service;


import com.skypro.sockswarehous.dto.SockDTO;
import com.skypro.sockswarehous.exception.QuantityNotEnoughException;

/**
 * Сервис склада
 */
public interface SockService {

    /**
     * Поступление носков на склад
     * @param sockDTO
     * @return
     */
    SockDTO incomeSocks(SockDTO sockDTO);

    /**
     * Отгрузка носков со склада
     * @param color
     * @param cottonPart
     * @param quantity
     * @throws QuantityNotEnoughException
     */
    void outcomeSocks(String color, Integer cottonPart, Integer quantity) throws QuantityNotEnoughException;

    /**
     * Данные о количестве товара на складе
     * @param color
     * @param cottonPart
     * @param operation
     * @return
     */
    Integer getSocks(String color, Integer cottonPart, String operation);
}
