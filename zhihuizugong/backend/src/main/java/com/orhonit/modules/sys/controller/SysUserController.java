package com.orhonit.modules.sys.controller;

import com.orhonit.common.annotation.SysLog;
import com.orhonit.common.utils.Constant;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.common.validator.Assert;
import com.orhonit.common.validator.ValidatorUtils;
import com.orhonit.common.validator.group.AddGroup;
import com.orhonit.common.validator.group.UpdateGroup;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.form.PasswordForm;
import com.orhonit.modules.sys.service.SysUserRoleService;
import com.orhonit.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author zjw
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired	
    private UploadConfig uploadConfig;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
//			params.put("createUserId", getUserId());
			params.put("userDept", getUser().getUserDept());
		}
		params.put("userDept", 0);
		PageUtils page = sysUserService.queryPage(params);

		return R.ok().put("page", page);
	}
	
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
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}

	/**
	 * APP端我的信息
	 */
	@GetMapping("/appInfo")
	public R appInfo(){
		Map user = sysUserService.selectAppInfo();
		return R.ok().put("user", user);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public R password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		
		//sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
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
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
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
