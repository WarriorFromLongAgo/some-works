package com.orhonit.ole.sys.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.orhonit.ole.sys.dao.AppBannerDao;
import com.orhonit.ole.sys.dto.AppBannerDTO;
import com.orhonit.ole.sys.service.AppBannerService;
@Service
public class AppBannerServiceImpl implements AppBannerService{

	@Value("${files.bannerPath}")
	private String path;
	
	@Value("${upload.serverUrl}")
	private String server;
	
	@Autowired
	private AppBannerDao appBannerDao;
		
	@Override
	public int getCount(Map<String, Object> params) {
		return this.appBannerDao.count(params);
	}

	@Override
	public List<AppBannerDTO> getList(Map<String, Object> params, Integer start, Integer length) {
		return this.appBannerDao.list(params, start, length);
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void save(AppBannerDTO appBannerDTO) {
		 this.appBannerDao.save(appBannerDTO);
	}
	
	@Override
	public void update(AppBannerDTO appBannerDTO) {
		 this.appBannerDao.update(appBannerDTO);
	}
	
	@Override
	public void delete(Integer id) {
		 this.appBannerDao.delete(id);
	}

	@Override
	public AppBannerDTO findOne(Integer id) {
		return appBannerDao.findOne(id);
	}

	@Override
	public List<AppBannerDTO> findByRoleId(Integer roleId) {
		return appBannerDao.findByRoleId(roleId);
	}
	
}
