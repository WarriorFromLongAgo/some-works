//字典常量类
var dict = {
	// 语言
	lang : 1001,
	// 案源
	caseSource : 1002,
	// 案件类型
	caseType : 1003,
	// 当事人类型
	partyTtype : 1600,
	// 性别
	sex : 1700,
	// 处理方式
	dealType : 1100,
	// 名族
	nation : 1701,
	// 文化程度
	edu : 1705,
	// 政治面貌
	political : 1702,
	// 主体执法性质
	duty_law_type : 1708,
	// 权责级别
	level : 1711,
	// 主体级别
	dept_level : 1710,
	// 职务类别
	duty : 1706,
	// 权责事项类别
	pro_type : 1712,
	// 是否生效
	if_effect : 1713,
	// 执法人员执法类型0
	law_type : 1703,
	// 主体性质
	dept_property : 1707,
	// 是否启用
	is_effect : 1709,
	// 法规类别
	statute_type : 2053,
	// 效力级别
	effect_level : 2052,
	// 是否公示
	is_ps : 1709,
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
		 * 案件结案
		 */
		 ANJA:14,
		 /**
		  * 案件归档
		  */
		 ANGD:90,
}