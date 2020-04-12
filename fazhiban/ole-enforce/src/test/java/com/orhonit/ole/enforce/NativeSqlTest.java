package com.orhonit.ole.enforce;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.orhonit.ole.enforce.dao.CaseDao;
import com.orhonit.ole.enforce.dto.DeptPersonDTO;
import com.orhonit.ole.enforce.repository.CaseRepository;

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
		
		
//		List<CaseDetailDTO> caseDetailDTOs = this.caseDao.findCase(id);
//		log.info("caseDetailDTOs : {}" , caseDetailDTOs);
//		
//		String result = this.caseRepository.getDeptUserByCurrentUser("15010001003");
//		
//		if ( result.split(",").length > 0 ) {
//			for ( String deptId : result.split(",")) {
//				if ( !deptId.equals("$")) {
//					System.out.println(deptId);
//				}
//			}
//		}
//		
//		log.info("result : {}",result);
		
		List<DeptPersonDTO> deptPersonDTOs = this.caseDao.getDeptUserByCurrentUser("15010001", 26);
		
		for ( DeptPersonDTO deptPersonDTO : deptPersonDTOs) {
			log.info("deptPersonDTO is : {}" , deptPersonDTO);
		}
		
		
	}
}
