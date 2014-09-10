package com.example.phr.model;

import android.graphics.drawable.Drawable;

public class Status {

	private String actionHolder;
	private String actionName;
	private String datettime;
	private String status;
	private Drawable actionImgUrl;
	private Drawable postViaImgUrl;
	private Drawable statusImgUrl;
	//private String image;
	
	/*public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}*/
	public Drawable getStatusImgUrl() {
		return statusImgUrl;
	}
	public void setStatusImgUrl(Drawable statusImgUrl) {
		this.statusImgUrl = statusImgUrl;
	}
	public String getActionHolder() {
		return actionHolder;
	}
	public void setActionHolder(String actionHolder) {
		this.actionHolder = actionHolder;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getDatettime() {
		return datettime;
	}
	public void setDatettime(String datettime) {
		this.datettime = datettime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Drawable getActionImgUrl() {
		return actionImgUrl;
	}
	public void setActionImgUrl(Drawable actionImgUrl) {
		this.actionImgUrl = actionImgUrl;
	}
	public Drawable getPostViaImgUrl() {
		return postViaImgUrl;
	}
	public void setPostViaImgUrl(Drawable postViaImgUrl) {
		this.postViaImgUrl = postViaImgUrl;
	}

}
