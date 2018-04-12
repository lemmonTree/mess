package com.belling.admin.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.alibaba.fastjson.annotation.JSONField;
import com.belling.base.model.PersistentObject;

/**
 * 登陆日志对应实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginLog extends PersistentObject {

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 登陆时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Timestamp loginTime;
	
	/**
	 * 登陆ip
	 */
	private String loginIp;
	
	/**
	 * ip信息：国家
	 */
	private String ipInfoCountry;
	
	/**
	 * ip信息：省份
	 */
	private String ipInfoRegion;
	
	/**
	 * ip信息：城市
	 */
	private String ipInfoCity;
	
	/**
	 * ip信息：运营商
	 */
	private String ipInfoIsp;
	
	/**
	 * 登录来源方式，1：web,2:android
	 */
	private Short loginType;
	
	/**
	 * 日志 备注
	 */
	private String loginDesc;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getIpInfoCountry() {
		return ipInfoCountry;
	}

	public void setIpInfoCountry(String ipInfoCountry) {
		this.ipInfoCountry = ipInfoCountry;
	}

	public String getIpInfoRegion() {
		return ipInfoRegion;
	}

	public void setIpInfoRegion(String ipInfoRegion) {
		this.ipInfoRegion = ipInfoRegion;
	}

	public String getIpInfoCity() {
		return ipInfoCity;
	}

	public void setIpInfoCity(String ipInfoCity) {
		this.ipInfoCity = ipInfoCity;
	}

	public String getIpInfoIsp() {
		return ipInfoIsp;
	}

	public void setIpInfoIsp(String ipInfoIsp) {
		this.ipInfoIsp = ipInfoIsp;
	}

	public Short getLoginType() {
		return loginType;
	}

	public void setLoginType(Short loginType) {
		this.loginType = loginType;
	}

	public String getLoginDesc() {
		return loginDesc;
	}

	public void setLoginDesc(String loginDesc) {
		this.loginDesc = loginDesc;
	}
}