package com.github.zhangkaitao.shiro.chapter19.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseEntity {
	private static final long serialVersionUID = 8453032007727952230L;
	
	@Column(length=32)
	private String name;
	
	@Column(length=128)
	private String description;
	
	@ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinTable(name="sys_role_resource", 
		joinColumns=@JoinColumn(name="role_id", foreignKey=@ForeignKey(name="fk_roleResource_role_id")), 
		inverseJoinColumns=@JoinColumn(name="resource_id", foreignKey=@ForeignKey(name="fk_roleResource_resource_id")))
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<Resource> resources = new ArrayList<>();
	
	private Boolean available=Boolean.TRUE;
	
	@ManyToMany
	@JoinTable(name="sys_user_role",
		joinColumns={@JoinColumn(name="role_id")},
		inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<User> users = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	  
	public String getDescription() {
		return description;
	}

	public Boolean getAvailable() {
		return available;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	public Set<? extends String> getPermissions() {
		Set<String> permissions = new HashSet<>();
		if(resources !=null && resources.size()> 0)
			for (Resource resource : resources) {
				permissions.add(resource.getPermission());
			}
		return permissions;
	}  
}
