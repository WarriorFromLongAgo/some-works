//字典常量类
var dict=
{
		//语言
		lang:1001,
		//案源
		caseSource:1002,
		//案件类型
		caseType:1003,
		//当事人类型
		partyTtype:1600,
		//性别
		sex:1700,
		//处理方式
		dealType:1100,
		//检查方式
		checkWayType:1800,
		//时限方式
		limitType:1810,
		
		checkMode:1200,
		//是否生效
		isEffect:1713,
		//案件状态
		caseStatus:1500,
		//处理方式
		dealType:1100,
		//流程类型
		flowType:1802,
		//是否必填
		needAdd:1801,
		//检查类型
		checkMode:1200,
		//检查类型
		punishType: 2060,
		// 执法(监督)类型
		cert_type : 1715,
		// 执法类型
		cert_category : 1716
}

var caseStatus=
{
		/**
		 *案件暂存
		 */
	    AJZC:11,
		/**
		 * 初步核实
		 */
		CBHS:12,
		/**
		 * 现场处理
		 */
	    XCCL:13,
		/**
		 *案件立案
		 */
	    AJLA:21,
		/**
		 *立案审批
		 */
		LASP:22,
		/**
		 *调查取证
		 */
	     DCQZ:23,
	     /**
		  *先行告知 
		  */
		 XXGZ:24,
		 /**
		  * 申请案件处理
		  */
		 SQAJCL:25,
		 /**
		  *听证申请
		  */
		 TZSQ:26,
		 /**
		  *听证受理
		  */
		 TZSL:27,
		 /**
		  *举行听证
		  */
		 JXTZ:28,
		 /**
		  *重大案件受理
		  */
		 ZDAJSL:29,
		 /**
		  *重大案件审核
		  */
		 ZDAJSH:30,
		 /**
		  *法制审核
		  */
		 FZSH:31,
		 /**
	      * 立案审核
		  */
		 LASH:35,
		 /**
		  * 案件核审
		  */
		 AJHS:40,
		 /**
		  * 申请复议
		  */
		 SQFY:42,
		 /**
		  * 初步核查 
		  */
		 CBSC:43,
		 /**
		  * 复议受理
		  */
		 FYSL:44,
		 /**
		  * 复议审理
		  */
		 FYSLL:45,
		 /**
		  * 调解协商
		  */
		 TJXS:46,
		 /**
		  * 复议处理
		  */
		 FYCL:47,
		 /**
		  * 复议决定
		  */
		 FYJD:48,
		 /**
		  * 结案审核
		  */
		 JASH:49,
		 /**
		  * 结案审批
		  */
		 JASP:50,
		 /**
		  * 强制执行审核
		  */
		 QZZXSH:51,
		 /**
		  * 强制执行审批
		  */
		 QZZXSP:52,
		 /**
		  * 案件处理审核
		  */
		 AJCLSH:54,
		 /**
		  * 案件处理审批
		  */
		 AJCLSQ:55,
		 /**
		  * 案件结案
		  */
		 ANJA:14,
		 /**
		  * 案件归档
		  */
		 ANGD:90,
		 /**
		  *集体讨论 32
		  */
		 JTTL:32,
		 /**
		  *听证告知
		  */
		  TZGZ:34,
		 /**
		  * 一般案件结案
		  */
		 YBANJA:36,
		 /**
		  * 案件合议 
		  */
		 AJHY:37,
		 /**
		  * 陈述申辩
		  */
	     CSSB:38,
	     /**
		   * 结案申请
		   */
		 JASQ:39,
		 /**
		  * 申请强制执行
		  */
		 SQQZZX:41,
	     /**
		  * 强制执行
		  */
		 QZZX:53,
	     /**
		  * 案件处理 
		  */
		 AJCL:56,
		 /**
		  * 申请陈述申辩 57
		  */
		SQCSSB : 57,
		/**
		 * 不予受理审核 58
		 */
		BYSLSH : 58,
		
		/**
		 * 不予受理审批 59
		 */
		 BYSLSQ : 59,
		
		/**
		 * 复议受理审核 60
		 */
		 FYSLSH : 60,
		
		/**
		 * 复议受理审批 61
		 */
		 FYSLSQ : 61,
		
		/**
		 * 复议调查取证 62
		 */
		 FYDCQZ : 62,
		
		/**
		 * 复议决定审核 63
		 */
		 FYDJSH : 63,
		
		/**
		 * 复议决定审批 64
		 */
		 FYDJSQ : 64,
		
		/**
		 * 复议下达通知 65
		 */
		 FYXDTZ : 65,
		
		/**
		 * 驳回复议审核 66
		 */
		 BHFYSH : 66,
		
		/**
		 * 驳回复议审批67
		 */
		 BHFYSP : 67,
		
		/**
		 * 驳回行政复议 68
		 */
		 BHXZFY : 68,
		
		/**
		 * 责令恢复审理 69
		 */
		 ZLHFSL : 69,
		
		/**
		 * 终止复议审核 70
		 */
		 ZZFYSH : 70,
		
		/**
		 * 终止复议审批 71
		 */
		 ZZFYSP : 71,
		
		/**
		 * 暂停审理审核 72
		 */
		 ZTSLSH : 72,
		
		/**
		 * 暂停审理审批
		 */
		 ZTSLSP : 73,
		
		/**
		 * 暂停审理 74
		 */
		 ZTSL : 74,
		
		/**
		 * 恢复审理 75
		 */
		 HFSL : 75,
		
		/**
		 * 初步核查审核 76
		 */
		 CBHCSH : 76,
		
		/**
		 * 初步核查审批 77
		 */
		 CBHCSP : 77,
		 
		/**
		 * 核查结果下达 78
		 */
		 HCJGXD : 78,
		
		/**
		 * 不予受理通知 79
		 */
		 BYSLTZ : 79,
		
		/**
		 * 责令恢复审核 80
		 */
		 ZLHFSH : 80,
		
		/**
		 * 责令恢复审批 81
		 */
		 ZLHFSP : 81,
		
		/**
		 * 责令恢复通知 82
		 */
		 ZLHFTZ : 82
}

