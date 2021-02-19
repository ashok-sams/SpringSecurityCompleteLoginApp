package com.SCM.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
