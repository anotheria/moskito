package net.anotheria.moskitodemo.guestbook.business;

import net.anotheria.moskitodemo.guestbook.business.data.Comment;
import net.anotheria.moskitodemo.guestbook.presentation.bean.CommentForm;

public class AntispamUtil {
	private static String BLACKLIST[] = {
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
		"stripping",
		"stockings",
		"freewebtown.com",
		"depression",
		"cosmetic",
		"hentai",
		"bondage",
		"groups.google.de/group/",
		"groups.google.com/group/",
		"groups.google.us/group/",
		"members.lycos.co.uk/",
		"ooholg.o-f.com",
		"nutzworld.net/",
		"parknhost.com/",
		"usafreespace.com/",
		".lycos.",
		"doxycycline",
		"superelectionpolls.info",
		"xeaast.o-f.com",
		"email@gmail.com",
		"emai1l@gmail.com",
		"girls@",
	 	"girlswet@mail.com",
	 	"ishmael@mail.com",
		"blackjack",
		"texas holdem",
		"poker",
		"casino",
		"insurance",
		"airline-ticket",
		"boobs",
		"slipboard.forumvila.com",
		"britneyspears",
		"bdsm",
		"oxycontin",
		"phendimetrazine",
		"dabelbrothers.com",
		"fioricet",
		"acomplia",
		"zithromax",
		"erythromycin",
		"dildo",
		"lubricants",
		"prednisone",
		
		"monica bellucci",
		"monica-bellucci",
		"sandra bullock",
		"sandra-bullock",
		"britney-spears",
		"britney spears",
		"pamela-anderson",
		"pamela anderson",
		"paula abdul",
		"paula-abdul",
		"emma watson",
		"emma-watson",
		
		".wsd.wednet.edu",
		"international.wmich.edu",
		"pinkmilk.net",
		"carsdoor.com",
		"abilitie-y08",
		"y08.info",
		"county-rmx",
		"rmx.info",
		"usafreespace.com",
		"wsu.edu",
		"bedfordshire",
		"cyworld.com",
		"blanksms.com",
		"purpleheartsbook.com",
		
		"pillstrade",
		"ephedrine",
		"accutane",
		"flagyl",
		"zovirax",
		"cipro",
		"paxil",
		"diflucan",
		"lexapro",
		"soma online",
		"condylox",
		"adipex",
		"valium",
		"ephedra",
		"[url",
		
		
		
		"cheap",
		"addiction",
		"ringtone",
		"webcam",
		"digitalcam",
		"crossdressing",
		"erotica",
		"teen ass",
		"100freemb.com",
		"generic",
		"fingering",
		"beastiality",
		"brunette",
		"blonde",
		"celeb",
		"orgie",
		"midget",
		"lover",
		
		"datadiri.cc",
		"sale",
		"girls",
		"nylon",
		"escorts",
		"humorousmaximus",
		"master",
		"talkman",
		"interchange",
		"online shopping",
		
		//"href=",
	} 	;

	public static boolean detectBot(Comment comment){
		if (isBlacklisted(comment.getFirstName()))
			return true;
		if (isBlacklisted(comment.getLastName()))
			return true;
		if (isBlacklisted(comment.getEmail()))
			return true;
		if (isBlacklisted(comment.getText()))
			return true;
		return false;
	}
	
	public static boolean detectBot(CommentForm comment){
		if (isBlacklisted(comment.getFirstName()))
			return true;
		if (isBlacklisted(comment.getLastName()))
			return true;
		if (isBlacklisted(comment.getEmail()))
			return true;
		if (isBlacklisted(comment.getText()))
			return true;
		return false;
	}

	private static boolean isBlacklisted(String s){
		if (s==null)
			s = "";
		s = s.toLowerCase();
		for (String b : BLACKLIST){
			if (s.indexOf(b)!=-1)
				return true;
		}
		return false;
	}

}
