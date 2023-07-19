package com.skypro.sockswarehouse.repository;

import com.skypro.sockswarehouse.entity.Sock;
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

    Collection<Sock> findByColorAndCottonPartGreaterThanEqual(String color, Integer cottonPart);
    Collection<Sock> findByColorAndCottonPartLessThanEqual(String color, Integer cottonPart);
    Collection<Sock> findByColorAndCottonPartEquals(String color, Integer cottonPart);






}
