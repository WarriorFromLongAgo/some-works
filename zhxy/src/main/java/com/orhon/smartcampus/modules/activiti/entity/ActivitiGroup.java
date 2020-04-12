package com.orhon.smartcampus.modules.activiti.entity;

import org.activiti.engine.impl.persistence.entity.GroupEntity;

import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;

public class ActivitiGroup implements GroupEntity {
	
	
	private String id;

	public  ActivitiGroup(OrgDepartments departments) {
		this.setId(departments.getId().toString());
		this.id = departments.getId().toString();
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public boolean isInserted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInserted(boolean inserted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUpdated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setUpdated(boolean updated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDeleted(boolean deleted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRevision(int revision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRevision() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRevisionNext() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

}
