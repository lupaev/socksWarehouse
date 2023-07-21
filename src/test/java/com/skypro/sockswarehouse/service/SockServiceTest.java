package com.skypro.sockswarehouse.service;

import com.querydsl.core.types.Predicate;
import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.Sock;
import com.skypro.sockswarehouse.exception.QuantityNotEnoughException;
import com.skypro.sockswarehouse.mapper.SockMapper;
import com.skypro.sockswarehouse.repository.SockRepository;
import com.skypro.sockswarehouse.service.impl.SockServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SockServiceTest {

  @InjectMocks
  private SockServiceImpl sockService;
  @Mock
  private SockRepository sockRepository;
  @Spy
  private SockMapper sockMapper;

  private SockDTO sockDTO;
  private Sock sock;


  @BeforeEach
  void setUp() {
    sockDTO = new SockDTO(1L, "red", 20, 100);
    sock = new Sock(1L, "red", 20, 100);

  }

  @AfterEach
  void clearTestData() {
    sock = null;
    sockDTO = null;
  }

//  @Test
//  void incomeSocksPositiveTest() {
//    SockServiceImpl sockService = mock(SockServiceImpl.class);
//    SockMapper sockMapper = mock(SockMapper.class);
//
//    when(sockMapper.toEntity(sockDTO)).thenReturn(sock);
//    when(sockService.add(sockDTO)).thenReturn(sockDTO);
//    when(sockRepository.save(any(Sock.class))).thenReturn(sock);
//    assertThat(sockService.add(sockDTO)).isNotNull().isEqualTo(sockDTO)
//        .isExactlyInstanceOf(SockDTO.class);
//    assertThat(sockMapper.toEntity(sockDTO)).isNotNull().isEqualTo(sock)
//        .isExactlyInstanceOf(Sock.class);
//    assertThat(sockRepository.save(sock)).isNotNull().isExactlyInstanceOf(Sock.class);
//
//    verify(sockRepository, times(1)).save(sock);
//    verify(sockService, times(1)).add(sockDTO);
//    verify(sockMapper, times(1)).toEntity(sockDTO);
//
//  }
//
//  @Test
//  void incomeSocksNegativeTest() {
//    SockServiceImpl sockService = mock(SockServiceImpl.class);
//    SockMapper sockMapper = mock(SockMapper.class);
//
//    when(sockMapper.toEntity(any())).thenThrow(NullPointerException.class);
//    when(sockRepository.save(any())).thenThrow(RuntimeException.class);
//    when(sockService.add(any())).thenThrow(RuntimeException.class);
//    assertThrows(NullPointerException.class, () -> sockMapper.toEntity(any()));
//    assertThrows(RuntimeException.class, () -> sockRepository.save(any(Sock.class)));
//    assertThatExceptionOfType(RuntimeException.class).isThrownBy(
//        () -> sockService.add(sockDTO));
//
//    verify(sockRepository, times(1)).save(any());
//    verify(sockService, times(1)).add(any());
//    verify(sockMapper, times(1)).toEntity(any());
//
//
//  }
//
//  @Test
//  void outcomeSocksPositiveTest() {
//    SockServiceImpl sockService = mock(SockServiceImpl.class);
//
//    Collection<Sock> socks = new ArrayList<Sock>(List.of(sock));
//
//    when(sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart())).thenReturn(
//        socks);
//    doNothing().when(sockRepository)
//        .deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
//    when(sockRepository.save(any(Sock.class))).thenReturn(sock);
//    lenient().doNothing().when(sockService)
//        .outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity());
//
//    assertThat(sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart()))
//        .isNotNull().isEqualTo(socks).isExactlyInstanceOf(ArrayList.class);
//    assertDoesNotThrow(
//        () -> sockRepository.deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart()));
//    assertThat(sockRepository.save(sock)).isNotNull().isExactlyInstanceOf(Sock.class);
//    assertDoesNotThrow(
//        () -> sockService.outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity()));
//
//    verify(sockRepository, times(1)).save(sock);
//    verify(sockService, times(1)).outcomeSocks(sock.getColor(), sock.getCottonPart(),
//        sock.getQuantity());
//    verify(sockRepository, times(1)).findByColorAndCottonPart(sock.getColor(),
//        sock.getCottonPart());
//    verify(sockRepository, times(1)).deleteAllByColorAndCottonPart(sock.getColor(),
//        sock.getCottonPart());
//  }

//  @Test
//  void outcomeSocksNegativeTest() {
//    SockServiceImpl sockService = mock(SockServiceImpl.class);
//
//    doThrow(NullPointerException.class).when(sockRepository)
//        .findByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
//    doThrow(IllegalArgumentException.class).when(sockRepository).save(sock);
//    doThrow(IllegalArgumentException.class).when(sockRepository)
//        .deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
//    doThrow(QuantityNotEnoughException.class).when(sockService)
//        .outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity());
//
//    assertThrows(NullPointerException.class,
//        () -> sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart()));
//    assertThrows(IllegalArgumentException.class, () -> sockRepository.save(sock));
//    assertThrows(IllegalArgumentException.class,
//        () -> sockRepository.deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart()));
//    assertThrows(QuantityNotEnoughException.class,
//        () -> sockService.outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity()));
//
//    verify(sockRepository, times(1)).save(sock);
//    verify(sockService, times(1)).outcomeSocks(sock.getColor(), sock.getCottonPart(),
//        sock.getQuantity());
//    verify(sockRepository, times(1)).findByColorAndCottonPart(sock.getColor(),
//        sock.getCottonPart());
//    verify(sockRepository, times(1)).deleteAllByColorAndCottonPart(sock.getColor(),
//        sock.getCottonPart());
//  }

  @Test
  void getAllPositiveTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);
    List<Sock> socks = new ArrayList<>(List.of(sock));
    Predicate predicate = null;

    when(sockRepository.findAll()).thenReturn(socks);
    assertThat(sockRepository.findAll()).isNotNull().isEqualTo(socks)
        .isExactlyInstanceOf(ArrayList.class);
    assertDoesNotThrow(() -> sockService.getAll(predicate, Pageable.unpaged()));

    verify(sockRepository, times(1)).findAll();
    verify(sockService, times(1)).getAll(predicate, Pageable.unpaged());
  }

  @Test
  void getAllNegativeTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);
    Predicate predicate = null;

    doThrow(NullPointerException.class).when(sockRepository)
        .findAll();
    doThrow(NullPointerException.class).when(sockService)
        .getAll(predicate, Pageable.unpaged());

    assertThrows(NullPointerException.class,
        () -> sockRepository.findAll());
    assertThrows(NullPointerException.class,
        () -> sockService.getAll(predicate, Pageable.unpaged()));

    verify(sockRepository, times(1)).findAll();
    verify(sockService, times(1)).getAll(predicate, Pageable.unpaged());

  }
}