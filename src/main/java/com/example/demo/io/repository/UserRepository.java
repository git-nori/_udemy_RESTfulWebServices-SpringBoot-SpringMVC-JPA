package com.example.demo.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{
    public UserEntity findByEmail(String email);

    public UserEntity findByUserId(String userId);
}
