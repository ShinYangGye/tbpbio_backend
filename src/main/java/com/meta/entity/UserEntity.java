package com.meta.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tbl_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="email", unique=true, nullable = false, length=50)
    private String email;
    
    @Column(name="password", nullable = false, length=150)
    private String password;
    
    @Column(name="name", nullable = false, length=50)
    private String name;
    
    @Column(name = "role", nullable = false, length = 50)
    private String role;
	
	@CreationTimestamp
	@Column(name="reg_at", nullable=false)
	private Timestamp regAt;
	
	
}
