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
import net.anotheria.maf.bean.annotations.Form;
import net.java.dev.moskito.annotation.MonitorClass;
import net.java.dev.moskitodemo.guestbook.business.AntispamUtil;
import net.java.dev.moskitodemo.guestbook.business.CommentServiceException;
import net.java.dev.moskitodemo.guestbook.business.data.Comment;
import net.java.dev.moskitodemo.guestbook.presentation.bean.CommentForm;
import net.java.dev.moskitodemo.guestbook.presentation.bean.MessageBean;

import org.apache.log4j.Logger;

/**
 * Creates a new comment object from form. 
 * @author dvayanu
 *
 */
@MonitorClass
public class CreateCommentAction extends BaseGuestbookAction{
	
	private static Logger log;
	static {
		log = Logger.getLogger(CreateCommentAction.class);
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, @Form(CommentForm.class)FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {

		MessageBean bean = new MessageBean();
		try{
			CommentForm form = (CommentForm)af;
			boolean isBot = AntispamUtil.detectBot(form);
			if (isBot){
				log.info("Detected bot comment: "+form+" from ip: "+req.getRemoteAddr());
				bean.setMessageText("Thanx, comment created (Actually it isn't, since I think that you are a guestbook-spamming bot! But in case the stupid checks for created in output, it's here");
				req.setAttribute("message", bean);
				return mapping.success();
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
		return mapping.success();
	}
	
}
