package net.java.dev.moskito.core.annotation.test;

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
import com.sun.tools.javac.util.List;

@SupportedAnnotationTypes(value = {TimeAnnotationProcessor.ANNOTATION_TYPE})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class TimeAnnotationProcessor extends AbstractProcessor {

  public static final String ANNOTATION_TYPE = "net.java.dev.moskito.core.annotation.test.Time";
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
	    System.out.println("%%% HELLO 2 %%%");
    if (annotations == null || annotations.isEmpty()) {
      return false;
    }
    
    System.out.println("Annotation executed on env: "+roundEnv);

    final Elements elements = javacProcessingEnv.getElementUtils();
   
    final TypeElement annotation = elements.getTypeElement(ANNOTATION_TYPE);

    if (annotation != null) {
      final Set<? extends Element> methods = roundEnv.getElementsAnnotatedWith(annotation);

      JavacElements utils = javacProcessingEnv.getElementUtils();
      for (final Element m : methods) {
        Time time = m.getAnnotation(Time.class);
        if (time != null) {
          JCTree blockNode = utils.getTree(m);
          if (blockNode instanceof JCMethodDecl) {
            final List<JCStatement> statements = ((JCMethodDecl) blockNode).body.stats;

            List<JCStatement> newStatements = List.nil();
            JCVariableDecl var = makeTimeStartVar(maker, utils, time);
            newStatements = newStatements.append(var);

            List<JCStatement> tryBlock = List.nil();
            for (JCStatement statement : statements) {
              tryBlock = tryBlock.append(statement);
            }

            JCBlock finalizer = makePrintBlock(maker, utils, time, var);
            System.out.println("finalizer -->"+finalizer+"<--");
            JCStatement stat = maker.Try(maker.Block(0, tryBlock), List.<JCCatch>nil(), finalizer);
            newStatements = newStatements.append(stat);

            System.out.println("newStatements -->"+newStatements+"<--");

            ((JCMethodDecl) blockNode).body.stats = newStatements;
          }
        }
      }

      return true;
    }

    return false;
  }

  private JCExpression makeCurrentTime(TreeMaker maker, JavacElements utils, Time time) {
    JCExpression exp = maker.Ident(utils.getName("System"));
    String methodName;
    switch (time.interval()) {
      case NANOSECOND:
        methodName = "nanoTime";
        break;
      default:
        methodName = "currentTimeMillis";
        break;
    }
    exp = maker.Select(exp, utils.getName(methodName));
    return maker.Apply(List.<JCExpression>nil(), exp, List.<JCExpression>nil());
  }

  protected JCVariableDecl makeTimeStartVar(TreeMaker maker, JavacElements utils, Time time) {
    JCExpression currentTime = makeCurrentTime(maker, utils, time);
    String fieldName = fieldName = "time_start_" + (int) (Math.random() * 10000);
    return maker.VarDef(maker.Modifiers(Flags.FINAL), utils.getName(fieldName), maker.TypeIdent(TypeTags.LONG), currentTime);
  }

  protected JCBlock makePrintBlock(TreeMaker maker, JavacElements utils, Time time, JCVariableDecl var) {
    JCExpression printlnExpression = maker.Ident(utils.getName("System"));
    printlnExpression = maker.Select(printlnExpression, utils.getName("out"));
    printlnExpression = maker.Select(printlnExpression, utils.getName("println"));

    JCExpression currentTime = makeCurrentTime(maker, utils, time);
    JCExpression elapsedTime = maker.Binary(JCTree.MINUS, currentTime, maker.Ident(var.name));

    JCExpression formatExpression = maker.Ident(utils.getName("String"));
    formatExpression = maker.Select(formatExpression, utils.getName("format"));

    List<JCExpression> formatArgs = List.nil();
    formatArgs.append(maker.Literal(time.format()));
    formatArgs.append(elapsedTime);

    JCExpression format = maker.Apply(List.<JCExpression>nil(), formatExpression, formatArgs);

    List<JCExpression> printlnArgs = List.nil();
    printlnArgs.append(format);

    JCExpression print = maker.Apply(List.<JCExpression>nil(), printlnExpression, printlnArgs);
    JCExpressionStatement stmt = maker.Exec(print);

    List<JCStatement> stmts = List.nil();
    stmts.append(stmt);

    return maker.Block(0, stmts);
  }
}
