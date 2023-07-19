package com.skypro.sockswarehouse.service;

import com.skypro.sockswarehouse.dto.SockDTO;
import com.skypro.sockswarehouse.entity.ComparisonOperation;
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

  @Test
  void incomeSocksPositiveTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);
    SockMapper sockMapper = mock(SockMapper.class);

    when(sockMapper.toEntity(sockDTO)).thenReturn(sock);
    when(sockService.incomeSocks(sockDTO)).thenReturn(sockDTO);
    when(sockRepository.save(any(Sock.class))).thenReturn(sock);
    assertThat(sockService.incomeSocks(sockDTO)).isNotNull().isEqualTo(sockDTO)
        .isExactlyInstanceOf(SockDTO.class);
    assertThat(sockMapper.toEntity(sockDTO)).isNotNull().isEqualTo(sock)
        .isExactlyInstanceOf(Sock.class);
    assertThat(sockRepository.save(sock)).isNotNull().isExactlyInstanceOf(Sock.class);

    verify(sockRepository, times(1)).save(sock);
    verify(sockService, times(1)).incomeSocks(sockDTO);
    verify(sockMapper, times(1)).toEntity(sockDTO);

  }

  @Test
  void incomeSocksNegativeTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);
    SockMapper sockMapper = mock(SockMapper.class);

    when(sockMapper.toEntity(any())).thenThrow(NullPointerException.class);
    when(sockRepository.save(any())).thenThrow(RuntimeException.class);
    when(sockService.incomeSocks(any())).thenThrow(RuntimeException.class);
    assertThrows(NullPointerException.class, () -> sockMapper.toEntity(any()));
    assertThrows(RuntimeException.class, () -> sockRepository.save(any(Sock.class)));
    assertThatExceptionOfType(RuntimeException.class).isThrownBy(
        () -> sockService.incomeSocks(sockDTO));

    verify(sockRepository, times(1)).save(any());
    verify(sockService, times(1)).incomeSocks(any());
    verify(sockMapper, times(1)).toEntity(any());


  }

  @Test
  void outcomeSocksPositiveTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);

    Collection<Sock> socks = new ArrayList<Sock>(List.of(sock));

    when(sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart())).thenReturn(
        socks);
    doNothing().when(sockRepository)
        .deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
    when(sockRepository.save(any(Sock.class))).thenReturn(sock);
    lenient().doNothing().when(sockService)
        .outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity());

    assertThat(sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart()))
        .isNotNull().isEqualTo(socks).isExactlyInstanceOf(ArrayList.class);
    assertDoesNotThrow(
        () -> sockRepository.deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart()));
    assertThat(sockRepository.save(sock)).isNotNull().isExactlyInstanceOf(Sock.class);
    assertDoesNotThrow(
        () -> sockService.outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity()));

    verify(sockRepository, times(1)).save(sock);
    verify(sockService, times(1)).outcomeSocks(sock.getColor(), sock.getCottonPart(),
        sock.getQuantity());
    verify(sockRepository, times(1)).findByColorAndCottonPart(sock.getColor(),
        sock.getCottonPart());
    verify(sockRepository, times(1)).deleteAllByColorAndCottonPart(sock.getColor(),
        sock.getCottonPart());
  }

  @Test
  void outcomeSocksNegativeTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);

    doThrow(NullPointerException.class).when(sockRepository)
        .findByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
    doThrow(IllegalArgumentException.class).when(sockRepository).save(sock);
    doThrow(IllegalArgumentException.class).when(sockRepository)
        .deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
    doThrow(QuantityNotEnoughException.class).when(sockService)
        .outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity());

    assertThrows(NullPointerException.class,
        () -> sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart()));
    assertThrows(IllegalArgumentException.class, () -> sockRepository.save(sock));
    assertThrows(IllegalArgumentException.class,
        () -> sockRepository.deleteAllByColorAndCottonPart(sock.getColor(), sock.getCottonPart()));
    assertThrows(QuantityNotEnoughException.class,
        () -> sockService.outcomeSocks(sock.getColor(), sock.getCottonPart(), sock.getQuantity()));

    verify(sockRepository, times(1)).save(sock);
    verify(sockService, times(1)).outcomeSocks(sock.getColor(), sock.getCottonPart(),
        sock.getQuantity());
    verify(sockRepository, times(1)).findByColorAndCottonPart(sock.getColor(),
        sock.getCottonPart());
    verify(sockRepository, times(1)).deleteAllByColorAndCottonPart(sock.getColor(),
        sock.getCottonPart());
  }

  @Test
  void getSocksPositiveTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);
    Collection<Sock> socks = new ArrayList<Sock>(List.of(sock));

    when(sockRepository.findByColorAndCottonPartGreaterThanEqual(sock.getColor(),
        sock.getCottonPart())).thenReturn(socks);
    when(sockService.getSocks(sock.getColor(), sock.getCottonPart(), ComparisonOperation.GREATERTHAN)).thenReturn(
        sock.getQuantity());

    assertThat(sockRepository.findByColorAndCottonPartGreaterThanEqual(sock.getColor(),
        sock.getCottonPart()))
        .isNotNull().isEqualTo(socks).isExactlyInstanceOf(ArrayList.class);
    assertThat(sockService.getSocks(sock.getColor(), sock.getCottonPart(), ComparisonOperation.GREATERTHAN))
        .isNotNull().isEqualTo(sock.getQuantity()).isExactlyInstanceOf(Integer.class);
    assertDoesNotThrow(
        () -> sockService.getSocks(sock.getColor(), sock.getCottonPart(), ComparisonOperation.GREATERTHAN));

    verify(sockRepository, times(1)).findByColorAndCottonPartGreaterThanEqual(sock.getColor(),
        sock.getCottonPart());
    verify(sockService, times(2)).getSocks(sock.getColor(), sock.getCottonPart(), ComparisonOperation.GREATERTHAN);

  }

  @Test
  void getSocksNegativeTest() {
    SockServiceImpl sockService = mock(SockServiceImpl.class);

    doThrow(NullPointerException.class).when(sockRepository)
        .findByColorAndCottonPartGreaterThanEqual(sock.getColor(), sock.getCottonPart());
    doThrow(NullPointerException.class).when(sockService)
        .getSocks(sock.getColor(), sock.getCottonPart(), ComparisonOperation.GREATERTHAN);

    assertThrows(NullPointerException.class,
        () -> sockRepository.findByColorAndCottonPartGreaterThanEqual(sock.getColor(),
            sock.getCottonPart()));
    assertThrows(NullPointerException.class,
        () -> sockService.getSocks(sock.getColor(), sock.getCottonPart(), ComparisonOperation.GREATERTHAN));

    verify(sockRepository, times(1)).findByColorAndCottonPartGreaterThanEqual(sock.getColor(),
        sock.getCottonPart());
    verify(sockService, times(1)).getSocks(sock.getColor(), sock.getCottonPart(), ComparisonOperation.GREATERTHAN);

  }
}