package com.orhonit.ole.common.constants;

/**
 * 系统常量类
 * 
 * @author liuzh
 */
public class CommonParameters {
	/**
	 * 案件状态
	 * 
	 * @author liuzh
	 */
	public interface CaseStatus {

		/**
		 * 案件暂存 10
		 */
		Integer AJSL = 10;

		/**
		 * 案件暂存 11
		 */
		Integer AJZC = 11;

		/**
		 * 初步核实 12
		 */
		Integer CBHS = 12;

		/**
		 * 现场处理 13
		 */
		Integer XCCL = 13;

		/**
		 * 案件结案 14
		 */
		Integer ANJA = 14;

		/**
		 * 案件立案 21
		 */
		Integer AJLA = 21;

		/**
		 * 立案审批 22
		 */
		Integer LASP = 22;

		/**
		 * 调查取证 23
		 */
		Integer DCQZ = 23;
		/**
		 * 先行告知 24
		 */
		Integer XXGZ = 24;
		/**
		 * 申请案件处理 25
		 */
		Integer SQAJCL = 25;

		/**
		 * 听证申请 26
		 */
		Integer TZSQ = 26;

		/**
		 * 听证受理 27
		 */
		Integer TZSL = 27;

		/**
		 * 举行听证 28
		 */
		Integer JXTZ = 28;

		/**
		 * 重大案件受理 29
		 */
		Integer ZDAJSL = 29;

		/**
		 * 重大案件审核 30
		 */
		Integer ZDAJSH = 30;

		/**
		 * 法制审核 31
		 */
		Integer FZSH = 31;

		/**
		 * 集体讨论 32
		 */
		Integer JTTL = 32;

		/**
		 * 听证告知 34
		 */
		Integer TZGZ = 34;

		/**
		 * 立案审核 35
		 */
		Integer LASH = 35;

		/**
		 * 一般案件结案 36
		 */
		Integer YBANJA = 36;

		/**
		 * 案件合议 37
		 */
		Integer AJHY = 37;

		/**
		 * 陈述申辩 38
		 */
		Integer CSSB = 38;

		/**
		 * 结案申请 39
		 */
		Integer JASQ = 39;

		/**
		 * 案件核审 40
		 */
		Integer AJHS = 40;

		/**
		 * 申请强制执行 41
		 */
		Integer SQQZZX = 41;

		/**
		 * 申请复议 42
		 */
		Integer SQFY = 42;

		/**
		 * 初步核查 43
		 */
		Integer CBSC = 43;

		/**
		 * 复议受理 44
		 */
		Integer FYSL = 44;

		/**
		 * 复议审理 45
		 */
		Integer FYSLL = 45;

		/**
		 * 调解协商 46
		 */
		Integer TJXS = 46;

		/**
		 * 复议处理 47
		 */
		Integer FYCL = 47;

		/**
		 * 复议决定 48
		 */
		Integer FYJD = 48;

		/**
		 * 结案审核 49
		 */
		Integer JASH = 49;

		/**
		 * 结案审批 50
		 */
		Integer JASP = 50;

		/**
		 * 强制执行审核 51
		 */
		Integer QZZXSH = 51;

		/**
		 * 强制执行审批 52
		 */
		Integer QZZXSP = 52;

		/**
		 * 强制执行 53
		 */
		Integer QZZX = 53;

		/**
		 * 案件处理审核 54
		 */
		Integer AJCLSH = 54;

		/**
		 * 案件处理审批 55
		 */
		Integer AJCLSQ = 55;

		/**
		 * 案件处理 56
		 */
		Integer AJCL = 56;

		/**
		 * 申请陈述申辩 57
		 */
		Integer SQCSSB = 57;

		/**
		 * 不予受理审核 58
		 */
		Integer BYSLSH = 58;

		/**
		 * 不予受理审批 59
		 */
		Integer BYSLSQ = 59;

		/**
		 * 复议受理审核 60
		 */
		Integer FYSLSH = 60;

		/**
		 * 复议受理审批 61
		 */
		Integer FYSLSQ = 61;

		/**
		 * 复议调查取证 62
		 */
		Integer FYDCQZ = 62;

		/**
		 * 复议决定审核 63
		 */
		Integer FYDJSH = 63;

		/**
		 * 复议决定审批 64
		 */
		Integer FYDJSQ = 64;

		/**
		 * 复议下达通知 65
		 */
		Integer FYXDTZ = 65;

