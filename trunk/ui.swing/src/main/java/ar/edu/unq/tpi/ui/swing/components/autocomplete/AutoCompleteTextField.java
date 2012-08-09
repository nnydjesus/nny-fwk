package ar.edu.unq.tpi.ui.swing.components.autocomplete;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.Document;

import ar.edu.unq.tpi.base.common.Observable;
import ar.edu.unq.tpi.ui.swing.components.CenteredJFrame;

public class AutoCompleteTextField extends KeyProcessingTextField {

    private static final long serialVersionUID = 1L;

    public AutoCompleteTextField() {
        super();
        this.init();
    }

    public AutoCompleteTextField(final Document a, final String b, final int c) {
        super(a, b, c);
        this.init();
    }

    public AutoCompleteTextField(final int a) {
        super(a);
        this.init();
    }

    public AutoCompleteTextField(final String a) {
        super(a);
        this.init();
    }

    public AutoCompleteTextField(final String a, final int b) {
        super(a, b);
        this.init();
    }

    /**
     * Sets up stuff.
     */
    private void init() {
        this.enableEvents(AWTEvent.KEY_EVENT_MASK);
        this.enableEvents(AWTEvent.HIERARCHY_EVENT_MASK);
        this.enableEvents(AWTEvent.FOCUS_EVENT_MASK);
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        entryList = new AutoCompleteList();
        this.showPopup();
    }

    /**
     * Fires an action event.
     * 
     * If the popup is visible, this resets the current text to be the selection
     * on the popup (if something was selected) prior to firing the event.
     */
    @Override
    protected void fireActionPerformed() {
        if (popup != null) {
            String selection = (String) entryList.getSelectedStringValue();
            this.hidePopup();
            if (selection != null) {
                this.setValue(selection);
                return;
            }
        }

        super.fireActionPerformed();
    }

    /**
     * Forwards necessary events to the AutoCompleteList.
     */
    @Override
    public void processKeyEvent(final KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            evt.consume();
        }

        super.processKeyEvent(evt);

