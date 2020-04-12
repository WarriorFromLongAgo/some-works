package com.orhonit.ole.enforce;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.orhonit.ole.report.dao.CaseDao;
import com.orhonit.ole.report.dto.CaseDetailDTO;
import com.orhonit.ole.report.repository.CaseRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j(topic="junit")
public class NativeSqlTest {

	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private CaseDao caseDao;
	
	@Test
	public void getCaseInfo() {
		
		String id = "b61e0e3e-bd4a-45ac-b424-99fd7ae1a44d";
		
		
		List<CaseDetailDTO> caseDetailDTOs = this.caseDao.findCase(id);
		log.info("caseDetailDTOs : {}" , caseDetailDTOs);
		
	}
}