		/**
		 * 驳回复议审核 66
		 */
		Integer BHFYSH = 66;

		/**
		 * 驳回复议审批67
		 */
		Integer BHFYSP = 67;

		/**
		 * 驳回行政复议 68
		 */
		Integer BHXZFY = 68;

		/**
		 * 责令恢复审理 69
		 */
		Integer ZLHFSL = 69;

		/**
		 * 终止复议审核 70
		 */
		Integer ZZFYSH = 70;

		/**
		 * 终止复议审批 71
		 */
		Integer ZZFYSP = 71;

		/**
		 * 暂停审理审核 72
		 */
		Integer ZTSLSH = 72;

		/**
		 * 暂停审理审批
		 */
		Integer ZTSLSP = 73;

		/**
		 * 暂停审理 74
		 */
		Integer ZTSL = 74;

		/**
		 * 恢复审理 75
		 */
		Integer HFSL = 75;

		/**
		 * 初步核查审核 76
		 */
		Integer CBHCSH = 76;

		/**
		 * 初步核查审批 77
		 */
		Integer CBHCSP = 77;

		/**
		 * 核查结果下达 78
		 */
		Integer HCJGXD = 78;

		/**
		 * 不予受理通知 79
		 */
		Integer BYSLTZ = 79;

		/**
		 * 责令恢复审核 80
		 */
		Integer ZLHFSH = 80;

		/**
		 * 责令恢复审批 81
		 */
		Integer ZLHFSP = 81;

		/**
		 * 责令恢复通知 82
		 */
		Integer ZLHFTZ = 82;

		/**
		 * 案件归档 90
		 */
		Integer ANGD = 90;

		/**
		 * 撤销立案审核
		 */
		Integer CXLASH = 91;

		/**
		 * 撤销立案审批
		 */
		Integer CXLASP = 92;
	}

	/**
	 * 一般流程处理方式
	 * 
	 * @author ebusu
	 */
	public interface SimpleFlow {
		/**
		 * 不予处理 1
		 */
		Integer NOT_DEAL = 1;

		/**
		 * 立案 2
		 */
		Integer PLACE_CASE = 2;

		/**
		 * 现场处理 3
		 */
		Integer LOCATE_PRO = 3;

		/**
		 * 给与警告 4
		 */
		Integer TO_WARNING = 4;

		/**
		 * 给与处罚 5
		 */
		Integer TO_PUNISH = 5;

		/**
		 * 不予立案 6
		 */
		Integer NOT_FILING = 6;

		/**
		 * 调查取证 7
		 */
		Integer TO_SEARCH_PROOF = 7;

		/**
		 * 重新立案 8
		 */
		Integer RE_FILING = 8;

		/**
		 * 先行告知 9
		 */
		Integer TO_INFORMING = 9;

		/**
		 * 不予处罚 10
		 */
		Integer NOT_PUNISH = 10;

		/**
		 * 撤销立案 11
		 */
		Integer REVOKE_FILING = 11;

		/**
		 * 听证告知 12
		 */
		Integer HEAR_NOTIFY = 12;

		/**
		 * 重大案件 13
		 */
		Integer MAJOR_CASE = 13;

		/**
		 * 听证受理 14
		 */
		Integer HEAR_ADMISS = 14;

		/**
		 * 三天内未申请，自动转到案件受理 15
		 */
		Integer AUTO_CASE_ADMISS = 15;

		/**
		 * 不集体讨论 16
		 */
		Integer NOT_GROUP_DISCUSSION = 16;

		/**
		 * 集体讨论 17
		 */
		Integer GROUP_DISCUSSION = 17;

		/**
		 * 材料不全 18
		 */
		Integer INCOMPLETE_MATERIAL = 18;

		/**
		 * 法制审核意见 19
		 */
		Integer AUDIT_OPINION = 19;

		/**
		 * 听证 20
		 */
		Integer TO_HEAR = 20;

		/**
		 * 申请复审 21
		 */
		Integer TO_APPLICATION_REEXAMINATION = 21;

		/**
		 * 接受处罚 22
		 */
		Integer TO_ACCEPT_PUNISHMENT = 22;

		/**
		 * 不接受处罚不申请复议 23
		 */
		Integer NO_APPLY_RECONSIDERATION_ACCEPT_PUNISHMENT = 23;

		/**
		 * 申请复议 24
		 */
		Integer TO_APPLY_RECONSIDERATION = 24;

