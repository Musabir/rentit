package com.rentit.common.rest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("ALL")
@XmlType(name = "_xlink", namespace = Link.ATOM_NAMESPACE)
public class ExtendedLink extends Link {
	private static final long serialVersionUID = -9037755944661782122L;
	private HttpMethod method;

	protected ExtendedLink(){}
	
	public ExtendedLink(String href, HttpMethod method){
		super(href);
		this.method = method;
	}
	
	public HttpMethod getMethod(){
		return method;
	}

}
