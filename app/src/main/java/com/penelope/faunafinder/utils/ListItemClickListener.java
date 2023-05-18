package com.penelope.faunafinder.utils;

/**
 * <code>ListItemClickListener</code> provides a callback to be called on an item's click
 */
public interface ListItemClickListener {
    /**
     * Item click callback to be called within a slide's onItemClickListener.
     *
     * @param id The information to pass to the callback.
     */
    void onItemClick(int id);
}
