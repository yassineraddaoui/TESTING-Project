package com.ala.book.role;

import com.ala.book.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(String name);


}
