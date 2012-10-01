package net.anotheria.moskitodemo.guestbook.business;

import net.anotheria.moskitodemo.guestbook.business.data.Comment;
import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A utility class for comments clean-up (anti-spam) in offline mode.
 * @author another
 *
 */
public class CommentsCleaner {
	
	static{
		BasicConfigurator.configure();
	}
	private static ICommentService service = CommentServiceFactory.getCommentService();
	
	static final String WORDS[] = {
		"viagra",
		"levitra",
		"phentermine",
		"fluoxetine",
		"cialis",
		"tramadol",
		"adult",
		"loan",
		"sex",
		"videos",
		"diabete",
		"hydrocodone",
		"lesbian",
		"porn",
		"vicodin",
		"xenical",
		"penis",
		"erection",
		"ringtones",
		"breast",
		"sucking",
		"pussy",
		"blowjob",
		"teenies",
		"teens",
		"pharmacy",
		"fuck",
		"chat",
		"pizdayahena",
		"asphost4free",
		"lingerie",
		"vaginal",
		"topless",
		"fakes",
		"naked",
		"freewebtown.com",
		"depression",
		"cosmetic",
		"hentai",
		"bondage",
		"href=",
		"gonew@gmail.com",
	} 	;
	
	public static final Integer ZERO = new Integer(0);

	
	public static void main(String a[]) throws CommentServiceException{
		analyze();
		clean();
	}
	
	public static void clean() throws CommentServiceException{
		List<Comment> comments = new ArrayList<Comment>();
		comments.addAll(service.getComments());
		System.out.println(comments.size()+" comments to test.");
		long removed = 0;
		for (Comment c : comments){ 
			for (int i=0; i<WORDS.length; i++){
				if (doesFieldMatch(c.getFirstName(), WORDS[i]) || 
						doesFieldMatch(c.getLastName(), WORDS[i]) ||
						doesFieldMatch(c.getText(), WORDS[i])){
					service.deleteComment(c.getId());
					removed++;
					break;
					
				}
			}
		}
		System.out.println("Removed: "+removed+" from "+comments.size());
		
	}

	private static void analyze() throws CommentServiceException{
		
		List<Comment> comments = new ArrayList<Comment>();
		comments.addAll(service.getComments());
		System.out.println(comments.size()+" comments loaded.");
		HashMap<String, Integer> statistics = new HashMap<String, Integer>();
		for (Comment c : comments){
			for (int i=0; i<WORDS.length; i++){
				if (doesFieldMatch(c.getFirstName(), WORDS[i]) || 
						doesFieldMatch(c.getLastName(), WORDS[i]) ||
						doesFieldMatch(c.getText(), WORDS[i])){
					Integer oldValue = statistics.get(WORDS[i]);
					if (oldValue==null)
						oldValue = ZERO;
					statistics.put(WORDS[i], oldValue.intValue()+1);
					break;
				}
			}
		}
		//System.out.println("Statistics: "+statistics);
		int sum = 0;
		for (Iterator<String> it = statistics.keySet().iterator(); it.hasNext() ; ){
			String word = it.next();
			int value = statistics.get(word);
			sum += value;
			System.out.println(word+": "+value);
		}
		System.out.println("UNSPAMMED: "+(comments.size()-sum));
	}
	
	private static boolean doesFieldMatch(String fieldValue, String word){
		return fieldValue != null && fieldValue.toLowerCase().indexOf(word)!=-1;
	}
}