        if (dict != null) {
            switch (evt.getID()) {
            case KeyEvent.KEY_PRESSED:
                switch (evt.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (popup != null) {
                        entryList.decrementSelection();
                    } else {
                        this.showPopup(dict.getIterator());
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (popup != null) {
                        entryList.incrementSelection();
                    } else {
                        this.showPopup(dict.getIterator());
                    }
                    break;
                }
                break;
            case KeyEvent.KEY_TYPED:
                switch (evt.getKeyChar()) {
                case KeyEvent.VK_ESCAPE:
                    if (popup != null) {
                        this.hidePopup();
                        this.selectAll();
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    entryList.selectText();
                    break;
                default:
                    this.autoCompleteInput();
                }
            }
        }
    }
    

    /**
     * Ensures the popup gets hidden if this text-box is hidden and that the
     * popup is shown if a previous show is pending (from trying to autocomplete
     * while it wasn't visible).
     */
    @Override
    protected void processHierarchyEvent(final HierarchyEvent evt) {
        super.processHierarchyEvent(evt);

        if ((evt.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED) {
            boolean showing = this.isShowing();
            if (!showing && popup != null) {
                this.hidePopup();
            } else if (showing && popup == null && showPending) {
                this.autoCompleteInput();
            }
        }
    }

    /**
     * Ensures that if we lose focus, the popup goes away.
     */
    @Override
    protected void processFocusEvent(final FocusEvent evt) {
        super.processFocusEvent(evt);

        if (evt.getID() == FocusEvent.FOCUS_LOST) {
            if (popup != null) {
                this.hidePopup();
            }
        }
    }

    // ----------------------------------------------------------------------------
    // Public methods
    // ----------------------------------------------------------------------------

    /**
     * Set the dictionary that autocomplete lookup should be performed by.
     * 
     * @param dict
     *            The dictionary that will be used for the autocomplete lookups.
     */
    public void setDictionary(final AutoCompleteDictionary dict) {
        this.dict = dict;
    }

    /**
     * Gets the dictionary currently used for lookups.
     * 
     * @return dict The dictionary that will be used for the autocomplete
     *         lookups.
     */
    public AutoCompleteDictionary getDictionary() {
        return dict;
    }

    /**
     * Creates the default dictionary object
     */
    public AutoCompleteDictionary createDefaultDictionary() {
        return new TrieSet(true);
    }

    /**
     * Sets whether the component is currently performing autocomplete lookups
     * as keystrokes are performed.
     * 
     * @param val
     *            True or false.
     */
    public void setAutoComplete(final boolean val) {
        // UISettings.AUTOCOMPLETE_ENABLED.setValue(val);
        // return true;
    }

    /**
     * Gets whether the component is currently performing autocomplete lookups
     * as keystrokes are performed. Looks up the value in UISettings.
     * 
     * @return True or false.
     */
    public boolean getAutoComplete() {
        // return UISettings.AUTOCOMPLETE_ENABLED.getValue();
        return true;
    }

    /**
     * Adds the current value of the field underlying dictionary
     */
    public void addToDictionary() {
        dict.addEntry(this.getText().trim());
    }

    /**
     * Adds the specified string to the underlying dictionary
     */
    public void addToDictionary(final Object s) {
        if (dict == null) {
            dict = this.createDefaultDictionary();
        }
        dict.addEntry(s);
        
    }
    public void addToDictionary(Object key,final Observable s) {
        if (dict == null) {
            dict = this.createDefaultDictionary();
        }
        dict.addEntry(key.toString(), s);
    }

    // ----------------------------------------------------------------------------
    // Protected methods
    // ----------------------------------------------------------------------------

    /**
     * Gets the component that is the popup listing other choices.
     */
    protected JComponent getPopupComponent() {
        if (entryPanel != null)
            return entryPanel;

        entryPanel = new JPanel(new GridBagLayout());
        entryPanel.setBorder(UIManager.getBorder("List.border"));
        entryPanel.setBackground(UIManager.getColor("List.background"));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;

        entryList = new AutoCompleteList();
        JScrollPane entryScrollPane = new JScrollPane(entryList);
        entryScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        entryScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        entryPanel.add(entryScrollPane, c);

        entryPanel.add(new ClearHistory(), c);

        return entryPanel;
    }

    /**
     * Fills the popup with text & shows it.
     */
    protected void showPopup(final Iterator iter) {
        this.getPopupComponent(); // construct the component.

        boolean different = false;
        Vector v = new Vector();
        ListModel model = entryList.getModel();
        for (int i = 0; iter.hasNext(); i++) {
            Object next = iter.next();
            v.add(next);

            if (!different && i < model.getSize()) {
                different |= !next.equals(model.getElementAt(i));
            }
        }

        different |= model.getSize() != v.size();

        // if things were different, reset the data.
        if (different) {
            entryList.setListData(v);
            entryList.clearSelection();
        }

        entryList.setCurrentText(this.getText());
        this.showPopup();
    }

    /**
     * Shows the popup.
     */
    public void showPopup() {
        // only show the popup if we're currently visible.
        // due to delay in focus-forwarding & key-pressing events,
        // we may not be visible by the time this is called.
        if (popup == null && entryList.getModel().getSize() > 0) {
            if (this.isShowing()) {
                Point origin = this.getLocationOnScreen();
                PopupFactory pf = PopupFactory.getSharedInstance();
                Component parent = this;
                // OSX doesn't handle MOUSE_CLICKED events correctly
                // using medium-weight popups, so we need to force
                // PopupFactory to return a heavy-weight popup.
                // This is done by adding a panel into a Popup, which implicitly
                // adds it into a Popup.HeavyWeightWindow, which PopupFactory
                // happens
                // to check as a condition for returning a heavy-weight popup.
                // In an ideal world, the OSX bug would be fixed.
                // In a less ideal world, Popup & PopupFactory would be written
                // so that
                // outside developers can correctly subclass methods.
                if (CommonUtils.isMacOSX()) {
                    parent = new JPanel();
                    new MyPopup(this, parent, 0, 0);
                }
                popup = pf.getPopup(parent, this.getPopupComponent(), origin.x, origin.y + this.getHeight() + 1);
                showPending = false;
                popup.show();
            } else {
                showPending = true;
            }
        }
    }

    /**
     * Hides the popup window.
     */
    public void hidePopup() {
        showPending = false;
        if (popup != null) {
            popup.hide();
            popup = null;
        }
    }

    /**
     * Displays the popup window with a list of auto-completable choices, if any
     * exist.
     */
    public void autoCompleteInput() {
        String input = this.getText();
        if (input != null && input.length() > 0) {
            Iterator it = dict.getIterator(input);
            if (it.hasNext()) {
                this.showPopup(it);
            } else {
                this.hidePopup();
            }
        } else {
            this.hidePopup();
        }
    }

    // ----------------------------------------------------------------------------
    // Fields
    // ----------------------------------------------------------------------------
    /** The dictionary used for autocompletion. */
    protected AutoCompleteDictionary dict;

    /** The list auto-completable items are shown in */
    protected AutoCompleteList entryList;

    /** The panel the popup is shown in. */
    protected JPanel entryPanel;

    /** The popup the scroll pane is in */
    protected Popup popup;

    /** Whether or not we tried to show a popup while this wasn't showing */
    protected boolean showPending;

    /**
     * Component that clears the history of the dictionary when clicked.
     */
    private class ClearHistory extends JButton {

        private static final long serialVersionUID = 1L;

        ClearHistory() {
            // super(GUIMediator.getStringResource("GENERAL_CLEAR_HISTORY"));
            this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
            this.setFocusable(false);
        }

        @Override
        protected void processMouseEvent(final MouseEvent me) {
            super.processMouseEvent(me);

            if (me.getID() == MouseEvent.MOUSE_CLICKED) {
               // dict.clear();	
                //AutoCompleteTextField.this.hidePopup();
            }
        }
    }

    /**
     * A list that's used to show auto-complete items.
     */
    private class AutoCompleteList extends JList {
        private static final long serialVersionUID = 1L;

        private String currentText;

        private DefaultComboBoxModel listaPersonasModel;

        AutoCompleteList() {
            super();
            listaPersonasModel = new DefaultComboBoxModel(new String[] {});
            this.setModel(listaPersonasModel);
            this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.setFocusable(true);
        }

        /**
         * Sets the text field's selection with the clicked item.
         */

        protected void selectText() {
            String selection = (String) this.getSelectedStringValue();
            AutoCompleteTextField.this.setValue(selection);
            AutoCompleteTextField.this.hidePopup();
        }

        @Override
        protected void processMouseEvent(final MouseEvent me) {
            super.processMouseEvent(me);
            if (me.getID() == MouseEvent.MOUSE_PRESSED) {
            	AutoCompleteTextField.this.setValue(currentText);
                this.selectText();
                AutoCompleteTextField.this.fireActionPerformed();
            }
        }

        /**
         * Sets the text to place in the text field when items are unselected.
         */
        void setCurrentText(final String text) {
            currentText = text;
        }

        /**
         * Increments the selection by one.
         */
        void incrementSelection() {
            if (this.getSelectedIndex() == this.getModel().getSize() - 1) {
                AutoCompleteTextField.this.setValue(currentText);
                this.clearSelection();
            } else {
                int selectedIndex = this.getSelectedIndex() + 1;
                this.setSelectedIndex(selectedIndex);
                this.ensureIndexIsVisible(selectedIndex);
                AutoCompleteTextField.this.setValue(this.getSelectedStringValue());
            }
        }

        /**
         * Decrements the selection by one.
         */
        void decrementSelection() {
            if (this.getSelectedIndex() == 0) {
                AutoCompleteTextField.this.setValue(currentText);
                this.clearSelection();
            } else {
                int selectedIndex = this.getSelectedIndex();
                if (selectedIndex == -1) {
                    selectedIndex = this.getModel().getSize() - 1;
                } else {
                    selectedIndex--;
                }
                this.setSelectedIndex(selectedIndex);
                this.ensureIndexIsVisible(selectedIndex);
                AutoCompleteTextField.this.setValue(this.getSelectedStringValue());
            }
        }

        /**
         * Sets the size according to the number of entries.
         */
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            int width = AutoCompleteTextField.this.getSize().width - 2;
            int rows = Math.min(this.getModel().getSize(), 8);
            int height = rows * this.getCellBounds(0, 0).height;
            return new Dimension(width, height);
        }
        
        public String getSelectedStringValue() {
            if(super.getSelectedValue()!=null)
                return super.getSelectedValue().toString();
            else
                return currentText;
        }
    }

    /**
     * Subclass that provides access to the constructor.
     */
    private static class MyPopup extends Popup {
        public MyPopup(final Component owner, final Component contents, final int x, final int y) {
            super(owner, contents, x, y);
        }
    }

    public static void main(final String[] args) {
    	CenteredJFrame frame = new CenteredJFrame();
        AutoCompleteTextField text = new AutoCompleteTextField();
        text.addToDictionary("aaaa");
        text.addToDictionary("aaee");
        text.addToDictionary("arrr");
        text.addToDictionary("aaaer");
        frame.add(text);
        frame.setSize(200, 200);
        frame.setVisible(true);
        text.showPopup();
    }
}
