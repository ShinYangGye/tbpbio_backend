package com.meta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.BrandFileEntity;
import com.meta.entity.ProductEntity;
import com.meta.entity.ProductFileEntity;

public interface ProductFileRepository extends JpaRepository<ProductFileEntity, Long> {
	List<ProductFileEntity> findByProductIdAndKind(Long productId, String kind);
	
	void deleteByProductId(Long productId);
}
