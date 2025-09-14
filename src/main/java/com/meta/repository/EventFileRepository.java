package com.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.EventFileEntity;

public interface EventFileRepository extends JpaRepository<EventFileEntity, Long> {
	
}