//日常检查、专项检查状态
var checkStatus = 
{
		/**
		 * 暂存
		 */
		CHECK_ZC:0,
		/**
		 * 下达通知
		 */
		NOTIFICATIONS:1,
		/**
		 * 现场检查
		 */
		LOCATE_CHECK:2,
		/**
		 * 责令整改
		 */
		ORDER_RECTIFICATION:3,
		/**
		 * 案件受理
		 */
		CASE_ADMISS:4,
		/**
		 * 申请抽样取证 
		 */
		APPLICATION_FOR_SAMPLING:5,
		/**
		 * 领导审批 
		 */
		LEADER_APPROVE:6,
		/**
		 * 抽样取证
		 */
		SAMPLING_EVIDENCE:7,
}

var resultCode = 
{
		/**
		 * 服务器内部错误
		 */
		INTERNAL_SERVER_ERROR : 500,
		/**
		 * 表单验证不通过
		 */
		FIELD_ERROR : 400,
		/**
		 * 程序运行过程中出现异常
		 */
		BUSI_ERROR : 405,
		/**
		 * token为空
		 */
		TOKEN_IS_NULL : 406,
		/**
		 * 成功
		 */
	    SUCCESS : 0,
	    /**
		 * 失败
		 */
		ERROR : -1,
}

//流程类型
var flowType = 
{
		/**
		 * 简易流程案件 1
		 */
	     SIMPLE_CASE:1,
	     
		 /**
		 * 一般案件流程 2
		 */
	     NORMAL_CASE : 2,
		
		/**
		 * 重大案件流程 3
		 */
		MAJOR_CASE: 3,

		/**
		 * 专项检查流程 4
		 */
		SPECIAL_CHECK :4,
		
		/**
		 * 日常检查流程 5
		 */
		DAILY_CHECK :5,
}

