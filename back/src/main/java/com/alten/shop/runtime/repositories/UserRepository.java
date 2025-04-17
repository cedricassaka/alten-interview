package com.alten.shop.runtime.repositories;

import com.alten.shop.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE (u.email = :param OR u.username = :param)")
    Optional<User> findFirstByEmailOrUsername(@Param("param") String parameter);
}
