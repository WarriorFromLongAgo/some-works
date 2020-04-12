2018-01-03 15:52:56,313 [http-nio-8080-exec-7] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 15:52:56,497 [http-nio-8080-exec-7] ==> Parameters: admin(String)
2018-01-03 15:52:56,589 [http-nio-8080-exec-7] <==      Total: 1
2018-01-03 15:52:56,959 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 15:52:57,036 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 15:52:57,479 [taskExecutor-1] <==    Updates: 1
2018-01-03 15:53:38,727 [taskExecutor-2] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 15:53:38,734 [taskExecutor-2] ==> Parameters: 1(Long), 退出(String), true(Boolean), null
2018-01-03 15:53:38,907 [taskExecutor-2] <==    Updates: 1
2018-01-03 15:53:44,129 [http-nio-8080-exec-9] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 15:53:44,130 [http-nio-8080-exec-9] ==> Parameters: admin(String)
2018-01-03 15:53:44,193 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 15:53:44,196 [taskExecutor-3] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 15:53:44,198 [taskExecutor-3] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 15:53:44,334 [taskExecutor-3] <==    Updates: 1
2018-01-03 16:07:34,239 [http-nio-8080-exec-9] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 16:07:34,246 [http-nio-8080-exec-9] ==> Parameters: 1(Long), 44(String)
2018-01-03 16:07:35,716 [http-nio-8080-exec-9] <==      Total: 71
2018-01-03 16:18:00,588 [taskExecutor-5] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:18:00,590 [taskExecutor-5] ==> Parameters: 1(Long), 退出(String), true(Boolean), null
2018-01-03 16:18:00,869 [taskExecutor-5] <==    Updates: 1
2018-01-03 16:18:04,977 [http-nio-8080-exec-9] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 16:18:04,979 [http-nio-8080-exec-9] ==> Parameters: admin(String)
2018-01-03 16:18:05,080 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 16:18:05,090 [taskExecutor-6] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:18:05,099 [taskExecutor-6] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 16:18:05,227 [http-nio-8080-exec-3] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 16:18:05,228 [http-nio-8080-exec-3] ==> Parameters: 1(Long), 66(String)
2018-01-03 16:18:05,354 [taskExecutor-6] <==    Updates: 1
2018-01-03 16:18:05,365 [http-nio-8080-exec-3] <==      Total: 0
2018-01-03 16:18:16,415 [taskExecutor-9] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:18:16,416 [taskExecutor-9] ==> Parameters: 1(Long), 退出(String), true(Boolean), null
2018-01-03 16:18:16,629 [taskExecutor-9] <==    Updates: 1
2018-01-03 16:18:19,906 [http-nio-8080-exec-4] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 16:18:19,908 [http-nio-8080-exec-4] ==> Parameters: admin(String)
2018-01-03 16:18:20,017 [http-nio-8080-exec-4] <==      Total: 1
2018-01-03 16:18:20,022 [taskExecutor-10] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:18:20,027 [taskExecutor-10] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 16:18:20,143 [http-nio-8080-exec-8] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 16:18:20,146 [http-nio-8080-exec-8] ==> Parameters: 1(Long), 66(String)
2018-01-03 16:18:20,225 [taskExecutor-10] <==    Updates: 1
2018-01-03 16:18:20,735 [http-nio-8080-exec-8] <==      Total: 0
2018-01-03 16:18:27,814 [taskExecutor-13] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:18:27,815 [taskExecutor-13] ==> Parameters: 1(Long), 退出(String), true(Boolean), null
2018-01-03 16:18:27,977 [taskExecutor-13] <==    Updates: 1
2018-01-03 16:18:31,488 [http-nio-8080-exec-1] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 16:18:31,490 [http-nio-8080-exec-1] ==> Parameters: admin(String)
2018-01-03 16:18:31,580 [http-nio-8080-exec-1] <==      Total: 1
2018-01-03 16:18:31,587 [taskExecutor-14] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:18:31,588 [taskExecutor-14] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 16:18:31,767 [http-nio-8080-exec-8] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 16:18:31,768 [http-nio-8080-exec-8] ==> Parameters: 1(Long), 66(String)
2018-01-03 16:18:31,769 [taskExecutor-14] <==    Updates: 1
2018-01-03 16:18:31,855 [http-nio-8080-exec-8] <==      Total: 0
2018-01-03 16:20:29,019 [taskExecutor-3] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:20:29,020 [taskExecutor-3] ==> Parameters: 1(Long), 退出(String), true(Boolean), null
2018-01-03 16:20:29,210 [taskExecutor-3] <==    Updates: 1
2018-01-03 16:20:32,131 [http-nio-8080-exec-9] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 16:20:32,135 [http-nio-8080-exec-9] ==> Parameters: admin(String)
2018-01-03 16:20:32,233 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 16:20:32,238 [taskExecutor-4] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:20:32,240 [taskExecutor-4] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 16:20:32,392 [http-nio-8080-exec-8] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 16:20:32,394 [http-nio-8080-exec-8] ==> Parameters: 1(Long), 151(String)
2018-01-03 16:20:32,545 [taskExecutor-4] <==    Updates: 1
2018-01-03 16:20:32,546 [http-nio-8080-exec-8] <==      Total: 1
2018-01-03 16:23:25,373 [taskExecutor-9] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:23:25,376 [taskExecutor-9] ==> Parameters: 1(Long), 退出(String), true(Boolean), null
2018-01-03 16:23:25,561 [taskExecutor-9] <==    Updates: 1
2018-01-03 16:23:28,048 [http-nio-8080-exec-4] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 16:23:28,049 [http-nio-8080-exec-4] ==> Parameters: admin(String)
2018-01-03 16:23:28,128 [http-nio-8080-exec-4] <==      Total: 1
2018-01-03 16:23:28,131 [taskExecutor-10] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:23:28,138 [taskExecutor-10] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 16:23:28,324 [taskExecutor-10] <==    Updates: 1
2018-01-03 16:23:28,362 [http-nio-8080-exec-9] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 16:23:28,363 [http-nio-8080-exec-9] ==> Parameters: 1(Long), 151(String)
2018-01-03 16:23:28,446 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 16:27:25,311 [http-nio-8080-exec-6] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 16:27:25,459 [http-nio-8080-exec-6] ==> Parameters: admin(String)
2018-01-03 16:27:25,639 [http-nio-8080-exec-6] <==      Total: 1
2018-01-03 16:27:25,672 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 16:27:25,762 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 16:27:25,936 [taskExecutor-1] <==    Updates: 1
2018-01-03 16:27:25,963 [http-nio-8080-exec-8] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 16:27:25,973 [http-nio-8080-exec-8] ==> Parameters: 1(Long), 151(String)
2018-01-03 16:27:26,042 [http-nio-8080-exec-8] <==      Total: 1
2018-01-03 17:13:20,112 [http-nio-8080-exec-9] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 17:13:20,419 [http-nio-8080-exec-9] ==> Parameters: admin(String)
2018-01-03 17:13:20,522 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 17:13:20,553 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 17:13:20,680 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 17:13:20,843 [taskExecutor-1] <==    Updates: 1
2018-01-03 17:13:20,943 [http-nio-8080-exec-10] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 17:13:20,953 [http-nio-8080-exec-10] ==> Parameters: 1(Long), 151(String)
2018-01-03 17:13:21,025 [http-nio-8080-exec-10] <==      Total: 1
2018-01-03 18:07:28,168 [http-nio-8080-exec-9] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 18:07:28,342 [http-nio-8080-exec-9] ==> Parameters: admin(String)
2018-01-03 18:07:28,454 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 18:07:28,482 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 18:07:28,607 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 18:07:28,730 [taskExecutor-1] <==    Updates: 1
2018-01-03 18:07:28,797 [http-nio-8080-exec-4] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 18:07:28,807 [http-nio-8080-exec-4] ==> Parameters: 1(Long), 151(String)
2018-01-03 18:07:28,861 [http-nio-8080-exec-4] <==      Total: 1
2018-01-03 21:11:38,609 [http-nio-8080-exec-9] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 21:11:38,620 [http-nio-8080-exec-9] ==> Parameters: admin(String)
2018-01-03 21:11:38,748 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 21:11:38,782 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 21:11:38,802 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 21:11:38,907 [taskExecutor-1] <==    Updates: 1
2018-01-03 21:11:39,081 [http-nio-8080-exec-10] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 21:11:39,085 [http-nio-8080-exec-10] ==> Parameters: 1(Long), 151(String)
2018-01-03 21:11:39,179 [http-nio-8080-exec-10] <==      Total: 1
2018-01-03 21:19:52,675 [http-nio-8080-exec-9] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 21:19:52,690 [http-nio-8080-exec-9] ==> Parameters: admin(String)
2018-01-03 21:19:52,738 [http-nio-8080-exec-9] <==      Total: 1
2018-01-03 21:19:52,771 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 21:19:52,779 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 21:19:52,914 [taskExecutor-1] <==    Updates: 1
2018-01-03 21:19:53,162 [http-nio-8080-exec-4] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 21:19:53,166 [http-nio-8080-exec-4] ==> Parameters: 1(Long), 151(String)
2018-01-03 21:19:53,240 [http-nio-8080-exec-4] <==      Total: 1
2018-01-03 21:22:24,788 [http-nio-8080-exec-7] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 21:22:24,804 [http-nio-8080-exec-7] ==> Parameters: admin(String)
2018-01-03 21:22:24,852 [http-nio-8080-exec-7] <==      Total: 1
2018-01-03 21:22:24,894 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 21:22:25,045 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 21:22:25,125 [taskExecutor-1] <==    Updates: 1
2018-01-03 21:22:25,288 [http-nio-8080-exec-2] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 21:22:25,294 [http-nio-8080-exec-2] ==> Parameters: 1(Long), 151(String)
2018-01-03 21:22:25,341 [http-nio-8080-exec-2] <==      Total: 1
2018-01-03 21:25:21,933 [http-nio-8080-exec-8] ==>  Preparing: select * from ole_sys_user t where t.username = ? 
2018-01-03 21:25:21,945 [http-nio-8080-exec-8] ==> Parameters: admin(String)
2018-01-03 21:25:21,995 [http-nio-8080-exec-8] <==      Total: 1
2018-01-03 21:25:22,027 [taskExecutor-1] ==>  Preparing: insert into ole_sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2018-01-03 21:25:22,149 [taskExecutor-1] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2018-01-03 21:25:22,230 [taskExecutor-1] <==    Updates: 1
2018-01-03 21:25:22,364 [http-nio-8080-exec-9] ==>  Preparing: select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? AND sys_id = ? order by p.sort 
2018-01-03 21:25:22,370 [http-nio-8080-exec-9] ==> Parameters: 1(Long), 151(String)
2018-01-03 21:25:22,414 [http-nio-8080-exec-9] <==      Total: 1