		/**
		 * 当事人提交的材料不全 25
		 */
		Integer TO_PARTY_INCOMPLETE_MATERIAL = 25;

		/**
		 * 符合复议受理条件 26
		 */
		Integer TO_CONFORMING_CONDITIONS_RECONSIDERATION = 26;

		/**
		 * 终止复议或驳回复议 27
		 */
		Integer TO_REJECT_RECONSIDERATION = 27;

		/**
		 * to 复议调查取证 28
		 */
		Integer TO_RECONSIDER_DISPOSE = 28;

		/**
		 * to 调解协商 29
		 */
		Integer TO_RECONSIDER_MEDIATE = 29;

		/**
		 * 达成协议 30
		 */
		Integer TO_REACH_AGREEMENT = 30;

		/**
		 * 未达成协议 31
		 */
		Integer TO_NO_REACH_AGREEMENT = 31;

		/**
		 * 不予受理 32
		 */
		Integer TO_OFF_THE_DOCKET = 32;

		/**
		 * 案件受理提交
		 */
		Integer TO_PRE_VERIFY = 33;

		/**
		 * 简易流程归档
		 */
		Integer TO_EASY_FILE = 34;

		/**
		 * 立案审核
		 */
		Integer TO_FILING_VERIFY = 35;

		/**
		 * 立案审批
		 */
		Integer TO_FILING_APPROVE = 36;

		/**
		 * 调查取证提交
		 */
		Integer TO_INVESTIGATING_SEARCHING = 37;

		/**
		 * to重大案件审核
		 */
		Integer TO_MAJOR_CASE_VERIFY = 38;

		/**
		 * 申请（听证申请）
		 */
		Integer TO_HEARING_APPLY = 39;

		/**
		 * to举行听证
		 */
		Integer TO_HOLD_HEARING = 40;

		/**
		 * to案件核审(集体)
		 */
		Integer TO_CASE_VERIFY = 41;

		/**
		 * 提交案件处理
		 */
		Integer TO_SUBMIT_CASE_HANDLE = 42;

		/**
		 * 陈述申辩
		 */
		Integer TO_STATE_PLEAD = 43;

		/**
		 * 申辩（申请案件处理）
		 */
		Integer TO_APPLY_CASE_HANDLE = 44;

		/**
		 * 案件处理（案件处理审核）
		 */
		Integer TO_CASE_HANDLE_VERIFY = 45;

		/**
		 * to案件处理审批
		 */
		Integer TO_CASE_HANDLE_APPROVE = 46;

		/**
		 * to案件处理
		 */
		Integer TO_CASE_HANDLE = 47;

		/**
		 * 结案申请（强制执行审核）
		 */
		Integer TO_ENFORCEMENT_VERIFY = 48;

		/**
		 * to强制执行审批
		 */
		Integer TO_ENFORCEMENT_APPROVE = 49;

		/**
		 * to强制执行
		 */
		Integer TO_ENFORCEMENT = 50;

		/**
		 * to结案申请
		 */
		Integer TO_CLOSED_APPLY = 51;

		/**
		 * 结案审核（结案审核）
		 */
		Integer TO_CLOSED_VERIFY = 52;

		/**
		 * 结案审批（结案审批）
		 */
		Integer TO_CLOSED_APPROVE = 53;

		/**
		 * 结案（案件结案）
		 */
		Integer TO_CASE_CLOSED = 54;

		/**
		 * 一般流程归档
		 */
		Integer TO_COMMONLY_FILE = 55;

		/**
		 * to初步核查
		 */
		Integer TO_PRELIMINARY_CHECK = 56;

		/**
		 * 复议审理
		 */
		Integer TO_ACCEPTANCE_HEAR = 57;

		/**
		 * to复议决定
		 */
		Integer TO_RECONSIDERATION_DECISION = 58;

		/**
		 * 复议决定-结案申请
		 */
		Integer TO_DECISION_CLOSED_APPLY = 59;

		/**
		 * 申请陈述申辩 60
		 */
		Integer TO_APPLY_STATE_PLEAD = 60;

		/**
		 * to 决定复议 61
		 */
		Integer TO_SEQ_RECONSIDERATION_DECISION = 61;

		/**
		 * 驳回复议 62
		 */
		Integer TO_REJECT_RECONSIDER = 62;

		/**
		 * 维持原判 63
		 */
		Integer TO_MAINTAIN_ORIGINAL = 63;

		/**
		 * 终止复议 64
		 */
		Integer CLOSE_RECONSIDER_REVIEW = 64;

