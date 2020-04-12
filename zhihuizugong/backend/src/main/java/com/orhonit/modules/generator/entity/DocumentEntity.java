package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公文管理主表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-06 14:38:55
 */
@Data
@TableName("tb_document")
public class DocumentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String documentId;
	/**
	 * 发文机关
	 */
	private String fwjg;
	/**
	 * 发文字号
	 */
	private String fwzh;
	/**
	 * 紧急程度（1：特急、2：加急、3：常规）
	 */
	private Integer jjcd;
	/**
	 * 密级（1：绝密，2：机密，3：秘密，4：普通）
	 */
	private Integer mj;
	/**
	 * 文件标题
	 */
	private String title;
	/**
	 * 主要内容
	 */
	private String content;
	/**
	 * 办理情况
	 */
	private String blqk;
	/**
	 * 状态（1： 公文签发 ，2： 公文审核， 3： 拟办意见 ，4：办理意见， 5： 部长批示）
	 */
	private Integer type;
	/**
	 * 创建者
	 */
	private Long createby;
	/**
	 * 创建人姓名
	 */
	private String createName;
	/**
	 * 创建时间
	 */
	private Date createdate;
	/**
	 * 审核（1：通过，0：未通过）
	 */
	private Integer sh;
	/**
	 * 审核时间
	 */
	private Date auditTime;
	/**
	 * 份数
	 */
	private Integer number;
	/**
	 * 状态 (0:未读,  1:已读)
	 */
	private String remarks;
	/**
	 * 审核原因
	 */
	private String shyy;
	/**
	 * 收件人（用户id）
	 */
	private Long userId;
	/**
	 * 收件人姓名
	 */
	private String addresseeName;
	/**
	 * 主送机关
	 */
	private String mainLower;
	/**
	 * 抄送机关
	 */
	private String copyLower;
	/**
	 * 办公室人员
	 */
	private Long workId;
	/**
	 * 办公室人员名称
	 */
	private String workName;
	/**
	 * 领导人员
	 */
	private Long leadId;
	/**
	 * 领导人员名称
	 */
	private String leadName;
	/**
	 * 部长人员
	 */
	private Long ministerId;
	/**
	 * 部长人员名称
	 */
	private String ministerName;
	/**
	 * 拟办状态（1：已拟办 2：未拟办）
	 */
	private Integer workType;
	/**
	 * 办理状态（1：已办理 2：未办理）
	 */
	private Integer leadType;
	/**
	 * 批示状态（1：已批示 2：未批示）
	 */
	private Integer ministerType;

}
