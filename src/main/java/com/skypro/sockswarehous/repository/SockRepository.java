package com.skypro.sockswarehous.repository;

import com.skypro.sockswarehous.entity.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * репозиторий для носков
 */
@Repository
public interface SockRepository extends JpaRepository<Sock, Integer> {

}
