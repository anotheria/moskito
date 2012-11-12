package net.anotheria.moskito.webcontrol.guards;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.anotheria.moskito.webcontrol.guards.rules.GreenRule;
import net.anotheria.moskito.webcontrol.guards.rules.RedRule;
import net.anotheria.moskito.webcontrol.guards.rules.Rule;
import net.anotheria.moskito.webcontrol.guards.rules.YellowRule;

public class TrafficLightsGuard extends AbstractGuard {

	@Override
	public void invokeGuard() {

	}

	private List<Rule> rules;

	@Override
	public List<Rule> getRules() {
		if (rules == null) {
			rules = new ArrayList<Rule>();
		}
		return rules;
	}

	@Override
	public void parseRules(String rules) {
		String dots = "..";
		
		StringTokenizer st = new StringTokenizer(rules, ",");
		while (st.hasMoreElements()) {
			String s = st.nextToken();
			int dotsLength = dots.length();
			int pos = s.indexOf(dots);
			if (pos == 0) {
				getRules().add(new GreenRule(Double.MIN_VALUE, Double.valueOf(s.substring(pos+dotsLength, s.length()))));
			} else if (s.endsWith(dots)) {
				getRules().add(new RedRule(Double.valueOf(s.substring(0, pos)), Double.MAX_VALUE));
			} else if (pos > 0 && !s.endsWith(dots)) {
				getRules().add(new YellowRule(Double.valueOf(s.substring(0, pos)), Double.valueOf(s.substring(pos+dotsLength, s.length()))));
			}
		}
	}

}
