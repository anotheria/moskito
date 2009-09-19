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

import net.java.dev.moskitodemo.guestbook.business.AntispamUtil;
import net.java.dev.moskitodemo.guestbook.business.CommentServiceException;
import net.java.dev.moskitodemo.guestbook.business.data.Comment;
import net.java.dev.moskitodemo.guestbook.presentation.bean.CommentForm;
import net.java.dev.moskitodemo.guestbook.presentation.bean.MessageBean;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Creates a new comment object from form. 
 * @author dvayanu
 *
 */
public class CreateCommentAction extends BaseGuestbookAction{
	
	private static Logger log;
	static {
		log = Logger.getLogger(CreateCommentAction.class);
	}

	@Override
	public ActionForward moskitoExecute(ActionMapping mapping, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {

		MessageBean bean = new MessageBean();
		try{
			CommentForm form = (CommentForm)af;
			boolean isBot = AntispamUtil.detectBot(form);
			if (isBot){
				log.info("Detected bot comment: "+form+" from ip: "+req.getRemoteAddr());
				bean.setMessageText("Thanx, comment created (Actually it isn't, since I think that you are a guestbook-spamming bot! But in case the stupid checks for created in output, it's here");
				req.setAttribute("message", bean);
				return mapping.findForward("success");
			}
			
			Comment c = getCommentService().createComment(); 
			c.setFirstName(form.getFirstName());
			c.setLastName(form.getLastName());
			c.setEmail(form.getEmail());
			c.setText(form.getText());
			c.setWishesUpdates(form.isUpdateFlagChecked());
			getCommentService().updateComment(c);
			bean.setMessageText("Thanx, comment created.");
			bean.setLinkCaption("Return to overview");
			bean.setLink("gbookShowComments?ts="+System.currentTimeMillis());
		}catch(CommentServiceException e){
			
			bean.setMessageText("Error: "+e.getMessage());
			bean.setLink("javascript.history.back();");
			bean.setLinkCaption("Back");
		}
		
		req.setAttribute("message", bean);
		return mapping.findForward("success");
	}
	
}
