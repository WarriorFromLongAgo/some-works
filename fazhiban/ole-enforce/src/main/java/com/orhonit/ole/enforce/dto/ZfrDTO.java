package com.orhonit.ole.enforce.dto;

import lombok.Data;

/**
 * 执法人员信息 包括主执法人和副执法人
 * 
 * @author 武跃忠
 *
 */
@Data
public class ZfrDTO {
	private String caseZzfryname;

	private String caseZzfrycertNum;

	private String caseZzfrydeptName;

	private String caseFzfryname;

	private String caseFzfrycertNum;

	private String caseFzfrydeptName;

	private String caseWqryname;

	private String caseWqrycertNum;

	private String caseWqrydeptName;

	// private String caseAllZfrNameAndCertNum;

}