//流程处理方式
var FLOW={
		/**
		 * 不予处理 1
		 */
		NOT_DEAL : 1,

		/**
		 * 立案 2
		 */
		PLACE_CASE : 2,

		/**
		 * 现场处理  3
		 */ 
		LOCATE_PRO : 3,

		/**
		 * 给与警告 4
		 */
		TO_WARNING : 4,

		/**
		 * 给与处罚 5
		 */
		TO_PUNISH : 5,

		/**
		 * 不予立案 6
		 */
		NOT_FILING : 6,

		/**
		 * 调查取证 7 
		 */
		TO_SEARCH_PROOF : 7,

		/**
		 * 重新立案 8
		 */
		RE_FILING : 8,

		/**
		 * 先行告知 9
		 */
		TO_INFORMING : 9,

		/**
		 * 不予处罚 10
		 */
		NOT_PUNISH : 10,

		/**
		 * 撤销立案 11
		 */
		REVOKE_FILING : 11,

		/**
		 * 听证告知 12
		 */
		HEAR_NOTIFY  : 12,

		/**
		 * 重大案件 13
		 */
		MAJOR_CASE  : 13,

		/**
		 * 听证受理 14
		 */
		HEAR_ADMISS  : 14,

		/**
		 * 三天内未申请，自动转到案件受理 15
		 */
		AUTO_CASE_ADMISS  : 15,

		/**
		 * 不集体讨论 16
		 */
		NOT_GROUP_DISCUSSION  : 16,

		/**
		 * 集体讨论 17
		 */
		GROUP_DISCUSSION  : 17,

		/**
		 * 材料不全 18
		 */
		INCOMPLETE_MATERIAL  : 18,

		/**
		 * 法制审核意见 19
		 */
		AUDIT_OPINION  : 19,

		/**
		 * 听证 20
		 */
		TO_HEAR  : 20,

		/**
		 * 申请复审 21
		 */
		TO_APPLICATION_REEXAMINATION  : 21,

		/**
		 * 接受处罚 22
		 */
		TO_ACCEPT_PUNISHMENT : 22,

		/**
		 * 不接受处罚不申请复议 23
		 */
		NO_APPLY_RECONSIDERATION_ACCEPT_PUNISHMENT  : 23,

		/**
		 * 申请复议 24
		 */
		TO_APPLY_RECONSIDERATION : 24,

		/**
		 * 当事人提交的材料不全  25
		 */
		TO_PARTY_INCOMPLETE_MATERIAL : 25,

		/**
		 * 符合复议受理条件 26
		 */
		TO_CONFORMING_CONDITIONS_RECONSIDERATION : 26,

		/**
		 * 终止复议 27
		 */
		TO_REJECT_RECONSIDERATION : 27,

		/**
		 * to 复议调查取证 28
		 */
		TO_RECONSIDER_DISPOSE : 28,

		/**
		 * to 调解协商 29
		 */
		TO_RECONSIDER_MEDIATE : 29,

		/**
		 * 达成协议 30
		 */
		TO_REACH_AGREEMENT : 30,

		/**
		 * 未达成协议 31
		 */
		TO_NO_REACH_AGREEMENT : 31,

		/**
		 * 不予受理 32
		 */
		TO_OFF_THE_DOCKET : 32,

		/**
		 * 案件受理提交
		 */
		TO_PRE_VERIFY : 33,

		/**
		 * 简易流程归档
		 */
		TO_EASY_FILE : 34,

		/**
		 *立案审核
		 */
		TO_FILING_VERIFY : 35,

		/**
		 * 立案审批
		 */
		TO_FILING_APPROVE : 36,

		/**
		 * 调查取证提交
		 */
		TO_INVESTIGATING_SEARCHING : 37,

		/**
		 * to重大案件审核
		 */
		TO_MAJOR_CASE_VERIFY : 38,

		/**
		 * 申请（听证申请）
		 */
		TO_HEARING_APPLY : 39,

		/**
		 * to举行听证
		 */
		TO_HOLD_HEARING : 40,

		/**
		 * to案件核审(集体)
		 */
		TO_CASE_VERIFY : 41,

		/**
		 * 提交案件处理
		 */
		TO_SUBMIT_CASE_HANDLE : 42,

		/**
		 * 陈述申辩
		 */
		TO_STATE_PLEAD : 43,

		/**
		 * 申辩（申请案件处理）
		 */
		TO_APPLY_CASE_HANDLE : 44,

		/**
		 * 案件处理（案件处理审核）
		 */
		TO_CASE_HANDLE_VERIFY : 45,

		/**
		 * to案件处理审批
		 */
		TO_CASE_HANDLE_APPROVE : 46,

		/**
		 * to案件处理
		 */
		TO_CASE_HANDLE : 47,

		/**
		 * 结案申请（强制执行审核）
		 */
		TO_ENFORCEMENT_VERIFY : 48,

		/**
		 * to强制执行审批
		 */
		TO_ENFORCEMENT_APPROVE : 49,

		/**
		 * to强制执行
		 */
		TO_ENFORCEMENT : 50,

		/**
		 * to结案申请
		 */
		TO_CLOSED_APPLY : 51,

		/**
		 * 结案审核（结案审核）
		 */
		TO_CLOSED_VERIFY : 52,

		/**
		 * 结案审批（结案审批）
		 */
		TO_CLOSED_APPROVE : 53,

		/**
		 * 结案（案件结案）
		 */
		TO_CASE_CLOSED : 54,

		/**
		 * 一般流程归档
		 */
		TO_COMMONLY_FILE : 55,

		/**
		 * to初步核查
		 */
		TO_PRELIMINARY_CHECK : 56,

		/**
		 * 复议审理
		 */
		TO_ACCEPTANCE_HEAR : 57,

		/**
		 * to复议决定
		 */
		TO_RECONSIDERATION_DECISION : 58,

		/**
		 * 复议决定-结案申请
		 */
		TO_DECISION_CLOSED_APPLY : 59,
		
		/**
		 * 申请陈述申辩 60
		 */
		TO_APPLY_STATE_PLEAD : 60,
		
		/**
		 * to 决定复议 61
		 */
		TO_SEQ_RECONSIDERATION_DECISION : 61,
		
		/**
		 * 驳回复议 62
		 */
		TO_REJECT_RECONSIDER : 62,
		
		/**
		 * 维持原判 63
		 */
		TO_MAINTAIN_ORIGINAL : 63,
		
		/**
		 * 终止复议 64
		 */
		CLOSE_RECONSIDER_REVIEW : 64,
		
		/**
		 * 暂停审理 65
		 */
		STOP_REVIEW : 65,
		
		/**
		 * TO 初步核查审核 66
		 */
		TO_RECONSIDER_CENSOR_AUDITING : 66,
		
		/**
		 * TO 初步核查审批 67
		 */
		TO_RECONSIDER_CENSOR_APPROVE : 67,
		
		/**
		 * 不予受理审核提交 68
		 */
		TO_AUDITING_OFF_THE_DOCKET : 68,
		
		/**
		 * 不予受理审批提交 69
		 */
		TO_APPROVE_OFF_THE_DOCKET : 69,
		
		/**
		 * 复议受理审核提交 70
		 */
		TO_AUDITING_RECONSIDERATION : 70,
		
		/**
		 * 复议受理审批提交 71
		 */
		TO_APPROVE_RECONSIDERATION : 71,
		
		/**
		 * from 复议调查取证 TO 复议决定 72
		 */
		FROM_RECONSIDER_DISPOSE_TO_RECONSIDERATION_DECISION : 72,
		/**
		 * 复议决定审核
		 */
		TO_RECONSIDERATION_DECISION_AUDITING : 73,
		/**
		 * 复议决定审批
		 */
		TO_RECONSIDERATION_DECISION_APPROVE : 74,
		/**
		 * 复议下达通知
		 */
		TO_NOTICE_OF_RECONSIDERATION : 75,
		/**
		 * 驳回复议审核
		 */
		TO_REJECT_RECONSIDER_AUDITING: 76,
		/**
		 * 驳回复议审批
		 */
		TO_REJECT_RECONSIDER_APPROVE: 77,
		/**
		 * 驳回行政复议
		 */
		TO_DISMISSAL_OF_ADMINISTRATIVE_RECONSIDERATION: 78,
		/**
		 * 责令恢复审理
		 */
		TO_COMMAND_RESUME_TRIAL: 79,
		/**
		 * 终止复议审核
		 */
		TO_TERMINATE_RECONSIDERATION_AUDITING: 80,
		/**
		 * 终止复议审批
		 */
		TO_TERMINATE_RECONSIDERATION_APPROVE: 81,
		/**
		 * 暂停审理审核
		 */
		TO_STOP_REVIEW_AUDITING: 82,
		/**
		 * 暂停审理审批
		 */
		TO_STOP_REVIEW_APPROVE: 83,
		/**
		 * 暂停审理
		 */
		TO_STOP_REVIEW: 84,
		/**
		 * 恢复审理
		 */
		TO_RESUME_TRIAL : 85,
		/**
		 * 初步核查审批 提交 核查结果下达
		 */
		TO_VERIFICATION_RESULTS_ARE_REACHED : 86,
		
		/**
		 * 责令恢复审理 提交 责令恢复审核
		 */
		TO_ORDER_TO_RESTORE_AUDITING : 87,
		
		/**
		 * 责令恢复审核 提交 责令恢复审批
		 */
		TO_ORDER_TO_RESTORE_APPROVE : 88,
		
		/**
		 * 责令恢复审批 提交 责令恢复通知
		 */
		TO_ORDER_RECOVERY_NOTICE : 89,
		
		/**
		 * 责令恢复通知 提交 复议受理
		 */
		FROM_ORDER_RECOVERY_NOTICE_TO_FYSL : 90,
		
		/**
		 * 立案 提交 调查取证
		 */
		 FROM_LA_TO_DCQZ : 91,
		
		/**
		 * 立案 提交 立案审核
		 */
		 FROM_LA_TO_LASH : 92,
		
		/**
		 * 先行告知 提交 申请陈述申辩
		 */
		FROM_XXGZ_TO_SQCSSB : 93,
		
		/**
		 * 先行告知 提交 申请案件处理
		 */
		FROM_XXGZ_TO_SQANCL : 94,
		
		/**
		 * 申请案件处理 提交 案件处理
		 */
	   FROM_SQAJCL_TO_ANCL : 95,
		
		/**
		 * 申请案件处理 提交 案件处理审核
		 */
		FROM_SQAJCL_TO_ANCLSH : 96,
		
		/**
		 * 申请强制执行 提交 强制执行
		 */
		FROM_SQQZZX_TO_QZZX : 97,
		
		/**
		 * 申请强制执行 提交 强制执行审核
		 */
		 FROM_SQQZZX_TO_QZZXSH : 98,
		
		/**
		 * 结案申请 提交 结案审核
		 */
		 FROM_JASQ_TO_JASH : 99,
		
		/**
		 * 结案申请 提交 案件结案
		 */
		 FROM_JASQ_TO_AJJA : 100,
		
		/**
		 * 法制审核意见--->集体讨论
		 */
		 FROM_FZSH_TO_JTTL : 200,
		
		/**
		 * 法制审核意见--->申请案件处理
		 */
		 FROM_FZSH_TO_AJCL : 201,
		
		/**
		 * 听证告知-->听证申请
		 */
		 FROM_TZGZ_TO_TZSQ : 202,
		
		/**
		 * 听证告知-->申请案件处理
		 */
		FROM_TZGZ_TO_AJCL : 203,
		
		/**
		 * 先行告知-->听证告知
		 */
		FROM_SXGZ_TO_TZGZ : 204,
		
		/**
		 * 陈述申辩-->听证告知
		 */
		FROM_CSSB_TO_TZGZ : 205,
		
		/**
		 * 陈述申辩-->申请案件处理
		 */
		FROM_CSSB_TO_AJCL : 206,
		
		/**
		 *撤销立案审核
		 */
		TO_CXLASH : 207,
		
		/**
		 *撤销立案审批
		 */
		TO_CXLASP : 209,
		
		/**
		 *撤销立案归档
		 */
	     TO_CXLAGD : 210
		}