		/**
		 * 暂停审理 65(from复议下达)
		 */
		Integer STOP_REVIEW = 65;

		/**
		 * to 初步核查审核 66
		 */
		Integer TO_RECONSIDER_CENSOR_AUDITING = 66;

		/**
		 * to 初步核查审批 67
		 */
		Integer TO_RECONSIDER_CENSOR_APPROVE = 67;

		/**
		 * 不予受理审核提交 68
		 */
		Integer TO_AUDITING_OFF_THE_DOCKET = 68;

		/**
		 * 不予受理审批提交 69
		 */
		Integer TO_APPROVE_OFF_THE_DOCKET = 69;

		/**
		 * 复议受理审核提交 70
		 */
		Integer TO_AUDITING_RECONSIDERATION = 70;

		/**
		 * 复议受理审批提交 71
		 */
		Integer TO_APPROVE_RECONSIDERATION = 71;

		/**
		 * from 复议调查取证 TO 复议决定 72
		 */
		Integer FROM_RECONSIDER_DISPOSE_TO_RECONSIDERATION_DECISION = 72;
		/**
		 * 复议决定审核
		 */
		Integer TO_RECONSIDERATION_DECISION_AUDITING = 73;
		/**
		 * 复议决定审批
		 */
		Integer TO_RECONSIDERATION_DECISION_APPROVE = 74;
		/**
		 * 复议下达通知
		 */
		Integer TO_NOTICE_OF_RECONSIDERATION = 75;
		/**
		 * 驳回复议审核
		 */
		Integer TO_REJECT_RECONSIDER_AUDITING = 76;
		/**
		 * 驳回复议审批
		 */
		Integer TO_REJECT_RECONSIDER_APPROVE = 77;
		/**
		 * 驳回行政复议
		 */
		Integer TO_DISMISSAL_OF_ADMINISTRATIVE_RECONSIDERATION = 78;
		/**
		 * 责令恢复审理
		 */
		Integer TO_COMMAND_RESUME_TRIAL = 79;
		/**
		 * 终止复议审核
		 */
		Integer TO_TERMINATE_RECONSIDERATION_AUDITING = 80;
		/**
		 * 终止复议审批
		 */
		Integer TO_TERMINATE_RECONSIDERATION_APPROVE = 81;
		/**
		 * 暂停审理审核
		 */
		Integer TO_STOP_REVIEW_AUDITING = 82;
		/**
		 * 暂停审理审批
		 */
		Integer TO_STOP_REVIEW_APPROVE = 83;
		/**
		 * 暂停审理
		 */
		Integer TO_STOP_REVIEW = 84;
		/**
		 * 恢复审理
		 */
		Integer TO_RESUME_TRIAL = 85;

		/**
		 * 初步核查审批 提交 核查结果下达
		 */
		Integer TO_VERIFICATION_RESULTS_ARE_REACHED = 86;

		/**
		 * 责令恢复审理 提交 责令恢复审核
		 */
		Integer TO_ORDER_TO_RESTORE_AUDITING = 87;

		/**
		 * 责令恢复审核 提交 责令恢复审批
		 */
		Integer TO_ORDER_TO_RESTORE_APPROVE = 88;

		/**
		 * 责令恢复审批 提交 责令恢复通知
		 */
		Integer TO_ORDER_RECOVERY_NOTICE = 89;

		/**
		 * 责令恢复通知 提交 复议受理
		 */
		Integer FROM_ORDER_RECOVERY_NOTICE_TO_FYSL = 90;

		/**
		 * 立案 提交 调查取证
		 */
		Integer FROM_LA_TO_DCQZ = 91;

		/**
		 * 立案 提交 立案审核
		 */
		Integer FROM_LA_TO_LASH = 92;

		/**
		 * 先行告知 提交 申请陈述申辩
		 */
		Integer FROM_XXGZ_TO_SQCSSB = 93;

		/**
		 * 先行告知 提交 申请案件处理
		 */
		Integer FROM_XXGZ_TO_SQANCL = 94;

		/**
		 * 申请案件处理 提交 案件处理
		 */
		Integer FROM_SQAJCL_TO_ANCL = 95;

		/**
		 * 申请案件处理 提交 案件处理审核
		 */
		Integer FROM_SQAJCL_TO_ANCLSH = 96;

		/**
		 * 申请强制执行 提交 强制执行
		 */
		Integer FROM_SQQZZX_TO_QZZX = 97;

