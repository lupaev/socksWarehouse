package com.skypro.sockswarehouse.service;


import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.ComparisonOperation;
import com.skypro.sockswarehouse.exception.QuantityNotEnoughException;

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
    Integer getSocks(String color, Integer cottonPart, ComparisonOperation operation);
}
