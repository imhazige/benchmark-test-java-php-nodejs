package com.kazge.example.repository;

import com.kazge.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
	@Query("select u from User u where u.name = :name")
	Page<User> findBynameP(@Param("name") String name, Pageable pagenable);
	
	@Query("select u from User u where u.name = :name")
	List<User> findByname(@Param("name") String name);

	@Query("select u from User u where u.name = :name")
	User getByname(@Param("name") String name);

	@Query("select count(*) from User u where u.name = :name")
	int countByname(@Param("name") String name);
	
	@Query(value = "SELECT * FROM t_users WHERE name = :name limit 1", nativeQuery = true)
	User findBynameNative(@Param("name") String name);
	
	@Query(value = "DELETE * FROM t_users WHERE name = :name limit 1", nativeQuery = true)
	long deleteAllNative(@Param("name") String name);
}
