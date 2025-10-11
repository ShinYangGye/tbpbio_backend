package com.meta.entity;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="tbl_banner")
public class BannerEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name="banner_name", nullable=true, length=200)
	private String bannerName;
	
	@Column(name="product_id", nullable=false)
	private Long productId;
	
	@Column(name="menu_main_id", nullable=false)
	private Long menuMainId;
	
	@Column(name="menu_sub_id", nullable=false)
	private Long menuSubId;
	
	@Column(name="product_name", nullable=false, length=200)
	private String productName;
	
	@Column(name="type", nullable=false, length=10)
	private String type;
	
	@Column(name="sort", nullable=false)
	private int sort;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	BannerFileEntity file;
}
