package net.java.dev.moskito.webcontrol;

import java.util.List;

import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.ui.action.ViewConfigAction;

import org.junit.Test;

public class ViewConfigActionTest {
	
	@Test
	public void testFillPath() throws Exception {
		String interval = "default";
		SourceConfiguration source = new SourceConfiguration("server1", "http://extapi.parship.de/extapi/mui/mskShowAllProducers.xml?pForward=xml");
		List<ViewField> paths = ViewConfigAction.fillPaths(source, interval);
		for (ViewField path : paths) {
			System.out.println(path.getPath());
		}
	}
	
}
