package com.github.zhangkaitao.shiro.chapter19.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="sys_url_filter", uniqueConstraints=
		@UniqueConstraint(name="uk_urlFilter_url", columnNames={"url"}))
public class UrlFilter extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(length=32)
	private String name;
	
	@Column(length=128)
	private String url;

	@Column(length=128)
	private String roles;
	
	@Column(length=128)
	private String permissions;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getRoles() {
		return roles;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	
}
