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
package net.java.dev.moskito.test.cvsheaders;

import net.anotheria.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HeaderWriter {
	
	static long scanned, javaFiles, toDos, dones;
	static long dirs;
	
	public static void main(String a[]) throws IOException{
		File f = new File(".");
		System.out.println("starting with "+f.getAbsolutePath());
		proceedDir(f);
		System.out.println("Scanned directories: "+dirs+", files: "+scanned);
		System.out.println("JavaFiles: "+javaFiles);
		System.out.println("ToDos: "+toDos);
		System.out.println("Dones: "+dones);
	}
	
	private static void proceedDir(File dir) throws IOException{
		if (dir.getName().equals("CVS"))
			return;
		dirs++;
		//System.out.println("Proceeding directory : "+dir);
		File files[]  = dir.listFiles();
		for (File f:files){
			if (f.isFile())
				proceedFile(f);
			else
				proceedDir(f);
		}
	}
	
	private static void proceedFile(File f) throws IOException{
		scanned++;
		if (!f.getName().endsWith(".java"))
			return;
		javaFiles++;
		String content = IOUtils.readFileAtOnceAsString(f.getAbsolutePath());
		int index = content.indexOf("Copyright (c) 2006 The MoSKito Project Team");
		boolean startsWithComment = content.trim().startsWith("/*");
		if ( startsWithComment && index!=-1){
			//System.out.println("Already done: "+f);
			return;
		}
		
		if (startsWithComment){
			System.out.println(f+" starts with comment, but has no license.");
			return;
		}
		
		System.out.println("Todo: "+f);
		
		toDos++;
		//if (1==1)
			//return;
		content = HEADER + content;
		//System.out.println(content);
		FileOutputStream fOut = new FileOutputStream(f);
		fOut.write(content.getBytes());
		fOut.flush();
		fOut.close();
		dones++;
	}
	
	public static final String HEADER = ""+
	"/*\n"+
	" * $Id$\n"+
	" * \n"+
	" * This file is part of the MoSKito software project\n"+
	" * that is hosted at http://moskito.dev.java.net.\n"+
	" * \n"+
	" * All MoSKito files are distributed under MIT License:\n"+
	" * \n"+
	" * Copyright (c) 2006 The MoSKito Project Team.\n"+ 
	" * \n"+
	" * Permission is hereby granted, free of charge,\n"+ 
	" * to any person obtaining a copy of this software and\n"+ 
	" * associated documentation files (the \"Software\"), \n"+
	" * to deal in the Software without restriction, \n"+
	" * including without limitation the rights to use,\n"+ 
	" * copy, modify, merge, publish, distribute, sublicense,\n"+ 
	" * and/or sell copies of the Software, and to permit \n"+
	" * persons to whom the Software is furnished to do so, \n"+
	" * subject to the following conditions:\n"+
	" * \n"+
	" * The above copyright notice and this permission notice\n"+ 
	" * shall be included in all copies \n"+
	" * or substantial portions of the Software.\n"+
	" * \n"+
	" * THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY\n"+ 
	" * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT \n"+
	" * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"+
	" * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.\n"+ 
	" * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS \n"+
	" * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, \n"+
	" * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, \n"+
	" * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR\n"+ 
	" * THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n"+
	" */	\n";
}
