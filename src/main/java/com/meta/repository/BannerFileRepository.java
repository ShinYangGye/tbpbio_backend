package com.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.BannerFileEntity;

public interface BannerFileRepository extends JpaRepository<BannerFileEntity, Long> {
	
}
