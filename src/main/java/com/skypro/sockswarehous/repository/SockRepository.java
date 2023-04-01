package com.skypro.sockswarehous.repository;

import com.skypro.sockswarehous.entity.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * репозиторий для носков
 */
@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {

    Collection<Sock> findByColorAndCottonPart(String color, Integer cottonPart);

    void deleteAllByColorAndCottonPart(String color, Integer cottonPart);

    Collection<Sock> findByColorAndCottonPartGreaterThan(String color, Integer cottonPart);
    Collection<Sock> findByColorAndCottonPartLessThan(String color, Integer cottonPart);
    Collection<Sock> findByColorAndCottonPartEquals(String color, Integer cottonPart);






}
