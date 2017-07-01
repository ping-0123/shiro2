package com.github.zhangkaitao.shiro.chapter19.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="sys_resource")
public class Resource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8689302420444286023L;
	
    public static enum ResourceType {
        MENU("菜单"), BUTTON("按钮");

        private final String info;
        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }


	@Column(length=32)
	private String name;
	
	@Column(length=32)
	@Enumerated(EnumType.STRING)
	private ResourceType type;
	
	@Column(length=32)
	private String url;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_resource_parent_id"))
	private Resource parent;
	
	@Column(length=32, name="parent_ids")
	private String parentIds;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(foreignKey=@ForeignKey(name="fk_resource_permission_id"))
	private Permission permission;
	
	private Boolean available = Boolean.TRUE;

	@OneToMany(mappedBy="parent")
	private List<Resource> childs = new ArrayList<>();
	
	public List<Resource> getChilds() {
		return childs;
	}

	public Resource(){}
	public Resource(String name, ResourceType type, Resource parent, Permission permission){
		this.name= name;
		this.type = type;
		this.parent = parent;
		this.permission = permission;
	}
	
	/**
	 * 
	 * @return 返回所有的子孙资源
	 */
	public List<Resource> getAllChilds(){
		List<Resource> resources = new ArrayList<>();
		if(childs.size()>0){
			for (Resource res : childs) {
				resources.addAll(res.getAllChilds());
			}
		}
		return resources;
	}
	
	public void setChilds(List<Resource> childs) {
		this.childs = childs;
	}

	public String getName() {
		return name;
	}

	public ResourceType getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public Resource getParent() {
		return parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public Permission getPermission() {
		return permission;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	public String getPermissionName(){
		return permission==null ? null: permission.getName();
	}
	
	  public boolean isRootNode() {
	        return parent == null;
	    }
	
}
