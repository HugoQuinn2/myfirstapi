package com.hq.myfirtsapi.apiusers;

import com.hq.myfirtsapi.apiusers.UserApiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserApiRepository extends JpaRepository<UserApiModel, Long> {
    Optional<UserApiModel> findByUsername(String username);
}
