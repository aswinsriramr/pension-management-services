package com.pension.processpensioninput.proxy;

import javax.annotation.ManagedBean;

import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;

@Data
@ManagedBean
@RequestScope
public class AuthorizationContext {
	String authorization;
}
