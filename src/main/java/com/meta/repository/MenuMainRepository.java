package com.meta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.MenuMainEntity;

public interface MenuMainRepository extends JpaRepository<MenuMainEntity, Long> {
	
	List<MenuMainEntity> findByOrderBySortAscIdAsc();
	
}
