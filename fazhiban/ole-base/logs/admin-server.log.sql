2017-11-11 17:32:34,240 [http-nio-8080-exec-1] ==>  Preparing: select * from sys_user t where t.username = ? 
2017-11-11 17:32:34,362 [http-nio-8080-exec-1] ==> Parameters: 1501001015(String)
2017-11-11 17:32:34,379 [http-nio-8080-exec-1] <==      Total: 0
2017-11-11 17:32:41,033 [http-nio-8080-exec-2] ==>  Preparing: select * from sys_user t where t.username = ? 
2017-11-11 17:32:41,034 [http-nio-8080-exec-2] ==> Parameters: admin(String)
2017-11-11 17:32:41,042 [http-nio-8080-exec-2] <==      Total: 1
2017-11-11 17:32:41,051 [taskExecutor-2] ==>  Preparing: insert into sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2017-11-11 17:32:41,084 [taskExecutor-2] ==> Parameters: 1(Long), web端登陆(String), true(Boolean), null
2017-11-11 17:32:41,105 [taskExecutor-2] <==    Updates: 1
2017-11-11 17:32:41,206 [http-nio-8080-exec-2] ==>  Preparing: select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? order by p.sort 
2017-11-11 17:32:41,211 [http-nio-8080-exec-2] ==> Parameters: 1(Long)
2017-11-11 17:32:41,225 [http-nio-8080-exec-2] <==      Total: 26
2017-11-11 17:32:43,171 [http-nio-8080-exec-1] ==>  Preparing: select * from sys_role r inner join sys_role_user ru on r.id = ru.roleId where ru.userId = ? 
2017-11-11 17:32:43,173 [http-nio-8080-exec-1] ==> Parameters: 1(Long)
2017-11-11 17:32:43,176 [http-nio-8080-exec-1] <==      Total: 1
2017-11-11 17:32:43,180 [http-nio-8080-exec-1] ==>  Preparing: select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? order by p.sort 
2017-11-11 17:32:43,180 [http-nio-8080-exec-1] ==> Parameters: 1(Long)
2017-11-11 17:32:43,186 [http-nio-8080-exec-1] <==      Total: 26
2017-11-11 17:32:43,264 [http-nio-8080-exec-1] ==>  Preparing: select count(1) from sys_user t 
2017-11-11 17:32:43,267 [http-nio-8080-exec-1] ==> Parameters: 
2017-11-11 17:32:43,269 [http-nio-8080-exec-1] <==      Total: 1
2017-11-11 17:32:43,271 [http-nio-8080-exec-1] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2017-11-11 17:32:43,274 [http-nio-8080-exec-1] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:32:43,276 [http-nio-8080-exec-1] <==      Total: 2
2017-11-11 17:32:43,597 [http-nio-8080-exec-6] ==>  Preparing: select count(1) from sys_user t 
2017-11-11 17:32:43,598 [http-nio-8080-exec-6] ==> Parameters: 
2017-11-11 17:32:43,599 [http-nio-8080-exec-6] <==      Total: 1
2017-11-11 17:32:43,600 [http-nio-8080-exec-6] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2017-11-11 17:32:43,601 [http-nio-8080-exec-6] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:32:43,603 [http-nio-8080-exec-6] <==      Total: 2
2017-11-11 17:32:46,881 [http-nio-8080-exec-4] ==>  Preparing: select * from sys_permission t order by t.sort 
2017-11-11 17:32:46,882 [http-nio-8080-exec-4] ==> Parameters: 
2017-11-11 17:32:46,886 [http-nio-8080-exec-4] <==      Total: 26
2017-11-11 17:32:47,696 [http-nio-8080-exec-8] ==>  Preparing: select count(1) from sys_role t 
2017-11-11 17:32:47,698 [http-nio-8080-exec-8] ==> Parameters: 
2017-11-11 17:32:47,700 [http-nio-8080-exec-8] <==      Total: 1
2017-11-11 17:32:47,702 [http-nio-8080-exec-8] ==>  Preparing: select * from sys_role t order by updateTime desc limit ?, ? 
2017-11-11 17:32:47,704 [http-nio-8080-exec-8] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:32:47,706 [http-nio-8080-exec-8] <==      Total: 2
2017-11-11 17:32:51,029 [http-nio-8080-exec-4] ==>  Preparing: select * from sys_permission t order by t.sort 
2017-11-11 17:32:51,029 [http-nio-8080-exec-4] ==> Parameters: 
2017-11-11 17:32:51,032 [http-nio-8080-exec-4] <==      Total: 26
2017-11-11 17:32:51,058 [http-nio-8080-exec-2] ==>  Preparing: select * from sys_role t where t.id = ? 
2017-11-11 17:32:51,059 [http-nio-8080-exec-2] ==> Parameters: 1(Long)
2017-11-11 17:32:51,061 [http-nio-8080-exec-2] <==      Total: 1
2017-11-11 17:32:51,071 [http-nio-8080-exec-3] ==>  Preparing: select p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId where rp.roleId = ? order by p.sort 
2017-11-11 17:32:51,073 [http-nio-8080-exec-3] ==> Parameters: 1(Long)
2017-11-11 17:32:51,077 [http-nio-8080-exec-3] <==      Total: 26
2017-11-11 17:32:56,938 [http-nio-8080-exec-1] ==>  Preparing: select count(1) from file_info t 
2017-11-11 17:32:56,942 [http-nio-8080-exec-1] ==> Parameters: 
2017-11-11 17:32:56,948 [http-nio-8080-exec-1] <==      Total: 1
2017-11-11 17:33:00,054 [http-nio-8080-exec-3] ==>  Preparing: select count(1) from articles t 
2017-11-11 17:33:00,055 [http-nio-8080-exec-3] ==> Parameters: 
2017-11-11 17:33:00,057 [http-nio-8080-exec-3] <==      Total: 1
2017-11-11 17:33:24,211 [http-nio-8080-exec-7] ==>  Preparing: select count(1) from file_info t 
2017-11-11 17:33:24,211 [http-nio-8080-exec-7] ==> Parameters: 
2017-11-11 17:33:24,213 [http-nio-8080-exec-7] <==      Total: 1
2017-11-11 17:33:25,587 [http-nio-8080-exec-2] ==>  Preparing: select * from sys_permission t order by t.sort 
2017-11-11 17:33:25,588 [http-nio-8080-exec-2] ==> Parameters: 
2017-11-11 17:33:25,592 [http-nio-8080-exec-2] <==      Total: 26
2017-11-11 17:33:26,999 [http-nio-8080-exec-6] ==>  Preparing: select count(1) from sys_role t 
2017-11-11 17:33:27,000 [http-nio-8080-exec-6] ==> Parameters: 
2017-11-11 17:33:27,001 [http-nio-8080-exec-6] <==      Total: 1
2017-11-11 17:33:27,002 [http-nio-8080-exec-6] ==>  Preparing: select * from sys_role t order by updateTime desc limit ?, ? 
2017-11-11 17:33:27,002 [http-nio-8080-exec-6] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:33:27,004 [http-nio-8080-exec-6] <==      Total: 2
2017-11-11 17:33:35,156 [http-nio-8080-exec-4] ==>  Preparing: select count(1) from sys_user t 
2017-11-11 17:33:35,157 [http-nio-8080-exec-4] ==> Parameters: 
2017-11-11 17:33:35,159 [http-nio-8080-exec-4] <==      Total: 1
2017-11-11 17:33:35,160 [http-nio-8080-exec-4] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2017-11-11 17:33:35,160 [http-nio-8080-exec-4] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:33:35,162 [http-nio-8080-exec-4] <==      Total: 2
2017-11-11 17:33:35,799 [http-nio-8080-exec-8] ==>  Preparing: select count(1) from sys_user t 
2017-11-11 17:33:35,800 [http-nio-8080-exec-8] ==> Parameters: 
2017-11-11 17:33:35,801 [http-nio-8080-exec-8] <==      Total: 1
2017-11-11 17:33:35,802 [http-nio-8080-exec-8] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2017-11-11 17:33:35,803 [http-nio-8080-exec-8] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:33:35,805 [http-nio-8080-exec-8] <==      Total: 2
2017-11-11 17:33:50,682 [http-nio-8080-exec-10] ==>  Preparing: select * from sys_permission t order by t.sort 
2017-11-11 17:33:50,682 [http-nio-8080-exec-10] ==> Parameters: 
2017-11-11 17:33:50,685 [http-nio-8080-exec-10] <==      Total: 26
2017-11-11 17:33:51,641 [http-nio-8080-exec-6] ==>  Preparing: select count(1) from sys_role t 
2017-11-11 17:33:51,642 [http-nio-8080-exec-6] ==> Parameters: 
2017-11-11 17:33:51,643 [http-nio-8080-exec-6] <==      Total: 1
2017-11-11 17:33:51,644 [http-nio-8080-exec-6] ==>  Preparing: select * from sys_role t order by updateTime desc limit ?, ? 
2017-11-11 17:33:51,645 [http-nio-8080-exec-6] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:33:51,648 [http-nio-8080-exec-6] <==      Total: 2
2017-11-11 17:33:56,582 [http-nio-8080-exec-2] ==>  Preparing: select * from sys_permission t where t.type = 1 order by t.sort 
2017-11-11 17:33:56,583 [http-nio-8080-exec-2] ==> Parameters: 
2017-11-11 17:33:56,585 [http-nio-8080-exec-2] <==      Total: 13
2017-11-11 17:33:56,593 [http-nio-8080-exec-5] ==>  Preparing: select * from sys_permission t where t.id = ? 
2017-11-11 17:33:56,594 [http-nio-8080-exec-5] ==> Parameters: 9(Long)
2017-11-11 17:33:56,595 [http-nio-8080-exec-5] <==      Total: 1
2017-11-11 17:34:03,302 [http-nio-8080-exec-6] ==>  Preparing: update sys_permission t set parentId = ?, name = ?, css = ?, href = ?, type = ?, permission = ?, sort = ? where t.id = ? 
2017-11-11 17:34:03,304 [http-nio-8080-exec-6] ==> Parameters: 8(Long), 查询(String), fa-bomb(String), (String), 2(Integer), sys:menu:query(String), 100(Integer), 9(Long)
2017-11-11 17:34:03,326 [http-nio-8080-exec-6] <==    Updates: 1
2017-11-11 17:34:03,327 [taskExecutor-6] ==>  Preparing: insert into sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2017-11-11 17:34:03,328 [taskExecutor-6] ==> Parameters: 1(Long), 修改菜单(String), true(Boolean), null
2017-11-11 17:34:03,342 [taskExecutor-6] <==    Updates: 1
2017-11-11 17:34:04,601 [http-nio-8080-exec-7] ==>  Preparing: select * from sys_permission t order by t.sort 
2017-11-11 17:34:04,602 [http-nio-8080-exec-7] ==> Parameters: 
2017-11-11 17:34:04,605 [http-nio-8080-exec-7] <==      Total: 26
2017-11-11 17:34:08,863 [http-nio-8080-exec-5] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:08,865 [http-nio-8080-exec-5] ==> Parameters: 
2017-11-11 17:34:08,866 [http-nio-8080-exec-5] <==      Total: 1
2017-11-11 17:34:08,867 [http-nio-8080-exec-5] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by createTime desc limit ?, ? 
2017-11-11 17:34:08,869 [http-nio-8080-exec-5] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:08,880 [http-nio-8080-exec-5] <==      Total: 8
2017-11-11 17:34:12,198 [http-nio-8080-exec-8] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:12,200 [http-nio-8080-exec-8] ==> Parameters: 
2017-11-11 17:34:12,202 [http-nio-8080-exec-8] <==      Total: 1
2017-11-11 17:34:12,205 [http-nio-8080-exec-8] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by module asc limit ?, ? 
2017-11-11 17:34:12,208 [http-nio-8080-exec-8] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:12,220 [http-nio-8080-exec-8] <==      Total: 8
2017-11-11 17:34:12,682 [http-nio-8080-exec-9] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:12,683 [http-nio-8080-exec-9] ==> Parameters: 
2017-11-11 17:34:12,685 [http-nio-8080-exec-9] <==      Total: 1
2017-11-11 17:34:12,687 [http-nio-8080-exec-9] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by module desc limit ?, ? 
2017-11-11 17:34:12,689 [http-nio-8080-exec-9] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:12,701 [http-nio-8080-exec-9] <==      Total: 8
2017-11-11 17:34:12,977 [http-nio-8080-exec-7] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:12,978 [http-nio-8080-exec-7] ==> Parameters: 
2017-11-11 17:34:12,979 [http-nio-8080-exec-7] <==      Total: 1
2017-11-11 17:34:12,980 [http-nio-8080-exec-7] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by flag asc limit ?, ? 
2017-11-11 17:34:12,982 [http-nio-8080-exec-7] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:12,988 [http-nio-8080-exec-7] <==      Total: 8
2017-11-11 17:34:13,985 [http-nio-8080-exec-1] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:13,986 [http-nio-8080-exec-1] ==> Parameters: 
2017-11-11 17:34:13,987 [http-nio-8080-exec-1] <==      Total: 1
2017-11-11 17:34:13,988 [http-nio-8080-exec-1] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by remark asc limit ?, ? 
2017-11-11 17:34:13,990 [http-nio-8080-exec-1] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:13,996 [http-nio-8080-exec-1] <==      Total: 8
2017-11-11 17:34:15,753 [http-nio-8080-exec-6] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:15,754 [http-nio-8080-exec-6] ==> Parameters: 
2017-11-11 17:34:15,757 [http-nio-8080-exec-6] <==      Total: 1
2017-11-11 17:34:15,759 [http-nio-8080-exec-6] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by id asc limit ?, ? 
2017-11-11 17:34:15,762 [http-nio-8080-exec-6] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:15,773 [http-nio-8080-exec-6] <==      Total: 8
2017-11-11 17:34:16,274 [http-nio-8080-exec-10] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:16,275 [http-nio-8080-exec-10] ==> Parameters: 
2017-11-11 17:34:16,276 [http-nio-8080-exec-10] <==      Total: 1
2017-11-11 17:34:16,278 [http-nio-8080-exec-10] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by id desc limit ?, ? 
2017-11-11 17:34:16,280 [http-nio-8080-exec-10] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:16,289 [http-nio-8080-exec-10] <==      Total: 8
2017-11-11 17:34:16,812 [http-nio-8080-exec-2] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:16,812 [http-nio-8080-exec-2] ==> Parameters: 
2017-11-11 17:34:16,814 [http-nio-8080-exec-2] <==      Total: 1
2017-11-11 17:34:16,815 [http-nio-8080-exec-2] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by user asc limit ?, ? 
2017-11-11 17:34:16,816 [http-nio-8080-exec-2] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:19,236 [http-nio-8080-exec-3] ==>  Preparing: select count(1) from sys_logs t left join sys_user u on u.id = t.userId 
2017-11-11 17:34:19,237 [http-nio-8080-exec-3] ==> Parameters: 
2017-11-11 17:34:19,242 [http-nio-8080-exec-3] <==      Total: 1
2017-11-11 17:34:19,244 [http-nio-8080-exec-3] ==>  Preparing: select t.*, u.username, u.nickname from sys_logs t left join sys_user u on u.id = t.userId order by user desc limit ?, ? 
2017-11-11 17:34:19,247 [http-nio-8080-exec-3] ==> Parameters: 0(Integer), 10(Integer)
2017-11-11 17:34:30,664 [http-nio-8080-exec-9] ==>  Preparing: select count(1) from file_info t 
2017-11-11 17:34:30,665 [http-nio-8080-exec-9] ==> Parameters: 
2017-11-11 17:34:30,666 [http-nio-8080-exec-9] <==      Total: 1
2017-11-11 17:34:43,703 [http-nio-8080-exec-2] ==>  Preparing: select * from sys_role r inner join sys_role_user ru on r.id = ru.roleId where ru.userId = ? 
2017-11-11 17:34:43,704 [http-nio-8080-exec-2] ==> Parameters: 1(Long)
2017-11-11 17:34:43,705 [http-nio-8080-exec-2] <==      Total: 1
2017-11-11 17:34:43,707 [http-nio-8080-exec-2] ==>  Preparing: select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? order by p.sort 
2017-11-11 17:34:43,708 [http-nio-8080-exec-2] ==> Parameters: 1(Long)
2017-11-11 17:34:43,711 [http-nio-8080-exec-2] <==      Total: 26
2017-11-11 17:34:43,713 [http-nio-8080-exec-2] ==>  Preparing: select count(1) from articles t 
2017-11-11 17:34:43,714 [http-nio-8080-exec-2] ==> Parameters: 
2017-11-11 17:34:43,715 [http-nio-8080-exec-2] <==      Total: 1
