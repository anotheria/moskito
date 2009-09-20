package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class IterateTei extends TagExtraInfo {

  public VariableInfo[] getVariableInfo(TagData data) {

    VariableInfo[] variables = new VariableInfo[2];

    int counter = 0;

    String id = data.getAttributeString("id");
    String type = data.getAttributeString("type");
    if (id != null) {
      if (type == null) {
        type = "java.lang.Object";
      }
      variables[counter++] = new VariableInfo(id, type, true, VariableInfo.NESTED);
    }

    String indexId = data.getAttributeString("indexId");
    if (indexId != null) {
      variables[counter++] = new VariableInfo(indexId, "java.lang.Integer", true, VariableInfo.NESTED);
    }

    VariableInfo[] result;
    if (counter > 0) {
      result = new VariableInfo[counter];
      System.arraycopy(variables, 0, result, 0, counter);
    } else {
      result = new VariableInfo[0];
    }
    return result;
  }
  
}