package com.meta.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name="tbl_product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name="product_name", nullable=false, length=500)
	private String productName;
	
	@Column(name="product_use", nullable=false, length=500)
	private String productUse;
	
	@Column(name="product_brand", nullable=false, length=500)
	private String productBrand;
	
	@Column(name="product_contents", columnDefinition = "LONGTEXT")
	private String productContents ;

	@Column(name="menu_main_id", nullable=false)
	private Long menuMainId;
	
	@Column(name="menu_sub_id", nullable=false)
	private Long menuSubId;
	
	@CreationTimestamp
	@Column(name="reg_at", nullable=false)
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")	
	private Date regAt;
	
	@OneToMany(mappedBy="productId", fetch=FetchType.EAGER)
	private List<ProductFileEntity> files;
}
