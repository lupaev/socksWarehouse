package com.skypro.sockswarehouse.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.skypro.sockswarehouse.entity.QSock;
import com.skypro.sockswarehouse.entity.Sock;
import java.util.Iterator;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * репозиторий для носков
 */
@Repository
public interface SockRepository extends JpaRepository<Sock, Long>, QuerydslPredicateExecutor<Sock>,
    QuerydslBinderCustomizer<QSock> {

  Collection<Sock> findByColorAndCottonPart(String color, Integer cottonPart);

  void deleteAllByColorAndCottonPart(String color, Integer cottonPart);

//  Collection<Sock> findByColorAndCottonPartGreaterThanEqual(String color, Integer cottonPart);

//  Collection<Sock> findByColorAndCottonPartLessThanEqual(String color, Integer cottonPart);

//  Collection<Sock> findByColorAndCottonPartEquals(String color, Integer cottonPart);


  @Override
  default void customize(QuerydslBindings bindings, QSock qSock) {

    bindings.bind(String.class)
        .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    bindings.bind(qSock.id).all((path, value) -> {
      Iterator<? extends Long> it = value.iterator();
      Long from = it.next();
      if (value.size() >= 2) {
        Long to = it.next();
        return Optional.of(path.between(from, to)); // between
      } else {
        return Optional.of(path.goe(from)); // greater or equal
      }
    });
    bindings.bind(qSock.cottonPart).all((path, value) -> {
      Iterator<? extends Integer> it = value.iterator();
      Integer from = it.next();
      if (value.size() >= 2) {
        Integer to = it.next();
        return Optional.of(path.between(from, to)); // between
      } else {
        return Optional.of(path.goe(from)); // greater or equal
      }
    });
    bindings.bind(qSock.quantity).all((path, value) -> {
      Iterator<? extends Integer> it = value.iterator();
      Integer from = it.next();
      if (value.size() >= 2) {
        Integer to = it.next();
        return Optional.of(path.between(from, to)); // between
      } else {
        return Optional.of(path.goe(from)); // greater or equal
      }
    });
  }


}
