package com.springcaf.core.security.user;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class TenantOidcUser extends DefaultOidcUser implements AbstractTenantUser {

	private static final long serialVersionUID = 3533276991226536957L;
	private String encodedUserId = null;
	private String tenantId = null;
	private String tenantName = null;
	private String displayName = null;
	private boolean admin = false; 
	private boolean realTenantId = false;
	private boolean freeAccount = false;
	private boolean showClientNo = false;
	private boolean sendEmailFromServer = false;

	/**
	 * Constructor
	 * @param oidcUser
	 */
	public TenantOidcUser(OidcUser oidcUser) 
	{
		super(oidcUser.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo());
	}

	@Override
	public String getEncodedUserId() {
		return encodedUserId;
	}

	public void setEncodedUserId(String encodedUserId) {
		this.encodedUserId = encodedUserId;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean isAdmin) {
		this.admin = isAdmin;
	}

	@Override
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public boolean isRealTenantId() {
		return realTenantId;
	}

	public void setRealTenantId(boolean realTenantId) {
		this.realTenantId = realTenantId;
	}

	public boolean hasRealTenantId()
	{
		return this.realTenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	@Override
	public boolean isFreeAccount() {
		return freeAccount;
	}

	public void setFreeAccount(boolean freeAccount) {
		this.freeAccount = freeAccount;
	}

	@Override
	public boolean isShowClientNo() {
		return showClientNo;
	}

	public void setShowClientNo(boolean showClientNo) {
		this.showClientNo = showClientNo;
	}

	@Override
	public boolean isSendEmailFromServer() {
		return sendEmailFromServer;
	}

	public void setSendEmailFromServer(boolean sendEmailFromServer) {
		this.sendEmailFromServer = sendEmailFromServer;
	}

	@Override
	public String getUserEmail() {
		return this.getEmail();
	}
}
