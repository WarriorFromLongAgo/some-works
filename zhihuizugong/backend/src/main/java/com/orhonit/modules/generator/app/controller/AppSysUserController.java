package com.orhonit.modules.generator.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserRoleService;
import com.orhonit.modules.sys.service.SysUserService;

/**
 * 系统用户
 * 
 * @author zjw
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/app/sys/user")
public class AppSysUserController{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired	
    private UploadConfig uploadConfig;


	/**
	 * 模糊搜索用户列表
	 * @author zy
	 * @serialData 2019年7月19日10:12:32
	 */
	@GetMapping("/searchList")
//	@RequiresPermissions("sys:user:list")
	public R searchList(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
//		if(getUserId() != Constant.SUPER_ADMIN){
//			params.put("createUserId", getUserId());
//		}
		PageUtils page = sysUserService.searchList(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 上传附件
	 * 
	 * @param file
	 * @param request
	 */
	@PostMapping(value = "/headPortraitUpload")
	public R uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		System.out.println(request.getParameter("userNickname"));

		// 文件类型
		String[] temp = file.getOriginalFilename().split("\\.");
		String fileType = temp[temp.length - 1];
		// 原文件名
		// 新文件名
		String fileLocalName = request.getParameter("userNickname"); //新文件名 张元加
		//上传相同身份证名称的头像，则需要删除服务器原有的该身份证头像
		File oldFile = new File(uploadConfig.getPath()+"photo/"+fileLocalName+ "." + fileType);
		if (oldFile.exists() && oldFile.isFile()) {
			oldFile.delete();
		}
		// 文件保存路径
		String filePath = uploadConfig.getPath()+"photo/";//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
		try {
			// 创建目录
			File dir = new File(filePath);
			dir.mkdirs();

			// 获取文件输入流
			InputStream in = file.getInputStream();

			// 创建文件并保存
			File f = new File(filePath + fileLocalName + "." + fileType);
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			in.close();
			sysUserService.updateHeadPortrait("http://110.19.104.227:20020/api/image/photo/"+fileLocalName+ "." + fileType,fileLocalName);
			return R.ok("http://110.19.104.227:20020/api/image/photo/"+fileLocalName+ "." + fileType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.error("上传失败！");
		}
	}
	
}
