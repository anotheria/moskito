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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.annotation.MonitorClass;
import net.java.dev.moskitodemo.guestbook.business.data.Comment;
import net.java.dev.moskitodemo.guestbook.presentation.bean.CommentBean;

@MonitorClass
public class ShowCommentAction extends BaseGuestbookAction{
	

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		boolean userIsAuthorized = isAuthorized(req);
		
		String commentId = req.getParameter(PARAM_COMMENT_ID);
		int id = Integer.parseInt(commentId);
		Comment c = getCommentService().getComment(id);
		CommentBean bean = new CommentBean();
		
		bean.setId(""+c.getId());
		bean.setFirstName(c.getFirstName());
		bean.setLastName(c.getLastName());
		bean.setText(c.getText());
		bean.setDate(makeDateString(c.getTimestamp()));
		if (userIsAuthorized)
			bean.setEmail(c.getEmail());
		else
			bean.setEmail(obfuscateEmail(c.getEmail()));
		
		req.setAttribute("comment", bean);
		return mapping.success();
	}

}
