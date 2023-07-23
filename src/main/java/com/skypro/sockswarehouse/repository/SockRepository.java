package com.skypro.sockswarehouse.repository;

import com.skypro.sockswarehouse.entity.Sock;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * репозиторий для носков
 */
@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {

  Collection<Sock> findByColorAndCottonPart(String color, Integer cottonPart);

  void deleteAllByColorAndCottonPart(String color, Integer cottonPart);

  @Query(nativeQuery = true, value = "SELECT * FROM socks s WHERE s.color like ('%' || :word || '%') OR s.cotton_part == ('%' || :word || '%') OR  s.quantity == ('%' || :word || '%')")
  List<Sock> findSocksByKeyWord(@Param("word") String word);

  List<Sock> findSocksByColor(String color);


}