/**
 * 日常、专项检查处理方式
 * 
 */
var CheckDailyFlow={
	
	/**
	 * 责令整改 101
	 */
	ZLZG : 101,
	
	/**
	 * 行政处罚 102
	 */
	 XZCF : 102,
	
	/**
	 * 申请抽样取证 103
	 */
	 SQCYQZ : 103,
	
	/**
	 * 未发现违法行为 104
	 */
	 WFXWFXW : 104,
	
	/**
	 * 未整改 105
	 */
	 WZG : 105,
	
	/**
	 * 已整改 106
	 */
	 ZG : 106,
	
	/**
	 * 发现问题 107
	 */
	 FXWT : 107,
	
	/**
	 * 未发现问题 108
	 */
	 WFXWT : 108,
	
	/**
	 * 同意 109
	 */
	 TY : 109,
	
	/**
	 * 不同意 110
	 */
	 BTY : 110,
	
	/**
	 * to 下达通知节点 111
	 */
	 TO_XDTZ : 111,
	
	/**
	 * to 现场检查 112
	 */
	 TO_XCJC : 112,
	
	/**
	 * to 结束 from 案件处理 113
	 */
	 TO_JS_FROM_AJCL : 113,
	
	/**
	 * to 领导审核 114
	 */
	 LDSH : 114,
	
	/**
	 * to 领导审批 115
	 */
	 LDSP : 115
}
