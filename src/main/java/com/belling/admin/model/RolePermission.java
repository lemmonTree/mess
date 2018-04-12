package com.belling.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.belling.base.model.PersistentObject;


/**
 * 角色权限映射
 * 
 * @author Sunny
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RolePermission extends PersistentObject {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2817362249993235590L;

	/** 应用ID */

	private Integer roleId;
	
	/** 权限ID */

	private Integer permissionId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
}
