package com.sendhrresume.repo;

import com.sendhrresume.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByEmail(String email, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isEmailSentToCompany = true WHERE u.id = :id")
    int markEmailAsSent(@Param("id") int id );
}

