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
        //警告级别
        level:1900,
        //所处状态
        caseWarnType:2051,
        //指标类型
        type:2020,
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