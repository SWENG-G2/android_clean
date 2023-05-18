package com.penelope.faunafinder.utils;

/**
 * <code>ListItemClickAction</code> provides an action to be performed on slide click.
 */
public interface ListItemClickAction {
    /**
     * The action to be performed on slide click.
     *
     * @param id The information the action depends on.
     */
    void performAction(int id);
}
