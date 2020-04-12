package com.orhonit.ole.common.utils;

import java.util.List;

import com.orhonit.ole.common.constants.CommonParameters;

/**
 * 实时预警根据发送人判断星级
 * @author liuzhih
 *
 */
public class RoleUtil {
	
	/**
	 *实时预警根据发送人角色判断星级
	 */
	public static String getStarByRoles(List<String> roles){
		String star=CommonParameters.WarnStar.ONE;
		if(roles!=null&&roles.size()>0){
			if(roles.contains(CommonParameters.Role.SLD.toString())){
				star=CommonParameters.WarnStar.FIVE;
				return star;
			}else if(roles.contains(CommonParameters.Role.SFZBLD.toString())){	
				star=CommonParameters.WarnStar.FOUR;
				return star;
			}else if(roles.contains(CommonParameters.Role.SFZBJDRY.toString())){
				star=CommonParameters.WarnStar.THREE;
				return star;
			}else if(roles.contains(CommonParameters.Role.APPROVE.toString())){
				star=CommonParameters.WarnStar.TWO;
				return star;
			}else if(roles.contains(CommonParameters.Role.OPINION.toString())){
				star=CommonParameters.WarnStar.TWO;
				return star;
			}else if(roles.contains(CommonParameters.Role.WBJJDRY.toString())){
				star=CommonParameters.WarnStar.ONE;
				return star;
			}
		}
		return star;
	}

}

