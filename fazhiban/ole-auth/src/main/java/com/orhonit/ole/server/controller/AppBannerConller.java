package com.orhonit.ole.server.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.sys.dto.AppBannerDTO;
import com.orhonit.ole.sys.service.AppBannerService;


/**
 * app Banner图 管理相关
 * 
 * @author zhangjy
 *
 */

@RestController
@RequestMapping("/appBanner")
public class AppBannerConller {

	@Autowired
	private AppBannerService appBannerService;
	
	/**
	 * banner图列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<AppBannerDTO> listCase(TableRequest request) {
		return TableRequestHandler.<AppBannerDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return appBannerService.getCount(request.getParams());
			}
		}).listHandler(new ListHandler<AppBannerDTO>() {

			@Override
			public List<AppBannerDTO> list(TableRequest request) {
				List<AppBannerDTO> list = appBannerService.getList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/**
	 * 上传Banner图
	 * 
	 * @param file
	 * @param request
	 */

	@PostMapping(value = "/save")
	public Result<Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {	
		
		AppBannerDTO appBannerDTO = new AppBannerDTO();
		
		/*if(!StringUtils.isEmpty(request.getParameter("id"))){
			appBannerDTO.setId(Integer.valueOf(request.getParameter("id")));
		}*/
		
		appBannerDTO.setFile_name(file.getOriginalFilename());
		appBannerDTO.setContent(request.getParameter("content"));
		appBannerDTO.setRole_id(Integer.valueOf(request.getParameter("roleId")));
		appBannerDTO.setSort(Integer.valueOf(request.getParameter("sort")));
		appBannerDTO.setIf_show(request.getParameter("ifShow"));
		try {
			
			String url = appBannerService.getPath() + "/" + appBannerDTO.getRole_id() ;
			
			// 创建目录
			File dir = new File(url);
			dir.mkdirs();

			// 获取文件输入流
			InputStream in = file.getInputStream();

			// 创建文件并保存
			File f = new File(url+"/"+ appBannerDTO.getFile_name());
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			in.close();
			appBannerDTO.setUrl("/appBanner/"+appBannerDTO.getRole_id()+"/"+ appBannerDTO.getFile_name());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 保存上传信息到数据库
		try{
			/*if(StringUtils.isEmpty(appBannerDTO.getId())){*/
				appBannerDTO.setCreate_date(new Date());
				appBannerService.save(appBannerDTO);
			/*}else{
				appBannerDTO.setUpdate_date(new Date());
				appBannerService.update(appBannerDTO);
			}*/
			
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "上传成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR, "上传失败！");
	}
	
	/**
	 * 删除Banner图
	 * 
	 * @param file
	 * @param request
	 */
	@PostMapping(value = "/delete/{id}")
	public Result<Object> delete(@PathVariable Integer id) {
		if(!StringUtils.isEmpty(id)){
			try{
				appBannerService.delete(id);
				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "删除成功！");	
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else{
			return ResultUtil.toResponseWithData(ResultCode.ERROR, "参数为空！");
		}
		
		return ResultUtil.toResponseWithData(ResultCode.ERROR, "删除失败！");
	}
	
	/**
	 * 返显数据
	 * @param id
	 * @param request
	 */
	@PostMapping(value = "/findOne/{id}")
	public Result<Object> findOne(@PathVariable Integer id) {
		
		AppBannerDTO appBannerDTO = new AppBannerDTO();
		
		if(!StringUtils.isEmpty(id)){
			
			appBannerDTO = appBannerService.findOne(id);
	
		}else{
			
			return ResultUtil.toResponseWithData(ResultCode.ERROR, "参数错误！");
			
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, appBannerDTO);
	}
	
	/**
	 * 返显数据
	 * @param id
	 * @param request
	 */
	@PostMapping(value = "/update")
	public Result<Object> update(@RequestBody AppBannerDTO appBannerDTO) {
		try{
			appBannerDTO.setUpdate_date(new Date());	
			appBannerService.update(appBannerDTO);
		}catch(Exception e){
			e.printStackTrace();
			return ResultUtil.toResponseWithData(ResultCode.ERROR, "修改失败！");
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "修改成功！");
	}
	
}
