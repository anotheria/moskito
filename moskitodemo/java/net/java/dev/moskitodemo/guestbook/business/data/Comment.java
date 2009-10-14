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
package net.java.dev.moskitodemo.guestbook.business.data;

import java.io.Serializable;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.IComparable;

public class Comment implements Serializable, IComparable{
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String text;
	private long timestamp;
	private boolean wishesUpdates;
	
	public Comment(int anId){
		id = anId;
		firstName = "";
		lastName = "";
		email = "";
		text = "";
		timestamp = System.currentTimeMillis();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String toString(){
		return id+" "+NumberUtils.makeISO8601TimestampString(timestamp)+" "+firstName+" "+lastName+" ("+email+"):"+text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(Object o){
		return (o instanceof Comment)  ? 
				((Comment)o).id==id : false;
	}

	public int compareTo(IComparable anotherComparable, int method) {
		Comment anotherComment = (Comment)anotherComparable;
		switch(method){
			case CommentSortType.SORT_BY_EMAIL:
				return BasicComparable.compareString(email, anotherComment.email);
			case CommentSortType.SORT_BY_FIRST_NAME:
				return BasicComparable.compareString(firstName, anotherComment.firstName);
			case CommentSortType.SORT_BY_LAST_NAME:
				return BasicComparable.compareString(lastName, anotherComment.lastName);
			case CommentSortType.SORT_BY_ID:
				return BasicComparable.compareInt(id, anotherComment.id);
			case CommentSortType.SORT_BY_TIMESTAMP:
				return BasicComparable.compareLong(timestamp, anotherComment.timestamp);
			case CommentSortType.SORT_BY_TEXT:
				return BasicComparable.compareString(text, anotherComment.text);
		}
		throw new RuntimeException("Unsupported sort method: "+method);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public boolean wishesUpdates() {
		return wishesUpdates;
	}

	public void setWishesUpdates(boolean value) {
		wishesUpdates = value;
	}
	
	
	
}
