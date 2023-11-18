package com.skypro.sockswarehouse.mapper;


import com.skypro.sockswarehouse.dto.SockAddDTO;
import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер
 */

@Mapper
public interface SockMapper {

    Sock toEntity(SockDTO sockDTO);

    @Mapping(target = "id", ignore = true)
    Sock toEntity(SockAddDTO sockAddDTO);


    SockDTO toDTO(Sock sock);

}
