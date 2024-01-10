package com.hcmut.travogue.repository;

import com.hcmut.travogue.model.entity.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM _user AS u " +
            "WHERE CONCAT(u.email, ' ', u.first_name, ' ', u.last_name) " +
            "LIKE %:keyword%", nativeQuery = true)
    Page<User> findPageUsers(@Param("keyword") String keyword, Pageable pageable);
}
