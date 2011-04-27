package ar.edu.unq.tpi.commons.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

import ar.edu.unq.tpi.commons.configuration.jfig.JFigConfiguration;

public class TestGetConfiguration extends TestCase {

	public void testGetConfig() {
		final Collection<String> privateSections = new ArrayList<String>();
		privateSections.add("hibernate");
		privateSections.add("mailing");
		privateSections.add("modules");
		privateSections.add("enviroment");
		privateSections.add("webservice");
		privateSections.add("initialStatements");
		
		final Map<String, Map> entries = new TreeMap<String, Map>(JFigConfiguration.getInstance().getEntries());
		
		CollectionUtils.filter(entries.entrySet(), new Predicate<Entry<String, Map>>() {
			public boolean evaluate(final Entry<String, Map> section) {
				return !privateSections.contains(section.getKey());
			}
		});
		
		System.out.println(entries);
	}
}
