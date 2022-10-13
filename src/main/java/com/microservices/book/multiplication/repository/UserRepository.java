package com.microservices.book.multiplication.repository;

import com.microservices.book.multiplication.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);

    List<User> findAllByIdIn(final List<Long> ids);
}