		/**
		 * 申请强制执行 提交 强制执行审核
		 */
		Integer FROM_SQQZZX_TO_QZZXSH = 98;

		/**
		 * 结案申请 提交 结案审核
		 */
		Integer FROM_JASQ_TO_JASH = 99;

		/**
		 * 结案申请 提交 案件结案
		 */
		Integer FROM_JASQ_TO_AJJA = 100;

		/**
		 * 法制审核意见--->集体讨论
		 */
		Integer FROM_FZSH_TO_JTTL = 200;

		/**
		 * 法制审核意见--->申请案件处理
		 */
		Integer FROM_FZSH_TO_AJCL = 201;

		/**
		 * 听证告知-->听证申请
		 */
		Integer FROM_TZGZ_TO_TZSQ = 202;

		/**
		 * 听证告知-->申请案件处理
		 */
		Integer FROM_TZGZ_TO_AJCL = 203;

		/**
		 * 先行告知-->听证告知
		 */
		Integer FROM_SXGZ_TO_TZGZ = 204;

		/**
		 * 陈述申辩-->听证告知
		 */
		Integer FROM_CSSB_TO_TZGZ = 205;

		/**
		 * 陈述申辩-->申请案件处理
		 */
		Integer FROM_CSSB_TO_AJCL = 206;

		/**
		 * 撤销立案审核
		 */
		Integer TO_CXLASH = 207;

		/**
		 * 撤销立案审批
		 */
		Integer TO_CXLASP = 209;

		/**
		 * 撤销立案归档
		 */
		Integer TO_CXLAGD = 210;

	}

	/**
	 * 流程Key
	 * 
	 * @author ebusu
	 *
	 */
	public interface FlowKey {
		/**
		 * 案件流程
		 */
		String CASE = "case";

		/**
		 * 日常检查
		 */
		String DAY_CHECK = "dayCheck";

		/**
		 * 专项检查
		 */
		String PRO_CHECK = "proCheck";
	}

	/**
	 * 字典表大类
	 * 
	 * @author liuzhih
	 *
	 */
	public interface DictType {

		/**
		 * 语言 1001
		 */
		String LANG = "1001";

		/**
		 * 案源 1002
		 */
		String CASE_RES = "1002";

		/**
		 * 案件状态 1500
		 */
		String CASE_STATUS = "1500";

		/**
		 * 当事人类型 1600
		 */
		String PARTY_TYPE = "1600";

		/**
		 * 性别 1700
		 */
		String SEX = "1700";

		/**
		 * 日常检查状态1502
		 */
		String DAILY_CHECK_STATUS = "1502";

		/**
		 * 专项检查类型1501
		 */
		String CHECK_STATUS = "1501";
		/**
		 * 日常检查类型1200
		 */
		String CHECK_MODE = "1200";

		/**
		 * 预警级别
		 */
		String WARN_LEVEL = "1900";

		/**
		 * 一般流程处理方式 2000
		 */
		String YBCLFS = "2000";

	}

	/**
	 * 预警处理
	 * 
	 * @author ebusu
	 *
	 */

	public interface YuJChuL {
		/**
		 * 预警未处理 2
		 */
		String WCL = "2";

		/**
		 * 预警已处理 1
		 */
		String YCL = "1";

	}

	/**
	 * 角色
	 * 
	 * @author ebusu
	 *
	 */
	public interface Role {

		/**
		 * 有执法权力的人员 26
		 */
		Integer LAW_ENFORCEMENT_OFFICIALS = 26;

		/**
		 * 审核角色,对执法人员提交的案件信息进行审核处理 27
		 */
		Integer OPINION = 27;

		/**
		 * 审批角色,对审核人员提交的案件信息进行审批处理 28
		 */
		Integer APPROVE = 28;

		/**
		 * 法制受理人
		 */
		Integer FzCASE = 31;

		/**
		 * 法制审核人
		 */
		Integer FzOPINION = 29;

		/**
		 * 法制审批人
		 */
		Integer FzApprove = 37;

		/**
		 * 复议核查人
		 */
		Integer FyOPINION = 34;

		/**
		 * 复议审理人
		 */
		Integer FyApprove = 35;

		/**
		 * 听证受理人
		 */
		Integer TZSL = 38;

		/**
		 * 复议承办人
		 */
		Integer FYCB = 41;

		/**
		 * 复议科长
		 */
		Integer FYKZ = 39;

		/**
		 * 复议主任
		 */
		Integer FYZR = 40;

