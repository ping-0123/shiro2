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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="sys_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity{
	private static final long serialVersionUID = -6757432250580429608L;
	
	@Column(length=32)
	@Size(min=4, max=32, message="用户名需4-32位字符")
	private String username;
	
	@NotEmpty(message="密码不为空")
	@Column(length=128)
	private String password;
	
	@Column(length=128)
	private String salt;
	
	@Column(length=128, name="role_ids")
	private String roleIds;
	
	private Boolean locked=Boolean.FALSE;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST})
	@JoinColumn(foreignKey=@ForeignKey(name="fk_user_organization_id"))
	private Organization organization;
	
	
	@ManyToMany
	@JoinTable(name="sys_user_role", 
		joinColumns={@JoinColumn(name="user_id", foreignKey =@ForeignKey(name="fk_userRole_user_id"))},
		inverseJoinColumns={@JoinColumn(name="role_id", foreignKey=@ForeignKey(name="fk_userRole_role_id"))})
	private List<Role> roles = new ArrayList<>();
	
	
	
	 @Transient  
    public Set<String> getRoleNames(){  
        List<Role> roles=getRoles();  
        Set<String> set=new HashSet<String>();  
        for (Role role : roles) {  
            set.add(role.getName());  
        }  
        return set;  
    }



	public String getUsername() {
		return username;
	}



	public String getPassword() {
		return password;
	}



	public String getSalt() {
		return salt;
	}



	public String getRoleIds() {
		return roleIds;
	}



	public Boolean getLocked() {
		return locked;
	}



	public Organization getOrganization() {
		return organization;
	}



	public List<Role> getRoles() {
		return roles;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public void setSalt(String salt) {
		this.salt = salt;
	}



	public void setRoleIdds(String role_ids) {
		this.roleIds = role_ids;
	}



	public void setLocked(Boolean locked) {
		this.locked = locked;
	}



	public void setOrganization(Organization organization) {
		this.organization = organization;
	}



	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}



	public String getCredentialsSalt() {
		return username + salt;
	}  
	
	 
	
}
