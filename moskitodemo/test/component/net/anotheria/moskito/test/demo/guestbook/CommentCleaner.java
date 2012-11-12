package net.anotheria.moskito.test.demo.guestbook;

import net.anotheria.moskitodemo.guestbook.business.AntispamUtil;
import net.anotheria.moskitodemo.guestbook.business.CommentServiceFactory;
import net.anotheria.moskitodemo.guestbook.business.ICommentService;
import net.anotheria.moskitodemo.guestbook.business.data.Comment;
import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.List;


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
