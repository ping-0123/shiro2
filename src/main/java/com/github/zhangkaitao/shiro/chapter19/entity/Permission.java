package com.github.zhangkaitao.shiro.chapter19.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_permission")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Permission extends BaseEntity{
	private static final long serialVersionUID = 7525405111409749914L;
	
	@Column(length=128)
	private String name;
	
	@Column(length=128)
	private String description;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="sys_role_permission",
			joinColumns=@JoinColumn(name="permission_id", foreignKey=@ForeignKey(name="fk_rolePermission_permission_id")),
			inverseJoinColumns=@JoinColumn(name="role_id", foreignKey=@ForeignKey(name="fk_rolePermission_role_id")))
	private List<Role> roles = new ArrayList<>();
	
	@OneToOne(mappedBy="permission", fetch=FetchType.LAZY)
	private Resource resource;
	
	public Permission(){}
	public Permission(String name, String desc){
		this.name = name;
		this.description  = desc;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	
}
