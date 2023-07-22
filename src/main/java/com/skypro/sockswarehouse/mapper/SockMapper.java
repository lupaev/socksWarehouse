package com.skypro.sockswarehouse.mapper;


import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер
 */

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SockMapper {

  Sock toEntity(SockDTO sockDTO);


  SockDTO toDTO(Sock sock);

  void updateEntity(SockDTO sockDTO, @MappingTarget Sock sock);

}
