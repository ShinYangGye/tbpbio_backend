package com.meta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.entity.BannerEntity;


public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
	
	// 브랜드 전체 조회
	List<BannerEntity> findByOrderBySortAscIdDesc();
	
	// 
	List<BannerEntity> findByProductId(Long productId);
}