		/**
		 * 46 市领导
		 */
		Integer SLD = 46;

		/**
		 * 47 市法制办领导
		 */
		Integer SFZBLD = 47;

		/**
		 * 48 市法制办监督人员
		 */
		Integer SFZBJDRY = 48;

		/**
		 * 52 委办局监督人员
		 */
		Integer WBJJDRY = 52;

	}

	/**
	 * 日常检查、专项检查状态
	 * 
	 * @author ebusu
	 *
	 */
	public interface CheckStatus {
		/**
		 * 暂存
		 */
		Integer CHECK_ZC = 0;

		/**
		 * 下达通知 1
		 */
		Integer NOTIFICATIONS = 1;

		/**
		 * 现场检查 2
		 */
		Integer LOCATE_CHECK = 2;

		/**
		 * 责令整改 3
		 */
		Integer ORDER_RECTIFICATION = 3;

		/**
		 * 案件受理 4
		 */
		Integer CASE_ADMISS = 4;

		/**
		 * 申请抽样取证 5
		 */
		Integer APPLICATION_FOR_SAMPLING = 5;

		/**
		 * 领导审批 6
		 */
		Integer LEADER_APPROVE = 6;

		/**
		 * 抽样取证 7
		 */
		Integer SAMPLING_EVIDENCE = 7;

		/**
		 * 结束 8
		 */
		Integer FINISHED = 8;

		/**
		 * 领导审核 9
		 */
		Integer LEADER_VERIFY = 9;
	}

	/**
	 * 日常、专项检查处理方式
	 * 
	 * @author ebusu
	 */
	public interface CheckDailyFlow {

		/**
		 * 责令整改 101
		 */
		Integer ZLZG = 101;

		/**
		 * 行政处罚 102
		 */
		Integer XZCF = 102;

		/**
		 * 申请抽样取证 103
		 */
		Integer SQCYQZ = 103;

		/**
		 * 未发现违法行为 104
		 */
		Integer WFXWFXW = 104;

		/**
		 * 未整改 105
		 */
		Integer WZG = 105;

		/**
		 * 已整改 106
		 */
		Integer ZG = 106;

		/**
		 * 发现问题 107
		 */
		Integer FXWT = 107;

		/**
		 * 未发现问题 108
		 */
		Integer WFXWT = 108;

		/**
		 * 同意 109
		 */
		Integer TY = 109;

		/**
		 * 不同意 110
		 */
		Integer BTY = 110;

		/**
		 * to 下达通知节点 111
		 */
		Integer TO_XDTZ = 111;

		/**
		 * to 现场检查 112
		 */
		Integer TO_XCJC = 112;

		/**
		 * to 结束 from 案件处理 113
		 */
		Integer TO_JS_FROM_AJCL = 113;

		/**
		 * to 领导审核 114
		 */
		Integer LDSH = 114;

		/**
		 * to 领导审批 115
		 */
		Integer LDSP = 115;
	}

	/**
	 * 有效标记通用 1 有效 0 无效
	 * 
	 * @author ebusu
	 */
	public interface Effect {

		/**
		 * 无效 0
		 */
		Integer NOT_EFFECT = 0;

		/**
		 * 有效 1
		 */
		Integer EFFECT = 1;

	}

	/**
	 * 流程类型
	 * 
	 * @author liuzhih
	 */
	public interface FlowType {

		/**
		 * 简易流程案件 1
		 */
		String SIMPLE_CASE = "1";

		/**
		 * 一般案件流程 2
		 */
		String NORMAL_CASE = "2";

		/**
		 * 重大案件流程 3
		 */
		String MAJOR_CASE = "3";

		/**
		 * 专项检查流程 4
		 */
		String SPECIAL_CHECK = "4";

		/**
		 * 日常检查流程 5
		 */
		String DAILY_CHECK = "5";

		/**
		 * 案件6
		 */
		String CASE = "6";

		/**
		 * 待办案件 200
		 */
		String NEED_CASE = "200";
	}

	/**
	 * APP案件上报类型
	 * 
	 * @author zhangjingy
	 */
	public interface CaseType {
		/**
		 * 案件执行 0
		 */
		Integer CASE_IMPLEMENT = 0;

		/**
		 * 案件上报 1
		 */
		Integer CASE_REPORT = 1;
	}

	/**
	 * APP审批类型
	 * 
	 * @author zhangjingy
	 */
	public interface CommentType {
		/**
		 * 立案审批 0
		 */
		Integer CASE_FILING = 0;

