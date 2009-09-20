package net.java.dev.moskito.webui.tags.util;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator<T> implements Iterator<T> {
    
    private Enumeration<T> enumeration;
    private T last;
    
    public Enumeration<T> getEnumeration() {
    	return enumeration;
    }
    
    public void setEnumeration(final Enumeration<T> enumeration) {
    	this.enumeration = enumeration;
    }

    public EnumerationIterator(final Enumeration<T> enumeration) {
    	super();
        this.enumeration = enumeration;
        this.last = null;
    }

    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    public T next() {
        last = enumeration.nextElement();
        return last;
    }

    public void remove() {
       throw new UnsupportedOperationException("this Iterator does not support remove() operation");
    }

    
}
