/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.json;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("unused")
public final class JSONMessageQueue extends ConcurrentLinkedQueue<JSONMessage> {

    // Attributes
    private final int capacity;

    // Constructors
    public JSONMessageQueue() {
        super();
        capacity = Integer.MAX_VALUE;
    }
    public JSONMessageQueue(final int capacity) {
        super();
        if (capacity <= 0) throw new RuntimeException("Queue capacity must be >= 0");
        this.capacity = capacity;
    }

    // Methods
    public int getCapacity() {
        return capacity;
    }
    public int getAvailableCapacity() {
        return capacity - size();
    }
    public boolean isFull() {
        return capacity == size();
    }

    // Override Methods
    /**
     * Inserts the specified element at the tail of this queue if there is space available.
     * This method is equivalent to offer(JSONMessage).
     *
     * @param jsonMessage the message to add into the JSONMessageQueue.
     * @return true if there is space available in the queue, false otherwise.
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean add(JSONMessage jsonMessage) {
        if (size() + 1 < capacity) return super.add(jsonMessage);
        else return false;
    }
    /**
     * Inserts the specified element at the tail of this queue if there is space available.
     *
     * @param jsonMessage the message to add into the JSONMessageQueue.
     * @return true if there is space available in the queue, false otherwise.
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean offer(JSONMessage jsonMessage) {
        if (size() + 1 < capacity) return super.offer(jsonMessage);
        else return false;
    }
    /**
     * Inserts all the elements at the tail of this queue if there is space available.
     *
     * @param c the collection of JSONMessage to add into the queue.
     * @return true if there is space available in the queue, false otherwise.
     * @throws NullPointerException if the specified collection or any of its elements are null
     * @throws IllegalArgumentException if the collection is this queue
     */
    @Override
    public boolean addAll(Collection<? extends JSONMessage> c) {
        if (size() + c.size() < capacity) return super.addAll(c);
        else return false;
    }
}
