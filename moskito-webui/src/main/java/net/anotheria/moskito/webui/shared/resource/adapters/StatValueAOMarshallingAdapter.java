package net.anotheria.moskito.webui.shared.resource.adapters;

import net.anotheria.moskito.core.decorators.value.StatValueAO;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} adapter used for marshaling {@link StatValueAO}'s raw value to {@link String}.
 *
 * @author Illya Bogatyrchuk
 */
public class StatValueAOMarshallingAdapter extends XmlAdapter<String, StatValueAO> {
    @Override
    public StatValueAO unmarshal(final String value) throws Exception {
        throw new UnsupportedOperationException("Not supported!");
    }

    @Override
    public String marshal(final StatValueAO statValueAO) throws Exception {
        return statValueAO != null ? statValueAO.getRawValue() : null;
    }
}
