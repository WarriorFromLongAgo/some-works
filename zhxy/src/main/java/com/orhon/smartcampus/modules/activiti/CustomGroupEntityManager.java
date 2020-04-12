package com.orhon.smartcampus.modules.activiti;

import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orhon.smartcampus.modules.activiti.entity.ActivitiGroup;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.modules.systemctl.service.IOrgDepartmentsService;

@Component
public class CustomGroupEntityManager implements GroupEntityManager,Session {
	@Autowired
	private IOrgDepartmentsService departmentsService;

	private static final Log logger = LogFactory  
			.getLog(CustomGroupEntityManager.class);

	@Override
	public GroupEntity create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupEntity findById(String entityId) {
		// TODO Auto-generated method stub
		OrgDepartments byId = departmentsService.getById(entityId);
		ActivitiGroup activitiGroup = new ActivitiGroup(byId); 
		return activitiGroup;
	}

	@Override
	public void insert(GroupEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(GroupEntity entity, boolean fireCreateEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public GroupEntity update(GroupEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupEntity update(GroupEntity entity, boolean fireUpdateEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(GroupEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(GroupEntity entity, boolean fireDeleteEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public Group createNewGroup(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery createNewGroupQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isNewGroup(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}  


}
