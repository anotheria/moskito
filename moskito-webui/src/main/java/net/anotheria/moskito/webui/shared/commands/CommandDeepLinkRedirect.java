package net.anotheria.moskito.webui.shared.commands;

import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.moskito.webui.util.DeepLinkUtil;

/**
 * Command to make deep links in redirects.
 * Overwrites {@link CommandRedirect#getTarget()} method to add `remoteConnection` GET parameter to target,
 * if it was not already present, or current monitor connection is local.
 */
public class CommandDeepLinkRedirect extends CommandRedirect {

    public CommandDeepLinkRedirect(String name, String aTarget, int aCode) {
        super(name, aTarget, aCode);
    }

    public CommandDeepLinkRedirect(String name, String target) {
        super(name, target);
    }

    /**
     * Appends remote connection GET parameter, if it not present, to target link from parent class
     * @return redirect target with appended
     */
    public String getTarget() {
        return DeepLinkUtil.makeDeepLink(super.getTarget());
    }

}
