package com.ppdai.canalmate.api.entity.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
* 
* @author yanxd 
*/
public class BaseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5624246870594287531L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
