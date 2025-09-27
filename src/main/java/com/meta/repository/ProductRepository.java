package com.meta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.BrandEntity;
import com.meta.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
	// 브랜드 전체 조회
	// List<BrandEntity> findByOrderByBrandNameAsc();
	
	// 브랜드 시작 알파벳으로 조회
	// List<BrandEntity> findByBrandNameStartsWithOrderByBrandNameAsc(String brand);
	
	
	// 전체조회
	List<ProductEntity> findByOrderByIdDesc();
	
	// 메인메뉴 상품 조회
	List<ProductEntity> findByMenuMainIdOrderByIdDesc(Long mainId);
	
	// 메인메뉴, 서브메뉴 상품 조회
	List<ProductEntity> findByMenuMainIdAndMenuSubIdOrderByIdDesc(Long mainId, Long subId);
}
