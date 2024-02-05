package com.springcaf.core.security.user;

public interface AbstractTenantUser {
	
	public String getDisplayName();
	public String getTenantId();
	public String getEncodedUserId();
	public String getUserEmail();
	public boolean isFreeAccount();
	public boolean isShowClientNo();
	public boolean isSendEmailFromServer();
}
