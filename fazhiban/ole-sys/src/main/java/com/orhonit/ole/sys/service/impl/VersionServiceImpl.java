package com.orhonit.ole.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.orhonit.ole.sys.dao.VersionDao;
import com.orhonit.ole.sys.dto.VersionDTO;
import com.orhonit.ole.sys.service.VersionService;

@Service
public class VersionServiceImpl implements VersionService {
	
	@Value("${files.versionPath}")
	private String path;
	
	@Value("${upload.serverUrl}")
	private String server;

	@Autowired
	VersionDao versionDao;
	
	@Override
	public int getCount(Map<String, Object> params) {
		return versionDao.count(params);
	}

	@Override
	public List<VersionDTO> getList(Map<String, Object> params, Integer start, Integer length) {
		return versionDao.list(params,start,length);
	}

	@Override
	public void save(VersionDTO versionDTO) {
		versionDao.updateAll();
		versionDao.save(versionDTO);
	}

	@Override
	public void reset(Map<String, Object> params) {
		versionDao.updateAll();
		versionDao.updateOne(params);
	}

	@Override
	public Map<String, Object> getNewVersion() {
		VersionDTO versionDTO = versionDao.getNewVersion();
		Map<String, Object> res = new HashMap<>();
		res.put("versionCode", versionDTO.getVersion_code());
		res.put("versionName", versionDTO.getVersion_name());
		res.put("content", versionDTO.getContent());
		res.put("minSupport", versionDTO.getMin_support());
		res.put("url", getVersionUrl(versionDTO.getId()));
		return res;
	}
	
	@Override
	public String getPath(){
		return this.path;
	}

	@Override
	public String getVersionUrl(int id) {
		VersionDTO versionDTO = versionDao.getVersion(id);
		return server+"file/appVerson"+"/"+versionDTO.getVersion_code()+"/"+versionDTO.getApk_name();
	}

}
