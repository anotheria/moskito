package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class DefineTei extends TagExtraInfo {

    public VariableInfo[] getVariableInfo(TagData data) {
        String type = data.getAttributeString("type");
        if (type == null) {
           type = "java.lang.Object";
        }
        return new VariableInfo[] {
          new VariableInfo(data.getAttributeString("id"), type, true, VariableInfo.AT_END )
        };
    }

}