		/**
		 * 处理审批 1
		 */
		Integer CASE_DEAL = 1;

		/**
		 * 结案审批 2
		 */
		Integer CASE_CLOSED = 2;
	}

	/**
	 * APP当事人类型
	 * 
	 * @author zhangjingy
	 */
	public interface PartyType {
		/**
		 * 个人 1
		 */
		Integer PERSON = 1;

		/**
		 * 公司 2
		 */
		Integer COMPANY = 2;

	}

	/**
	 * APP审核审批类型
	 * 
	 * @author zhangjingy
	 */
	public interface SHSPType {

		/**
		 * 立案审核1
		 */
		Integer LASH = 1;

		/**
		 * 立案审批 2
		 */
		Integer LASP = 2;

		/**
		 * 处理审核 3
		 */
		Integer CLSH = 3;

		/**
		 * 处理审批 4
		 */
		Integer CLSP = 4;

		/**
		 * 结案审核 5
		 */
		Integer JASH = 5;

		/**
		 * 结案审批 6
		 */
		Integer JASP = 6;

		/**
		 * 执法人员 26
		 */
		Integer ZFPERSON = 27;

		/**
		 * 审批负责人 28
		 */
		Integer SPPERSON = 28;

	}

	/**
	 * 数据来源
	 * 
	 * @author liuzh
	 */
	public interface DataSource {

		/**
		 * PC端 1
		 */
		String PC = "1";

		/**
		 * app 2
		 */
		String APP = "2";
		
		/**
		 * 共享接口
		 */
		String SHARE_API = "3";
	}

	/**
	 * 案源 - 对应的检查类型
	 * 
	 * @author ebusu
	 */
	public interface CaseSourceCheckType {

		/**
		 * 日常检查 6
		 */
		String DAILY = "6";

		/**
		 * 专项检查 5
		 */
		String PRO = "5";
	}

	/**
	 * 预警类型
	 * 
	 * @author ebusu
	 */
	public interface WarnType {

		/**
		 * 执法时限预警 1
		 */
		String SXYJ = "1";

		/**
		 * 基础信息预警 4
		 */

		String JCXXYJ = "4";
		/**
		 * 执法过程预警 2
		 */
		String GCYJ = "2";

		/**
		 * 实时预警 3
		 */
		String SSYJ = "3";
	}

	/**
	 * 执法主体执法性质
	 * 
	 * @author liuzh
	 */
	public interface LawType {
		/**
		 * 执法 1
		 */
		String ZF = "1";
		/**
		 * 监督 2
		 */
		String JD = "2";
	}

	/**
	 * 执法区划级别
	 * 
	 * @author liuzh
	 */
	public interface AreaLevel {
		/**
		 * 洲级 0
		 */
		String ZJ = "0";

		/**
		 * 国家级 1
		 */
		String GJJ = "1";

		/**
		 * 省级行政区
		 */
		String SJ = "2";

		/**
		 * 地级行政区 3
		 */
		String DJ = "3";

		/**
		 * 县级行政区 4
		 */
		String XJ = "4";

		/**
		 * 乡级行政区 5
		 */
		String XIJ = "5";

		/**
		 * 村级行政区 6
		 */
		String CJ = "6";

		/**
		 * 组级行政区 7
		 */
		String ZUJ = "7";

	}

	/**
	 * 主体性质
	 * 
	 * @author 武跃忠
	 *
	 */
	public interface DeptProperty {

		/**
		 * 委托单位
		 */
		Integer WTDW = 2;

		/**
		 * 行政机关
		 */
		Integer XZJG = 3;

		/**
		 * 授权单位
		 */
		Integer SQDW = 1;
	}

	/**
	 * 预警星级
	 * 
	 * @author 武跃忠
	 *
	 */
	public interface WarnStar {
		/**
		 * 1 1星级
		 */
		String ONE = "1";

		/**
		 * 2 2星级
		 */
		String TWO = "2";

		/**
		 * 3 3星级
		 */
		String THREE = "3";

		/**
		 * 4 4星级
		 */
		String FOUR = "4";

		/**
		 * 5 5星级
		 */
		String FIVE = "5";
	}

	/**
	 * 实时预警指标
	 * 
	 * @author 张景越
	 *
	 */
	public interface Type {
		/**
		 * 9001 案件实时预警
		 * 
		 */
		Integer WARN_CASE = 9001;

