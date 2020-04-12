package com.orhonit.modules.generator.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.LearnalessonBookstoreEntity;


@Mapper
public interface LearnalessonBookstoreDao extends BaseMapper<LearnalessonBookstoreEntity>{
	
	//书苑  根据编号查询单条
	@Select("SELECT\r\n" + 
			"bookstore_id,\r\n" + 
			"bookstore_name,\r\n" + 
			"bookstore_type,\r\n" + 
			"bookstore_address,\r\n" + 
			"bookstore_pic,\r\n" + 
			"'describe',\r\n" + 
			"catalog,\r\n" + 
			"create_time,\r\n" + 
			"leadabook_integral,\r\n" + 
			"book_auther,\r\n" + 
			"readabook_number\r\n" + 
			"FROM\r\n" + 
			"learnalesson_bookstore\r\n" + 
			"WHERE \r\n" + 
			"bookstore_id=#{bookeStoreId}")
	LearnalessonBookstoreEntity selectStore(Integer bookeStoreId);
	
	//书苑 根据编号删除
	@Delete("DELETE FROM learnalesson_bookstore WHERE bookstore_id=#{bookestoreId}")
	void deleteStore(Integer bookestoreId);
	
	
	public boolean updateStore(LearnalessonBookstoreEntity entity);
}
