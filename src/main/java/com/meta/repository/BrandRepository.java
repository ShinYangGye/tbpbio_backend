package com.meta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.BrandEntity;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
	
	// 브랜드 전체 조회
	List<BrandEntity> findByOrderByBrandNameAsc();
	
	// 브랜드 시작 알파벳으로 조회
	List<BrandEntity> findByBrandNameStartsWithOrderByBrandNameAsc(String brand);
}
