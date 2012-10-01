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
package net.java.dev.moskitodemo.guestbook.presentation.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.anoplass.api.util.paging.PagingControl;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.slicer.Segment;
import net.anotheria.util.slicer.Slicer;
import net.java.dev.moskito.annotation.MonitorClass;
import net.java.dev.moskitodemo.guestbook.business.data.Comment;
import net.java.dev.moskitodemo.guestbook.business.data.CommentSortType;
import net.java.dev.moskitodemo.guestbook.presentation.bean.CommentTableHeaderBean;
import net.java.dev.moskitodemo.guestbook.presentation.bean.CommentTableItemBean;
import net.java.dev.moskitodemo.guestbook.presentation.bean.SortLinkBean;

@MonitorClass
public class ShowCommentsAction extends BaseGuestbookAction{
	
	public static final String P_SORT_BY = "pSortBy";
	public static final String P_SORT_ORDER = "pSortOrder";
	
	public static final String V_SORT_ORDER_ASC = "asc";
	public static final String V_SORT_ORDER_DESC = "desc";

	public static final int OVERVIEW_COMMENT_LENGTH = 50;
	
	public static final String CAPTIONS[] = {
		"Date",
		"First Name",
		"Last Name",
		"Email", 
		"Comment"
	};
	
	public static final int SORT_BYS[] ={
		CommentSortType.SORT_BY_TIMESTAMP,
		CommentSortType.SORT_BY_FIRST_NAME,
		CommentSortType.SORT_BY_LAST_NAME,
		CommentSortType.SORT_BY_EMAIL,
		CommentSortType.SORT_BY_TEXT
	};
	
	public static final String V_SORT_ORDERS[] = {
		V_SORT_ORDER_ASC,
		V_SORT_ORDER_DESC
	};
	
	public static final boolean SORT_ORDERS[] = {
		true,
		false
	};
	
	public static final String SORT_ORDER_CAPTIONS[] = {
		"A",
		"Z"
	};

	private String PAGE_NUMBER_PARAMETER_NAME = "page";
	private String ITEMS_ON_PAGE_PARAMETER_NAME = "pageSize";

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {

		boolean userIsAuthorized = isAuthorized(req);
		
		CommentSortType sortType = createSortTypeFromRequest(req);
		List<Comment> comments = getCommentService().getCommentsSorted(sortType);
		List<CommentTableItemBean> itemBeans = new ArrayList<CommentTableItemBean>(comments.size());

		for (Comment c : comments){
			itemBeans.add(createTableItemBean(c, !userIsAuthorized));
		}

		int itemsSize = itemBeans.size();
		int pageNumber = getIntPositiveValueFromRequest(PAGE_NUMBER_PARAMETER_NAME, req, 1);
		int pageSize = getIntPositiveValueFromRequest(ITEMS_ON_PAGE_PARAMETER_NAME, req, 5);

		req.setAttribute("pagination", new PagingControl(pageNumber, pageSize, itemsSize));
		req.setAttribute("comments", sliceList(itemBeans, pageNumber, pageSize));
		req.setAttribute("authorized", userIsAuthorized ? Boolean.TRUE : Boolean.FALSE);
		req.setAttribute("headers", createHeaders(sortType));
		
		return mapping.success();
	}

	private List sliceList(List listForSlicing, int pageNumber, int pageSize){

		return Slicer.slice(new Segment(pageNumber, pageSize), listForSlicing).getSliceData();
	}

	/**
	 * Returns parameter value from request, or default value if parameter is null or empty, or it negative.
	 *
	 * @param requestParameterName parameter name
	 * @param request			  httpServletRequest
	 * @param defaultValue		 default value
	 * @return int value
	 */
	private int getIntPositiveValueFromRequest(String requestParameterName, HttpServletRequest request, int defaultValue) {
		try {
			Integer value = Integer.valueOf(request.getParameter(requestParameterName));
			return value >= 0 ? value : defaultValue;
		} catch (NumberFormatException e) {}

		return defaultValue;
	}

	private CommentSortType createSortTypeFromRequest(HttpServletRequest req){
		int sortBy = CommentSortType.SORT_BY_DEFAULT;
		try{
			sortBy = Integer.parseInt(req.getParameter(P_SORT_BY));
		}catch(Exception ignored){}
		
		boolean sortOrder = CommentSortType.DESC;
		try{
			sortOrder = req.getParameter(P_SORT_ORDER).equals(V_SORT_ORDER_DESC) ? 
					CommentSortType.DESC : CommentSortType.ASC;
		}catch(Exception ignored){}
		return new CommentSortType(sortBy, sortOrder);
	}
	
	private CommentTableItemBean createTableItemBean(Comment c, boolean obfuscateEmail){
		CommentTableItemBean bean = new CommentTableItemBean();
		
		bean.setFirstName(c.getFirstName());
		bean.setLastName(c.getLastName());
		bean.setDate(makeDateString(c.getTimestamp()));
		bean.setId(""+c.getId());
		bean.setEmail(obfuscateEmail? obfuscateEmail(c.getEmail()) : c.getEmail() );
		bean.setComment(shortenCommentLine(c.getText()));
		
		return bean;
	}
	
	protected String shortenCommentLine(String text){
		if (text==null || text.length()==0)
			return "";
		if (text.length()<=OVERVIEW_COMMENT_LENGTH)
			return text;
		return text.substring(0, OVERVIEW_COMMENT_LENGTH-3)+"...";
	}
	
	private List<CommentTableHeaderBean> createHeaders(CommentSortType currentSortType){
		List<CommentTableHeaderBean> beans = new ArrayList<CommentTableHeaderBean>();
		
		for (int i=0; i<CAPTIONS.length; i++){
			CommentTableHeaderBean bean = new CommentTableHeaderBean();
			bean.setCaption(CAPTIONS[i]);
			for (int t=0; t<V_SORT_ORDERS.length; t++){
				String link = P_SORT_BY+"="+SORT_BYS[i]+"&"+P_SORT_ORDER+"="+V_SORT_ORDERS[t];
				SortLinkBean sortLinkBean = new SortLinkBean(SORT_ORDER_CAPTIONS[t], link);
				if (currentSortType.getSortBy()==SORT_BYS[i] && currentSortType.getSortOrder()==SORT_ORDERS[t])
					sortLinkBean.setActive(true);
				bean.addSortLink(sortLinkBean);
			}
			beans.add(bean);
		}
		
		return beans;
	}
	
}
