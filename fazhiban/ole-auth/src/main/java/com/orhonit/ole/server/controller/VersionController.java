package com.orhonit.ole.server.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.QrCodeUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.dto.VersionDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.VersionService;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * app版本管理相关
 * 
 * @author wuyz
 *
 */
@RestController
@RequestMapping("/version")
public class VersionController {
	
//	@Value("${files.versionPath}")
//	private String filesPath;
	
	@Autowired
	VersionService versionService;

	/**
	 * 版本列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<VersionDTO> listCase(TableRequest request) {
		return TableRequestHandler.<VersionDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return versionService.getCount(request.getParams());
			}
		}).listHandler(new ListHandler<VersionDTO>() {

			@Override
			public List<VersionDTO> list(TableRequest request) {
				List<VersionDTO> list = versionService.getList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	
	/**
	 * 上传APK
	 * 
	 * @param file
	 * @param request
	 */

	@PostMapping(value = "/save")
	public Result<Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		User user = UserUtil.getCurrentUser();
		VersionDTO versionDTO = new VersionDTO();
		versionDTO.setApk_name(file.getOriginalFilename());
		versionDTO.setCreate_by(user.getUsername());
		versionDTO.setCreate_date(new Date());
		versionDTO.setCreate_name(user.getNickname());
		versionDTO.setVersion_code(Integer.parseInt(request.getParameter("versionCode")));
		versionDTO.setVersion_name(request.getParameter("versionName"));
		versionDTO.setContent(request.getParameter("content"));
		versionDTO.setMin_support(Integer.parseInt(request.getParameter("minSupport")));
		try {
			// 创建目录
			File dir = new File(versionService.getPath() + "/" + versionDTO.getVersion_code());
			dir.mkdirs();

			// 获取文件输入流
			InputStream in = file.getInputStream();

			// 创建文件并保存
			File f = new File(versionService.getPath() + "/" + versionDTO.getVersion_code() + "/" + versionDTO.getApk_name());
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// 保存上传信息到数据库
		try{
			versionService.save(versionDTO);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "上传成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR, "上传失败");
	}
	
	/**
	 * 重新指定某版本为最新发行版
	 * @param id
	 * @return
	 */
	@GetMapping("/reset")
	public Result<Object> reSet(@RequestParam("id")int id){
		Map<String, Object> params = new HashMap<String, Object>();
		User user = UserUtil.getCurrentUser();
		params.put("id", id);
		params.put("update_date", new Date());
		params.put("update_by", user.getUsername());
		params.put("update_name", user.getNickname());
		try{
			versionService.reset(params);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR, "失败");
	}
	
	@GetMapping("/getQrCode")
	public void getQrCode(HttpServletResponse response,@RequestParam("id")int id){
		String url = this.versionService.getVersionUrl(id);
		try {
			QrCodeUtil.createQrcode(url , response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/getDownLoadUrl")
	public Result<Object> getDownLoadUrl(@RequestParam("id")int id){
		String url = this.versionService.getVersionUrl(id);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, url);
	}
}
