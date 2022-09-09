package net.anotheria.moskito.webui;

/**
 * This enum contains Features that can be switched off.
 *
 * @author lrosenberg
 * @since 20.04.15 17:12
 */
public enum Features {
	PRODUCER_FILTERING{
		public boolean isEnabled(){
			try{
				Boolean flag = (Boolean)MoSKitoWebUIContext.getCallContext().getAttribute(name());
				if (flag != null && flag == Boolean.FALSE)
					return false;
			}catch(Exception any){
				//; ignored
			}
			return true;
		}
	};

	public abstract boolean isEnabled();
}
