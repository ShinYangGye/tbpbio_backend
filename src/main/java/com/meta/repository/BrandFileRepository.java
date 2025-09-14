package com.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.BrandFileEntity;

public interface BrandFileRepository extends JpaRepository<BrandFileEntity, Long> {
	
}
