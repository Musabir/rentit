package com.rentit.common.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlTransient
public class ResourceSupport extends org.springframework.hateoas.ResourceSupport {
	@XmlElement(name = "_xlink", namespace = Link.ATOM_NAMESPACE)
	@JsonProperty("_xlinks")
	private final List<ExtendedLink> _xlinks;
	
	public ResourceSupport(){
		super();
		this._xlinks = new ArrayList<>();
	}
	
	public void add(Link link) {
		if(link instanceof ExtendedLink)
			this._xlinks.add((ExtendedLink) link);
		else
			super.add(link);
	}
	

}
