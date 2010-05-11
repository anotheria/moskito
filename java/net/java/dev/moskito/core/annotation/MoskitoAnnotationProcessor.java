package net.java.dev.moskito.core.annotation;

import com.sun.tools.javac.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTags;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCCatch;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCExpressionStatement;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS;


@SupportedAnnotationTypes(value = {MoskitoAnnotationProcessor.ANNOTATION_TYPE})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class MoskitoAnnotationProcessor extends AbstractProcessor{
	public static final String ANNOTATION_TYPE = "net.java.dev.moskito.core.annotation.Measure";
	private JavacProcessingEnvironment javacProcessingEnv;
	private TreeMaker maker;

	  @Override
	  public void init(ProcessingEnvironment procEnv) {
	    super.init(procEnv);
	    this.javacProcessingEnv = (JavacProcessingEnvironment) procEnv;
	    this.maker = TreeMaker.instance(javacProcessingEnv.getContext());
	  }

	  @Override
	  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		  
		  System.out.println("Hello "+annotations+", "+roundEnv);
		  
	    if (annotations == null || annotations.isEmpty()) {
	      return false;
	    }

	    final Elements elements = javacProcessingEnv.getElementUtils();

	    final TypeElement annotation = elements.getTypeElement(ANNOTATION_TYPE);

	    if (annotation != null) {
	      // Getting all annotated elements
	      final Set<? extends Element> methods = roundEnv.getElementsAnnotatedWith(annotation);

	      System.out.println("methods: "+methods);
	      
	      JavacElements utils = javacProcessingEnv.getElementUtils();
	      for (final Element m : methods) {
	        Measure measure = m.getAnnotation(Measure.class);
	        if (measure!=null) {
	        	System.out.println("replacing impl!");
	          JCTree blockNode = utils.getTree(m);
	          // We only check method declarations
	          if (blockNode instanceof JCMethodDecl) {
	            // Getting method body.
	            final List<JCStatement> statements = ((JCMethodDecl) blockNode).body.stats;

	            System.out.println("statements before "+statements);
	            
	            // new method body
	            List<JCStatement> newStatements = List.nil();
	            // Storing current time
	            JCVariableDecl var = makeTimeStartVar(maker, utils);
	            newStatements = newStatements.append(var);

	            // Creating try.
	            List<JCStatement> tryBlock = List.nil();
	            for (JCStatement statement : statements) {
	              tryBlock = tryBlock.append(statement);
	            }

	            // Creating new finally.
	            JCBlock finalizer = makePrintBlock(maker, utils, var);
	            System.out.println("finalizer : "+finalizer);
	            JCStatement stat = maker.Try(maker.Block(0, tryBlock), List.<JCCatch>nil(), finalizer);
	            newStatements = newStatements.append(stat);

	            // Replacing method code.
	            ((JCMethodDecl) blockNode).body.stats = newStatements;
	            System.out.println("statements after "+((JCMethodDecl) blockNode).body.stats);
	          }
	        }
	      }

	      return true;
	    }

	    return false;
	  }

	  private JCExpression makeCurrentTime(TreeMaker maker, JavacElements utils) {
	    // Creating call to system time.
	    JCExpression exp = maker.Ident(utils.getName("System"));
	    String methodName = "nanoTime";
	    exp = maker.Select(exp, utils.getName(methodName));
	    return maker.Apply(List.<JCExpression>nil(), exp, List.<JCExpression>nil());
	  }

	  protected JCVariableDecl makeTimeStartVar(TreeMaker maker, JavacElements utils) {
	    // creating variable for time storage. time_start_{random}
	    JCExpression currentTime = makeCurrentTime(maker, utils);
	    String fieldName = fieldName = "time_start_" + (int) (Math.random() * 10000);
	    return maker.VarDef(maker.Modifiers(Flags.FINAL), utils.getName(fieldName), maker.TypeIdent(TypeTags.LONG), currentTime);
	  }

	  protected JCBlock makePrintBlock(TreeMaker maker, JavacElements utils, JCVariableDecl var) {
	  // System.out.println
	    JCExpression printlnExpression = maker.Ident(utils.getName("System"));
	    printlnExpression = maker.Select(printlnExpression, utils.getName("out"));
	    printlnExpression = maker.Select(printlnExpression, utils.getName("println"));
	    System.out.println("\tprintln: "+printlnExpression);

	  // (currentTime - startTime)
	    JCExpression currentTime = makeCurrentTime(maker, utils);
	    JCExpression elapsedTime = maker.Binary(JCTree.MINUS, currentTime, maker.Ident(var.name));
	    System.out.println("\telapsedTime: "+elapsedTime);

	  // formatting the result
	    //JCExpression formatExpression = maker.Ident(utils.getName("String"));
	    //formatExpression = maker.Select(formatExpression, utils.getName("format"));

	  // putting all together
	    //List<JCExpression> formatArgs = List.nil();
	    //formatArgs.append(maker.Literal("abc"));
	    //formatArgs.append(elapsedTime);

	    //JCExpression format = maker.Apply(List.<JCExpression>nil(), formatExpression, formatArgs);

	    List<JCExpression> printlnArgs = List.nil();
	    printlnArgs.append(elapsedTime);

	    System.out.println("\tprintln "+printlnArgs);

	    
	    JCExpression print = maker.Apply(List.<JCExpression>nil(), printlnExpression, printlnArgs);
	    JCExpressionStatement stmt = maker.Exec(print);

	    System.out.println("\tstmt "+stmt);

	    List<JCStatement> stmts = List.nil();
	    stmts.append(stmt);
	    
	    System.out.println("Stamtents "+stmts);

	    return maker.Block(0, stmts);
	  }
}
