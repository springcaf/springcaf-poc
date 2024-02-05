package com.springcaf.core.security.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppManagedUser implements UserDetails, AbstractTenantUser {
    
	private static final long serialVersionUID = 1980909089485619544L;
	private String username;	// use email to represent username
    private String password;
    private String displayName;
    private String tenantId;
    private String internalUserId;
    
    /* Spring Security related fields*/
    private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    
	private boolean freeAccount = false;
	private boolean showClientNo = false;
	private boolean sendEmailFromServer = false;

	/**
     * Constructor
     * @param username
     * @param password
     */
    public AppManagedUser(String username, String password)
    {
    	this.username = username;
    	this.password = password;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
	
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void assignSecurityRole(String roleName)
	{
		this.authorities.add(new SimpleGrantedAuthority(roleName));
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getInternalUserId() {
		return internalUserId;
	}

	public void setInternalUserId(String internalUserId) {
		this.internalUserId = internalUserId;
	}
	
	@Override
	public String getEncodedUserId() {
		return internalUserId;
	}

	@Override
	public String getUserEmail() {
		return this.getUsername();
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

}
