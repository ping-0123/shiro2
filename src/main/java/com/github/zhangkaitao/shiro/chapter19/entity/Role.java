package com.github.zhangkaitao.shiro.chapter19.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Column(length=128)
	private String resourceIds;
	
	private Boolean available=Boolean.TRUE;
	
	@ManyToMany
	@JoinTable(name="sys_role_permission", 
			joinColumns=@JoinColumn(name="role_id"),
			inverseJoinColumns=@JoinColumn(name="permission_id"))
	private List<Permission> pemissions = new ArrayList<>();
	
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
	public List<Permission> getPemissions() {
		return pemissions;
	}
	public void setPemissions(List<Permission> pemissions) {
		this.pemissions = pemissions;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	  @Transient  
	    public List<String> getPermissionsName(){  
	        List<String> list=new ArrayList<String>();  
	        List<Permission> perlist=getPemissions();  
	        for (Permission per : perlist) {  
	            list.add(per.getName());  
	        }  
	        return list;  
	    }
	  
	public List<String> getResourceNames(){
		List<String> resNames = new ArrayList<>();
		if(this.pemissions.size()>0){
			for (Permission per : pemissions) {
				if(per.getResource() != null)
					resNames.add(per.getResource().getName());
			}
		}
		return resNames;
	}
	  
	public String getDescription() {
		return description;
	}
	public String getResourceIds() {
		return resourceIds;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}  
}
