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
package net.anotheria.moskitodemo.guestbook.business;

import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;

import org.apache.log4j.Logger;

public class AuthorizationServiceImpl implements IAuthorizationService{
	
	private String storedKey;
	
	private static Logger log;
	static{
		log = Logger.getLogger(AuthorizationServiceImpl.class);
	}
	
	AuthorizationServiceImpl(){
		storedKey = null;
		_load();
	}
	

	public boolean keyMatches(String key) throws AuthorizationServiceException {
		if (storedKey==null){
			log.warn("No stored key, denying everything.");
			return false;
		}
		return storedKey.equals(key);
	}
	
	private void _load(){
		try{
			String stored = IOUtils.readFileAtOnceAsString(BusinessConstants.getKeyFilePath());
			storedKey = StringUtils.removeChars(stored, new char[]{'\n','\r'});
			log.info("loaded key: "+storedKey);
		}catch(Exception e){
			log.error("_load", e);
		}
	}
	
}
