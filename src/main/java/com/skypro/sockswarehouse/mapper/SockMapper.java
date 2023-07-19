package com.skypro.sockswarehouse.mapper;


import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import org.mapstruct.Mapper;

/**
 * Маппер
 */

@Mapper
public interface SockMapper {

    Sock toEntity(SockDTO sockDTO);


    SockDTO toDTO(Sock sock);

}
