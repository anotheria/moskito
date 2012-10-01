package net.anotheria.moskitodemo;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.moskitodemo.guestbook.presentation.action.AuthorizeUserAction;
import net.anotheria.moskitodemo.guestbook.presentation.action.CreateCommentAction;
import net.anotheria.moskitodemo.guestbook.presentation.action.DeleteCommentAction;
import net.anotheria.moskitodemo.guestbook.presentation.action.NewCommentAction;
import net.anotheria.moskitodemo.guestbook.presentation.action.ShowCommentAction;
import net.anotheria.moskitodemo.guestbook.presentation.action.ShowCommentsAction;
import net.anotheria.moskitodemo.threshold.presentation.action.EmulateAverageRequestsAction;
import net.anotheria.moskitodemo.threshold.presentation.action.EmulateRequestsAction;
import net.anotheria.moskitodemo.usecases.fibonacci.presentation.action.FibonacciCalculatorAction;
import net.anotheria.moskitodemo.usecases.qe.presentation.SolveQEAction;

public class DemoMappingsConfiguration implements ActionMappingsConfigurator{
	
	@Override public void configureActionMappings(ActionMappings mappings){

		mappings.addMapping("gbookShowComments", ShowCommentsAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/guestbook/presentation/jsp/Comments.jsp")
		);
		
		mappings.addMapping("gbookShowComment", ShowCommentAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/guestbook/presentation/jsp/Comment.jsp")
		);

		mappings.addMapping("gbookDeleteComment", DeleteCommentAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/guestbook/presentation/jsp/Comments.jsp")
		);
		mappings.addMapping("gbookNewComment", NewCommentAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/guestbook/presentation/jsp/NewComment.jsp")
		);
		mappings.addMapping("gbookCreateComment", CreateCommentAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/guestbook/presentation/jsp/Message.jsp")
		);
		mappings.addMapping("gbookAuthorize", AuthorizeUserAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/guestbook/presentation/jsp/Message.jsp")
		);
		
		mappings.addMapping("solveQE", SolveQEAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/usecases/qe/presentation/jsp/QE.jsp")
		);
		mappings.addMapping("fibonacci", FibonacciCalculatorAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/usecases/fibonacci/presentation/jsp/Success.jsp"),
				new ActionForward("dialog", "/net/anotheria/moskitodemo/usecases/fibonacci/presentation/jsp/Dialog.jsp")
		);
		mappings.addMapping("thresholdsTR", EmulateRequestsAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/threshold/presentation/jsp/Success.jsp")
		);
		mappings.addMapping("thresholdsAVG", EmulateAverageRequestsAction.class, 
				new ActionForward("success", "/net/anotheria/moskitodemo/threshold/presentation/jsp/Success.jsp")
		);
	}
}
 