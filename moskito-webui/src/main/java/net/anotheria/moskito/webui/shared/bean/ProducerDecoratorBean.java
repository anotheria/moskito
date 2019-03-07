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
package net.anotheria.moskito.webui.shared.bean;

import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.producers.api.StatLineAO;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;

import java.util.LinkedList;
import java.util.List;

/**
 * Decorates a producer or a set of producers.
 * @author another
 *
 */
public class ProducerDecoratorBean extends AbstractDecoratorBean {

	/**
	 * Contained producers.
 	 */
	private List<ProducerAO> producers;

	/**
	 * {@link StatLineAO} containing cumulated value for all producers.
	 */
	private StatLineAO cumulatedStat;


	public ProducerDecoratorBean(){
		producers = new LinkedList<>();
	}

	public List<ProducerAO> getProducers() {
		return producers;
	}
	
	public void addProducerBean(ProducerAO producerBean){
		producers.add(producerBean);
	}
	
	public void setProducerBeans(List<ProducerAO> someProducers){
		producers = someProducers;
	}

	public StatLineAO getCumulatedStat() {
		return cumulatedStat;
	}

	public void setCumulatedStat(StatLineAO cumulatedStat) {
		this.cumulatedStat = cumulatedStat;
	}

	public String getSortTypeName(){
		return BaseMoskitoUIAction.BEAN_SORT_TYPE_PREFIX + getName();
	}

	public int getProducersAmount(){
		return producers == null ? 0 : producers.size();
	}
	
}