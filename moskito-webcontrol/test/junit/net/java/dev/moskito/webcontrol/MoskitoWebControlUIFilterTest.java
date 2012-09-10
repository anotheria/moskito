package net.java.dev.moskito.webcontrol;

import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.StatsSource;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.repository.*;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoskitoWebControlUIFilterTest {

	private class PatternWithName {
		private String pattern;
		private String fieldName;
		
		public PatternWithName(String name, String pattern) {
			this.fieldName = name;
			this.pattern = pattern;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	@Test
	public void testUpdate() throws Exception {

//		String pattern = "/producers/decorator[@name='*Pool*']/producer[@id='*']/values/value[@name='*Used']/text()";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(MoskitoWebControlUIFilterTest.class.getResourceAsStream("default.xml"));

		ConfigurationRepository.INSTANCE.loadViewsConfiguration();
		StatsSource config = new StatsSource("server1", "");
		RepositoryUpdater.getInstance().fillRepository(config, doc, ConfigurationRepository.INSTANCE.getContainerName("default"));
		
		ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView("Filters");
		
		Snapshot s = Repository.INSTANCE.getSnapshot(ConfigurationRepository.INSTANCE.getContainerName("default"), SnapshotSource
				.valueOf(config));
		for (ViewField field : viewConfig.getFields()) {
			Attribute att = s.getAttribute(field.getAttributeName());
			//System.out.println(field.getAttributeName()+"="+att.getValueString());
		}

		// String start = "";
		// String end = "";
		// String attributeName = "";
		//
		// {
		// Pattern p =
		// Pattern.compile("\\[@([^=]+)=\'([^*\\]]*\\*[^\\]]*)\'\\]+");
		// Matcher m = p.matcher(pattern);
		// while (m.find()) {
		// String fullMatch = m.group(0);
		// String attributeName = m.group(1);
		// String value = m.group(2);
		// System.out.println("fullMatch=" + fullMatch + ", name=" +
		// attributeName + ", value=" + value);
		//
		// String start = pattern.substring(0, pattern.indexOf(fullMatch));
		// System.out.println("start=" + start);
		//
		// NodeList list = (NodeList) xpath.compile(start).evaluate(doc,
		// XPathConstants.NODESET);
		// for (int i = 0; i < list.getLength(); i++) {
		// String attributeValue =
		// list.item(i).getAttributes().getNamedItem(attributeName).getNodeValue();
		// System.out.println("attributeValue=" + attributeValue);
		//
		// if (checkAttributeValue(value, attributeValue)) {
		// System.out.println("************ found *********");
		// String nextPattern = pattern.replaceAll(value.replaceAll("\\*",
		// "\\\\*"), attributeValue);
		// System.out.println(nextPattern);
		// }
		//
		// // String replaced =
		// // pattern.replaceAll("@"+attributeName+"=\\*",
		// // "@"+attributeName+"="+"'"+attributeValue+"'");
		// // System.out.println("replaced="+replaced);
		// }
		// }
		// }

		// {
		// String[] ss = new String[]{
		// "/producers/decorator[@name='Filter']/producer[@id='*']/values/value[@name='Req']/text()"};
		// Pattern p =
		// Pattern.compile("\\[@([^=]+)=([ ^*\\] ]* \\* [ ^\\] ]* )\\]");
		// for (String s : ss) {
		// Matcher m = p.matcher(s);
		// while (m.find()) {
		// System.out.println(m.group(2));
		// }
		// }
		// }

		// if (count == 0) {
		// // TODO just put pattern directly to the xpath.compile (single value)
		// start = pattern;
		// }
		//
		// System.out.println("start=" + start);
		// System.out.println("end=" + end);
		// System.out.println("attributeName=" + attributeName);
		// //
		// DocumentBuilderFactory factory =
		// DocumentBuilderFactory.newInstance();
		// DocumentBuilder builder = factory.newDocumentBuilder();
		// Document doc =
		// builder.parse(MoskitoWebControlUIFilterTest.class.getResourceAsStream("default.xml"));
		// //
		// XPath xpath = XPathFactory.newInstance().newXPath();
		// NodeList list = (NodeList) xpath.compile(start).evaluate(doc,
		// XPathConstants.NODESET);
		// //
		// for (int i = 0; i < list.getLength(); i++) {
		// String value =
		// list.item(i).getAttributes().getNamedItem(attributeName).getNodeValue();
		// System.out.println(value);
		// String replaced = pattern.replaceAll("@"+attributeName+"=\\*",
		// "@"+attributeName+"="+"'"+value+"'");
		// System.out.println(replaced);
		// String v = (String)xpath.compile(replaced).evaluate(doc,
		// XPathConstants.STRING);
		// System.out.println(v);
		// }

		// System.out.println("value=" + value);
	}

	private void buildFullPath(List<PatternWithName> result, Document doc) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();

		List<PatternWithName> res = new ArrayList<PatternWithName>();

		for (int j = 0; j < result.size(); j++) {
			PatternWithName pattern = result.get(j);

			if (isWildCard(pattern.getPattern())) {

				result.remove(pattern);

				Pattern p = Pattern.compile("\\[@([^=]+)=\'([^*\\]]*\\*[^\\]]*)\'\\]+");
				Matcher m = p.matcher(pattern.getPattern());
				while (m.find()) {
					String fullMatch = m.group(0);
					String attributeName = m.group(1);
					String value = m.group(2);
					System.out.println("fullMatch=" + fullMatch + ", name=" + attributeName + ", value=" + value);

					String start = pattern.getPattern().substring(0, pattern.getPattern().indexOf(fullMatch));
					System.out.println("start=" + start);

					NodeList list = (NodeList) xpath.compile(start).evaluate(doc, XPathConstants.NODESET);
					for (int i = 0; i < list.getLength(); i++) {
						String attributeValue = list.item(i).getAttributes().getNamedItem(attributeName).getNodeValue();
						System.out.println("attributeValue=" + attributeValue);

						if (checkAttributeValue(value, attributeValue)) {
							System.out.println("************ found *********");
							String s = value.replaceAll("\\*", "\\\\*");
							String replMatch = fullMatch.replaceAll(s, attributeValue);
							System.out.println("replMatch=" + replMatch);

							int idxOf = pattern.getPattern().indexOf(fullMatch);
							String nextMatch = pattern.getPattern().substring(0, idxOf) + replMatch
									+ pattern.getPattern().substring(idxOf + fullMatch.length(), pattern.getPattern().length());
							System.out.println("nextMatch=" + nextMatch);

							res.add(new PatternWithName(pattern.getFieldName()+"_["+attributeValue+"]", nextMatch));

						}

						// String replaced =
						// pattern.replaceAll("@"+attributeName+"=\\*",
						// "@"+attributeName+"="+"'"+attributeValue+"'");
						// System.out.println("replaced="+replaced);
					}
				}
			}
		}

		if (res.size() > 0) {
			for (PatternWithName p : res) {
				result.add(p);
			}
			buildFullPath(result, doc);
		}

	}

	private boolean isWildCard(String pattern) {
		Pattern p = Pattern.compile("\\[@([^=]+)=\'([^*\\]]*\\*[^\\]]*)\'\\]+");
		Matcher m = p.matcher(pattern);
		if (m.find()) {
			System.out.println("pattern=" + pattern + " is wildcard");
			return true;
		}
		return false;
	}

	private boolean checkAttributeValue(String value, String attributeValue) {
		String pattern = value.replaceAll("\\*", "(.*)");
		Pattern p = Pattern.compile("^" + pattern + "$");
		Matcher m = p.matcher(attributeValue);
		if (m.find()) {
			System.out.println(m.group(0));
			return true;
		}
		return false;
	}

}
