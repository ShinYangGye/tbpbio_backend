package com.meta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.ProductMainEntity;

public interface ProductMainRepository extends JpaRepository<ProductMainEntity, Long> {
	
	List<ProductMainEntity> findByOrderBySortAscIdAsc();
	
}
