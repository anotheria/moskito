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
package net.java.dev.moskitodemo.guestbook.presentation.bean;

import org.apache.struts.action.ActionForm;

public class CommentForm extends ActionForm{
	private String firstName;
	private String lastName;
	private String text;
	private String email;
	private boolean updateFlagChecked;
	
	public static final int MAX_TEXT_LENGTH = 1000;
	public static final int MAX_FIELD_LENGTH = 100;
	
	private String cutIfToLong(String s, int limit){
		if (s==null || s.length()<=limit)
			return s;
		return s.substring(0, limit-3 )+"...";
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = cutIfToLong(email, MAX_FIELD_LENGTH);
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = cutIfToLong(firstName, MAX_FIELD_LENGTH);
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = cutIfToLong(lastName, MAX_FIELD_LENGTH);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = cutIfToLong(text, MAX_TEXT_LENGTH);
	}
	
	public String toString(){
		return firstName+" "+lastName+" "+email+", upd: "+updateFlagChecked+", text: "+text;
	}
	public boolean isUpdateFlagChecked() {
		return updateFlagChecked;
	}
	public void setUpdateFlagChecked(boolean updateFlagChecked) {
		this.updateFlagChecked = updateFlagChecked;
	}
}
