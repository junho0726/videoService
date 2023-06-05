package com.video.service.repository.user;

import com.video.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer>, UserRepositoryCustom {


}
