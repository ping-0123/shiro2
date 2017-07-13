package com.github.zhangkaitao.shiro.chapter19.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.zhangkaitao.shiro.application.UserContext;  
  
/**
 * name rule
 * foreign key:  			 fk_{table name}_{foreign key column name}
 * foreign key column name:  {parent table name}_{referenced column name}
 * unique key: 				 uk_{table name}_{unique key column name}
 */

@MappedSuperclass  
public abstract class BaseEntity implements Serializable{  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id  
    @Column(nullable = false)  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;  

	@Column(name="create_user_id", updatable=false)
    private Long createUserId;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  
    @Column(updatable = false, name="create_timestamp")  
    private Date createDate;  

    @Column(name="last_modified_user_id")
    private Long lastModifiedUserId;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
    @Column(insertable=true,updatable=true, name="last_modified_timestamp")
    protected Date lastModifiedDate;  
    
      
    public BaseEntity() {
	}


	public void init() {
		Long userId = 1L;
		if(UserContext.getUser() != null){
			userId= UserContext.getUser().getId();
		}
		Date date = new Date();
    	this.createDate = date;
    	this.lastModifiedDate=date;
    	this.createUserId=userId;
    	this.lastModifiedUserId=userId;
	}

	public void beforeUpdate(){
		Long userId = 1L;
		if(UserContext.getUser() != null){
			userId= UserContext.getUser().getId();
		}
		this.lastModifiedDate = new Date();
		this.lastModifiedUserId = userId;
	}
	
    public Long getId() {  
        return id;  
    }  
  
    public void setId(long id) {  
        this.id = id;  
    }  
  

    public Date getCreateDate() {  
        return createDate;  
    }  
  

    public void setCreateDate(Date createDate) {  
        this.createDate = createDate;  
    }  
  

    public Date getLastModifiedDate() {  
        return lastModifiedDate;  
    }  
  
    public void setLastModifiedDate(Date modifyDate) {  
        this.lastModifiedDate = modifyDate;  
    }  
  
    @Override  
    public int hashCode() {  
        return id == null ? System.identityHashCode(this) : id.hashCode();  
    }  
  
    @Override  
    public boolean equals(Object obj) {  
        if (this == obj) {  
            return true;  
        }  
        if (obj == null) {  
            return false;  
        }  
        if (getClass().getPackage() != obj.getClass().getPackage()) {  
            return false;  
        }  
        final BaseEntity other = (BaseEntity) obj;  
        if (id == null) {  
            if (other.getId() != null) {  
                return false;  
            }  
        } else if (!id.equals(other.getId())) {  
            return false;  
        }  
        return true;  
    }

	public  Long getCreateUserId() {
		return createUserId;
	}

	public  void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public  Long getLastModifiedUserId() {
		return lastModifiedUserId;
	}

	public  void setLastModifiedUserId(Long moddifiedUserId) {
		this.lastModifiedUserId = moddifiedUserId;
	}


}  