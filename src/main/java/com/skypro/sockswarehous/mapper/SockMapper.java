package com.skypro.sockswarehous.mapper;


import com.skypro.sockswarehous.dto.SockDTO;
import com.skypro.sockswarehous.entity.Sock;
import org.mapstruct.Mapper;

@Mapper
public interface SockMapper {

    Sock toEntity(SockDTO sockDTO);


    SockDTO toDTO(Sock sock);

}
