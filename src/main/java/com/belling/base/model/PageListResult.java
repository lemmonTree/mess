/**
 * 
 */
package com.belling.base.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <pre>
 * Description	Layui分页响应业务对象
 * Copyright:	Copyright (c)2016
 * Company:		Sunny
 * Author:		Administrator
 * Version: 	1.0
 * Create at:	2016年3月29日 上午10:46:11  
 *  
 * Modification History:  
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Builder
@ToString
@Data
public class PageListResult implements Serializable {

	/**
	 * 序列化ID
	 */
	private long code; //  数据code
	private long count; // 数量
	private Object data; // The data we should display on the page
	private String msg;
	
	/**
	 * 构建数据
	 * 
	 * @param code
	 * @return
	 */
	public static PageListResult createResult(long code,Object data, long rowCount,String msg ) {
		PageListResult result = PageListResult.builder().code(code).msg(msg)
				.data(data)
				.count(rowCount)
				.build();
		return result;
	}
}
