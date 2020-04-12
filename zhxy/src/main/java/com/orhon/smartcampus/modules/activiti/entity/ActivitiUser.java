package com.orhon.smartcampus.modules.activiti.entity;

import org.activiti.engine.identity.Picture;
import org.activiti.engine.impl.persistence.entity.ByteArrayRef;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.orhon.smartcampus.modules.user.entity.Users;

public class ActivitiUser implements UserEntity {
	private String id;
	
	public ActivitiUser(Users users) {
		// TODO Auto-generated constructor stubt
		this.setId(users.getId().toString());
	}

	public ActivitiUser(String id){
		this.setId(id);

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
	public Picture getPicture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPicture(Picture picture) {
		// TODO Auto-generated method stub
		
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
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFirstName(String firstName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastName(String lastName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPictureSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ByteArrayRef getPictureByteArrayRef() {
		// TODO Auto-generated method stub
		return null;
	}

}
