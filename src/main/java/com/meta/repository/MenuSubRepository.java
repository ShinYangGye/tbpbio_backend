package com.meta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.MenuSubEntity;

public interface MenuSubRepository extends JpaRepository<MenuSubEntity, Long> {
	
	List<MenuSubEntity> findByOrderByIdDesc();
	void deleteByMenuMainId(Long id);
	
}