		/**
		 * 9002 日常检查实时预警
		 */
		Integer WARN_DAILY = 9002;

		/**
		 * 9003 专项检查实时预警
		 */
		Integer WARN_LSSUED = 9003;

		/**
		 * 9004 重大案件实时预警
		 */
		Integer WARN_MAJOR_CASE = 9004;

	}

	/**
	 * 处罚类别
	 * 
	 * @author 张景越
	 *
	 */
	public interface PunishType {

		/**
		 * 1 警告
		 */
		String JG = "1";

		/**
		 * 2 罚款
		 */
		String FK = "2";

		/**
		 * 3 没收违法所得，没收非法财物
		 */
		String MS = "3";

		/**
		 * 4 责令停产停业
		 */
		String ZLTYTC = "4";

		/**
		 * 5 暂扣或者吊销许可证，暂扣或者吊销执照
		 */
		String ZQHDX = "5";

		/**
		 * 6 行政拘留
		 */
		String XZJL = "6";

		/**
		 * 7 法律、行政法规规定的其他行政处罚
		 */
		String QTCF = "7";

	}

	/**
	 * 评查记录状态
	 *
	 * @author liuzhih
	 */
	public interface ReviewStatus {
		/**
		 * 暂存 1
		 */
		String ZC = "1";

		/**
		 * 一级待审批 2
		 */
		String YJDSP = "2";

		/**
		 * 二级待审批 3
		 */
		String EJDSP = "3";

		/**
		 * 审批完成 4
		 */
		String SHWC = "4";
	}

	/**
	 * 消息推送- 消息推送的内容
	 * 
	 * @author wxh
	 */
	public interface PushMessage {
		
		/**
		 * 消息推送跳转界面类型
		 * 1:跳转到领导端的案件详情界面
		 * 2:跳转到预警的详情界面
		 * 3:跳转到执法人员的专项检查的详情界面
		 * 4:跳转到执法人员的案件详情界面
		 * 5:跳转到领导的日常详情界面
		 * 6:跳转到领导的专项详情界面
		 * 7:跳转到执法人员的的日常详情界面
		 */

		String CASE_TYPE_1 = "1";
		String CASE_TYPE_2 = "2";
		String CASE_TYPE_3 = "3";
		String CASE_TYPE_4 = "4";
		String CASE_TYPE_5 = "5";
		String CASE_TYPE_6 = "6";
		String CASE_TYPE_7 = "7";
		String CASE_TYPE_8 = "8";
		
		/**
		 * 案件审核
		 */
		String AUDIT = "您有一条案件需要审理，请点击查看...";

		/**
		 * 案件审批
		 */
		String APPROVAL = "您有一条案件需要审理，请点击查看...";


		/**
		 * 执法人员的案件通过审核审批
		 */
		String ENFORCE_PERSON = "您有一条案件通过审核审批，请点击查看...";
		
	
		
		/**
		 * 日常审核
		 */
		String DAILY_AUDIT = "您有一条日常检查需要审理，请点击查看...";

		/**
		 * 日常审批
		 */
		String DAILY_APPROVAL = "您有一条案件日常检查需要审理，请点击查看...";
		
		/**
		 * 执法人员的日常检查通过审核审批
		 */
		String DAILY_ENFORCE_PERSON = "您有一条日常检查通过审核审批，请点击查看...";
		
		
		/**
		 * 专项审核
		 */
		String SPECIAL_AUDIT = "您有一条专项检查需要审理，请点击查看...";
		/**
		 * 专项审批
		 */
		String SPECIAL_APPROVAL = "您有一条案件专项检查需要审理，请点击查看...";
		/**
		 * 执法人员的专项检查通过审核审批
		 */
		String SPECIAL_ENFORCE_PERSON = "您有一条专项检查通过审核审批，请点击查看...";

	}
	
	/**
	 * 数据同步常量
	 * 
	 * @author wxh
	 */
	public interface ShareSyncCode {
		/**
		 * 	1. 行政区划数据
		 */
		String SYNC_AREA = "1";
		
		/**
		 * 	2. 执法主体数据
		 */
		String SYNC_DEPT = "2";
		
		/**
		 * 	3. 执法人员数据
		 */
		String SYNC_DEPT_PERSON = "3";
		
		/**
		 * 	4. 权责数据
		 */
		String SYNC_POTENCE = "4";
		
		/**
		 * 	5. 法律数据
		 */
		String SYNC_LAW = "5";
	}

}
