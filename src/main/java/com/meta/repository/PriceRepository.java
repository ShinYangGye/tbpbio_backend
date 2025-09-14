package com.meta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.entity.PriceEntity;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
	
	/*
	 * 
	 *     
	@Query("SELECT u FROM User u WHERE u.username LIKE %:char% and u.age > :maxAge")
    List<User> findByLetterWithConditions(@Param("char") char letter,
                                          @Param("maxAge") int age);	
	List<PriceEntity> findByDisplayOrderByIdDesc(String display);
	*/
	
	// 견적의뢰 목록 조회
	/*
	String listSql = ""
			+ "select p.id, p.name, p.mobile, p.title, p.contents, p.reg_at as regAt"
			+ " from tbl_price p"
			+ " where p.display = :display"
			+ " order by p.id desc";
	@Query(value =listSql, nativeQuery = true)
	List<PriceResDtoInterface> findPriceList(@Param("display") String display);
	*/
	List<PriceEntity> findByDisplayOrderByIdDesc(String display);
	
}
