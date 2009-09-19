package net.java.dev.moskito.test.demo.guestbook;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import net.java.dev.moskitodemo.guestbook.business.AntispamUtil;
import net.java.dev.moskitodemo.guestbook.business.CommentServiceFactory;
import net.java.dev.moskitodemo.guestbook.business.ICommentService;
import net.java.dev.moskitodemo.guestbook.business.data.Comment;

public class CommentCleaner {
	
	
	public static void main(String a[]) throws Exception{
		BasicConfigurator.configure();
		ICommentService service = CommentServiceFactory.getCommentService();
		
		/*
		Comment tmp = service.getComment(42590);
		System.out.println(tmp);
		System.out.println(tmp.getText());
		System.out.println(tmp.getText().indexOf("href="));
		System.out.println(isBlacklisted(tmp.getText()));*/
		///*
		List<Comment> comments = service.getComments();
		
		System.out.println("Got "+comments.size()+" comments to check");
		int bots = 0;
		List<Integer> toDelete = new ArrayList<Integer>();
		for (Comment c : comments){
			boolean bot = AntispamUtil.detectBot(c);
			if (bot){
				bots++;
				System.out.println(c);
				toDelete.add(c.getId());
			}
		}
		service.deleteComments(toDelete);
		System.out.println("Detected "+bots+" bot comments");
		//*/
	}
	
}
