package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewManagerModel for managing and changing view states.
 */
public class ViewManagerModel {

    private final PropertyChangeSupport support;
    private String currentState;

    public ViewManagerModel() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public synchronized void setState(String newState) {
        if (newState == null || newState.equals(currentState)) {
            return; // Avoid setting to null or firing an unnecessary update
        }
        String oldState = this.currentState;
        this.currentState = newState;
        support.firePropertyChange("state", oldState, newState);
    }

    public synchronized void firePropertyChanged() {
        support.firePropertyChange("state", null, currentState);
    }

    public String getState() {
        return currentState;
    }
}
