package com.meta.config.principal;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.meta.entity.UserEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Getter
public class PrincipalDetails implements UserDetails {
	

	
	private UserEntity user;
	
	public PrincipalDetails (UserEntity user) {
		this.user = user;
	}
	
	/*
	public UserEntity getUserEntity() {
		return userEntity;
	}
	*/
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       
		/*
		List<GrantedAuthority> authorities = new ArrayList<>();		  
		for (RolesDto role : user.getRoles()) {		    
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
		*/
		/*
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				return user.getRole();
			}
			
		});		
		
		return collect;		
		*/
		log.info("======================= user.getRole() == {}", user.getRole());
	    ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
	    authList.add(new SimpleGrantedAuthority(user.getRole()));
	    return authList;
    }

	@Override
	public String getPassword() {
		log.info("user.getPassword()============== {}", user.getPassword());
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// return EnumUserStatus.BLOCK_LOGIN.equals(user.getStatus()) ? false : true;
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// return "REMOVE".equals(user.getStatus()) ? false : true;
		return true;
	}

}
