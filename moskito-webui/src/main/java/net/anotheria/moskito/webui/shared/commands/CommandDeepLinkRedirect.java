package net.anotheria.moskito.webui.shared.commands;

import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.moskito.webui.util.DeepLinkUtil;

public class CommandDeepLinkRedirect extends CommandRedirect {

    public CommandDeepLinkRedirect(String name, String aTarget, int aCode) {
        super(name, aTarget, aCode);
    }

    public CommandDeepLinkRedirect(String name, String target) {
        super(name, target);
    }

    public String getTarget() {
        return DeepLinkUtil.makeDeepLink(super.getTarget());
    }

}
