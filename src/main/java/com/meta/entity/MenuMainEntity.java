package com.meta.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
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
@Table(name="tbl_menu_main")
public class MenuMainEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name="menu_name", nullable=false, length=200)
	private String menuName;
	
	@Column(name="menu_info", columnDefinition = "LONGTEXT")
	private String menuInfo;
	
	@Column(name="sort")
	private int sort;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	MenuMainFileEntity file;
	
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<ProductMenuTwoEntity> twoMenus = new ArrayList<>();
    
	@OneToMany(mappedBy="menuMainId", fetch=FetchType.EAGER)
	private List<MenuSubEntity> menuSub;
	
}
