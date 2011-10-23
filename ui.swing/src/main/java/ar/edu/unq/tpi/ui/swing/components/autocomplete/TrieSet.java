package ar.edu.unq.tpi.ui.swing.components.autocomplete;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unq.tpi.base.common.Observable;

/**
 * A TrieSet. A set-like interface designed specifically for Strings. Uses a
 * Trie as the backing Map, and provides an implementation specific to Strings.
 * Has the same retrieval/insertion times as the backing Trie. Stores the value
 * as the string, for easier retrieval. The goal is to efficiently find Strings
 * that can branch off a prefix.
 * 
 * Primarily designed as an AutoCompleteDictionary
 * 
 */
public class TrieSet implements AutoCompleteDictionary {
    /**
     * The backing map. A binary-sorted Trie.
     */
    private transient Trie map;

    /**
     * This constuctor sets up a dictionary where case IS significant but whose
     * sort order is binary based. All Strings are stored with the case of the
     * last entry added.
     */
    public TrieSet(final boolean caseSensitive) {
        map = new Trie(caseSensitive);
    }

    /**
     * Adds a value to the set. Different letter case of values is always kept
     * and significant. If the TrieSet is made case-insensitive, it will not
     * store two Strings with different case but will update the stored values
     * with the case of the last entry.
     */
    public void addEntry(final String property, Observable data ) {
        if (!this.contains(data)) {
            map.add(String.valueOf(data.getProperty(property)), new ValueNode(data, property));
        }
    }
    public void addEntry(final Object data) {
        if (!this.contains(data)) {
            map.add(data.toString(), data);
        }
    }

    /**
     * Determines whether or not the Set contains this String.
     */
    public boolean contains(final Object data) {
        return map.get(data.toString()) != null;
    }

    /**
     * Removes a value from the Set.
     * 
     * @return <tt>true</tt> if a value was actually removed.
     */
    public boolean removeEntry(final String data) {
        return map.remove(data);
    }

    /**
     * Return all the Strings that can be prefixed by this String. All values
     * returned by the iterator have their case preserved.
     */
    public Iterator getPrefixedBy(final String data) {
        return map.getPrefixedBy(data);
    }

    /**
     * Return the last String in the set that can be prefixed by this String
     * (Trie's are stored in alphabetical order). Return null if no such String
     * exist in the current set.
     */
    public String lookup(final String data) {
        Iterator it = map.getPrefixedBy(data);
        if (!it.hasNext())
            return null;
        return (String) it.next();
    }

    /**
     * Returns all values (entire TrieSet)
     */
    public Iterator getIterator() {
        return map.getIterator();
    }

    /**
     * Returns all potential matches off the given String.
     */
    public Iterator getIterator(final String s) {
        return map.getPrefixedBy(s);
    }

    /**
     * Clears all items in the dictionary.
     */
    public void clear() {
        List l = new LinkedList();
        for (Iterator i = this.getIterator(); i.hasNext();) {
            l.add(i.next());
        }
        for (Iterator i = l.iterator(); i.hasNext();) {
            this.removeEntry((String) i.next());
        }
    }
}