package com.orhonit.ole.report.controller;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.report.dto.CaseInfoDTO;
import com.orhonit.ole.report.entity.CaseEntity;
import com.orhonit.ole.report.service.caseinfo.CaseService;
import com.orhonit.ole.report.util.RestTemplateUtil;
import com.orhonit.ole.common.datatables.TableResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 案件控制器
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/case")
@Slf4j
public class CaseController {
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("/save")
	public void saveCase(@RequestBody @Valid CaseInfoDTO caseInfoDTO) {
		CaseEntity caseEntity = new CaseEntity();
		BeanUtils.copyProperties(caseInfoDTO, caseEntity);
		this.caseService.save(caseEntity);
	}
	
	/**
	 * 获取模板列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CaseInfoDTO> listCase(TableRequest request) {
		
		Page<CaseEntity> page = this.caseService.getCaseList(request.getParams(), request.getStart(), request.getLength());
		
		return TableRequestHandler.<CaseInfoDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return Long.valueOf(page.getTotalElements()).intValue();
			}
		}).listHandler(new ListHandler<CaseInfoDTO>() {

			@Override
			public List<CaseInfoDTO> list(TableRequest request) {
				return page.getContent().stream().map(
						e -> {
							CaseInfoDTO caseInfoDTO = new CaseInfoDTO();
							BeanUtils.copyProperties(e, caseInfoDTO);
							return caseInfoDTO;
						}
				).collect(Collectors.toList());
			}
		}).build().handle(request);
	}
	
	/**
	 * 获取案件内容
	 * @param id
	 * @return
	 */
	@GetMapping("/getCase/{id}")
	@ResponseBody
	public CaseInfoDTO getCaseInfoById(@PathVariable String id) {
		log.info("调用getCase方法: 参数： {} " , id);
		return this.caseService.findOne(id);
	}
	
	/**
	 * 测试restTemplate
	 */
	@GetMapping("/test")
	public void getTestRestTemplate() throws Exception{
		
//		String caseId = "b61e0e3e-bd4a-45ac-b424-99fd7ae1a44d";
//		
//		String url = "http://localhost:8080/enforce/case/getCase/" + caseId;
//		
//		// restTemplate.getForObject(url, CaseInfoDTO.class);
//		
//		// ResponseEntity<CaseInfoDTO> response = restTemplate.getForEntity(url, CaseInfoDTO.class);
//		
		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		//JSONObject jsonObj = JSONObject.fromObject(params);
		JSONObject jsonObj  = new JSONObject();
		jsonObj.put("username", "admin");
		jsonObj.put("password", "admin");
		String loginUrl = "http://localhost:8080/enforce/sys/login";
		//HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
		//String result = restTemplate.postForObject(loginUrl, formEntity, String.class);
		//log.info("result is {}.", result);
		// String result = restTemplate.getForObject(url, String.class);
		
		
//		MultiValueMap<String, Object> postData = new LinkedMultiValueMap<String, Object>();
//		postData.add("username", "admin");
//		postData.add("password", "admin");
//		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(postData);
//		HttpEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);
//		System.out.println("headers: " + response.getHeaders());
//		System.out.println("body: " + response.getBody());
//		
//		
//		
//		HttpHeaders headerstest = response.getHeaders();
//		Iterator<Entry<String, List<String>>> iter = headerstest.entrySet().iterator();
//		if ( iter.hasNext() ) {
//			Entry<String, List<String>> list = iter.next();
//			String key = list.getKey();
//			System.out.println(key);
//			List<String> values = list.getValue();
//			if ( values != null ) {
//				for ( String value : values) {
//					
//					System.out.println(value);
//				}
//			}
//		}
		
//		String resultResponse = RestTemplateUtil.post(loginUrl, jsonObj, MediaType.APPLICATION_JSON, String.class);
//		System.out.println("===================================");
//		System.out.println(resultResponse);
		
		HttpHeaders headers = new HttpHeaders();  
        /*headers.add(HttpHeadersImpl.ACCEPT, "application/json"); 
        headers.add(HttpHeadersImpl.ACCEPT_ENCODING, "gzip"); 
        headers.add(HttpHeadersImpl.CONTENT_ENCODING, "UTF-8"); 
        headers.add(HttpHeadersImpl.CONTENT_TYPE, 
                "application/json; charset=UTF-8"); 
        headers.add(HttpHeadersImpl.COOKIE, token); 
        headers.add("Token", token);*/  
        headers.add("Accept", "application/json");  
        headers.add("Accpet-Encoding", "gzip");  
        headers.add("Content-Encoding", "UTF-8");  
        headers.add("Content-Type", "application/json; charset=UTF-8");  
        System.out.println(jsonObj.toString());
		// HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);4
        MultiValueMap<String, Object> postData = new LinkedMultiValueMap<String, Object>();
		postData.add("username", "admin");
		postData.add("password", "admin");
		HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(postData);
		String result = restTemplate.postForObject(loginUrl, formEntity, String.class);  
		System.out.println(result);
		
		ResponseEntity<String> exchangeResult = restTemplate.exchange(loginUrl, HttpMethod.POST, formEntity, String.class);
		HttpHeaders responseHeader = exchangeResult.getHeaders();
		
		System.out.println(responseHeader.getCacheControl());
		List<String> cooikes = responseHeader.get("Set-Cookie");
		HttpHeaders caseheaders = new HttpHeaders();  
		for (String c : cooikes ) {
			if ( c.indexOf("JSESSIONID=") != -1 ) {
				String[] a = c.split(";");
				System.out.println("jseesion_id is : "  + a[0] + ";");
				caseheaders.add("JSESSIONID", a[0]);
			}
		}
		
		
		
		HttpEntity<String> headerEntity = new HttpEntity<String>( caseheaders);
		
	
		
		String caseId = "b61e0e3e-bd4a-45ac-b424-99fd7ae1a44d";
		
		String url = "http://localhost:8080/enforce/case/getCase/" + caseId;
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, headerEntity, String.class);
		 
		System.out.println(response);
		
	}

}
