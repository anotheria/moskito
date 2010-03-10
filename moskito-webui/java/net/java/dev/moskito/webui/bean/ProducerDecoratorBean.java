/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.webui.action.BaseMoskitoUIAction;

/**
 * Decorates a producer.
 * @author another
 *
 */
public class ProducerDecoratorBean extends AbstractDecoratorBean {
	
	private List<MetaHeaderBean> metaheader;
	
	private List<ProducerBean> producers;
	
	private ProducerBeanVisibilityType visibilityTypeBean;
	
	public ProducerDecoratorBean(){
		metaheader = new ArrayList<MetaHeaderBean>();
		producers = new ArrayList<ProducerBean>();
		visibilityTypeBean = new ProducerBeanVisibilityType();
	}

	public List<MetaHeaderBean> getMetaHeader(){
		return metaheader;
	}
	
	public void setMetaHeader(List<MetaHeaderBean> metaheader){
		this.metaheader = metaheader;
	}
	
	public List<ProducerBean> getProducers() {
		return producers;
	}
	
	public void addProducerBean(ProducerBean producerBean){
		producers.add(producerBean);
	}
	
	public void setProducerBeans(List<ProducerBean> someProducers){
		producers = someProducers;
	}	
	
	
	public ProducerBeanVisibilityType getVisibilityTypeBean() {
		return visibilityTypeBean;
	}

	public void setVisibilityTypeBean(ProducerBeanVisibilityType visibilityTypeBean) {
		this.visibilityTypeBean = visibilityTypeBean;
	}

	public String getSortTypeName(){
		return BaseMoskitoUIAction.BEAN_SORT_TYPE_PREFIX + getName();
	}
	
	public String getProducerVisibilityTypeName() {
		//default - is expanded
		return BaseMoskitoUIAction.BEAN_VISIBILITY_TYPE_PREFIX + getName();
	}
}