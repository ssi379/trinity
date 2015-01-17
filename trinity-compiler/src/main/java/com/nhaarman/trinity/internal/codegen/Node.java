package com.nhaarman.trinity.internal.codegen;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Node<T> {

    private final T mValue;
    private final Collection<Node<T>> mChildren;

    public Node(final T value) {
        mValue = value;
        mChildren = new HashSet<>();
    }

    public Collection<Node<T>> getChildren() {
        return Collections.unmodifiableCollection(mChildren);
    }

    public void add(final Node<T> node) {
        mChildren.add(node);
    }

    public void remove(final Node<T> node) {
        mChildren.remove(node);
    }

    public T getValue() {
        return mValue;
    }

    public Collection<T> values() {
        Collection<T> results = new ArrayDeque<>();
        results.add(mValue);

        for (Node<T> child : mChildren) {
            results.addAll(child.values());
        }

        return results;
    }

    /**
     * Does a depth-first search to detect cycles.
     *
     * @return true if a cycle is found, false otherwise.
     */
    public boolean isCyclic() {
        return isCyclic(new HashSet<Node<T>>());
    }

    /**
     * Does a depth-first search to detect cycles.
     *
     * @param visited The visited nodes.
     *
     * @return true if a cycle is found, false otherwise.
     */
    public boolean isCyclic(final Collection<Node<T>> visited) {
        if (visited.contains(this)) {
            return true;
        }

        if (mChildren.isEmpty()) {
            return false;
        }

        visited.add(this);

        boolean result = false;
        for (Node<T> child : mChildren) {
            result |= child.isCyclic(visited);
        }
        return result;
    }
}
