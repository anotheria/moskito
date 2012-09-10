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
package net.java.dev.moskitodemo.guestbook.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import net.anotheria.util.sorter.QuickSorter;
import net.anotheria.util.sorter.Sorter;
import net.java.dev.moskito.core.util.storage.Storage;
import net.java.dev.moskitodemo.guestbook.business.data.Comment;
import net.java.dev.moskitodemo.guestbook.business.data.CommentSortType;

import org.apache.log4j.Logger;

public class CommentServiceImpl implements ICommentService{
	
	private AtomicInteger nextId;
	private Storage<Integer, Comment> comments;
	
	private Sorter<Comment> sorter;
	
	private static Logger log;
	static{
		log = Logger.getLogger(CommentServiceImpl.class);
	}
	
	CommentServiceImpl(){
		nextId = new AtomicInteger(1);
		comments = Storage.createConcurrentHashMapStorage();
		sorter = new QuickSorter<Comment>();
		_load();
	}

	public Comment createComment() throws CommentServiceException {
		Comment c = new Comment(nextId.getAndIncrement());
		comments.put(c.getId(), c);
		_save();
		return c;
	}

	public void deleteComment(int id) throws CommentServiceException {
		Comment old = comments.remove(id);
		if (old!=null)
			_save();
	}

	public void deleteComments(List<Integer> ids) throws CommentServiceException {
		for (Integer id : ids)
			comments.remove(id);
		_save();
	}

	public Comment getComment(int id) throws CommentServiceException {
		Comment c = comments.get(id);
		if (c==null)
			throw new NoSuchCommentException(id);
		return c;
	}

	public List<Comment> getComments() throws CommentServiceException {
		return getCommentsSorted(new CommentSortType());
	}

	public List<Comment> getCommentsSorted(CommentSortType sortType) throws CommentServiceException {
		return sorter.sort(createListFromMap(), sortType);
	}
	
	private List<Comment> createListFromMap(){
		return new ArrayList<Comment>(comments.values());
	}

	public void updateComment(Comment c) throws CommentServiceException {
		//ensure a comment with this id was already there.
		//an exception would be thrown otherwise
		getComment(c.getId());
		comments.put(c.getId(), c);
		_save();
	}
	
	private synchronized void _save(){
		ObjectOutputStream oOut = null;
		try{
			File f = new File(BusinessConstants.getDataDir());
			if (!f.exists())
				f.mkdirs();
			oOut = new ObjectOutputStream(new FileOutputStream(BusinessConstants.getCommentFilePath()));
			oOut.writeObject(nextId);
			oOut.writeObject(comments.fillMap(new HashMap<Integer, Comment>(comments.size())));
		}catch(Exception e){
			log.error("_save",e);
		}finally{
			if (oOut!=null){
				try{
					oOut.close();
				}catch(IOException ignored){}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private synchronized void _load(){
		ObjectInputStream oIn = null;
		try{
			FileInputStream fIn = new FileInputStream(BusinessConstants.getCommentFilePath());
			oIn = new ObjectInputStream(fIn);
			nextId = (AtomicInteger)oIn.readObject();
			Map<Integer,Comment> commentsMap = (Map<Integer, Comment>)oIn.readObject();
			comments.putAll(commentsMap);
		}catch(Exception e){
			log.error("_load", e);
		}finally{
			if (oIn!=null){
				try{
					oIn.close();
				}catch(IOException ignored){}
			}
		}
	}
	
}
