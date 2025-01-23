package com.doublevpartners.tutickets.repository;

import com.doublevpartners.tutickets.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
