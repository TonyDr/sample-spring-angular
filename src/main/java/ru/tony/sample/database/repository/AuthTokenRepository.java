package ru.tony.sample.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tony.sample.database.entity.AuthToken;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long>{

    AuthToken findByToken(String token);
}
