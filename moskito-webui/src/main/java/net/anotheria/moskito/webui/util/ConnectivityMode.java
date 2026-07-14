package net.anotheria.moskito.webui.util;

/**
 * Possible connectivity modes, default should be local.
 *
 * @author lrosenberg
 * @since 21.03.14 17:36
 */
public enum ConnectivityMode {
	LOCAL{
        @Override
		public boolean isRemote(){ return false;}
	},
	REMOTE{
        @Override
		public boolean isRemote(){ return true;}
	}
	;

	public abstract boolean isRemote();
}
