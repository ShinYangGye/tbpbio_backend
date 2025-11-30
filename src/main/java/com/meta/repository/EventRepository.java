package com.meta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
	
	List<EventEntity> findByOrderByIdDesc();
	
	List<EventEntity> findTop3ByOrderByIdDesc();
	
	List<EventEntity> findByStatusIsOrStatusIsOrderByIdDesc(String statusI, String statusE);
	
	List<EventEntity> findByStatusIsOrderByIdDesc(String status);
	
}
