package com.google.codeu.codingchallenge;

import java.util.*;

/**
 * Created by Pablo on 4/19/2017.
 */


class GenQueue<E extends Comparable<? super E>> {
    public GenQueue(GenQueue<E> commas) {
        this.list = new LinkedList<>(commas.list);
    }
    public GenQueue() {

    }
    public static <E extends Comparable<E>> int compareTo(E x, E y) {

        if(x == null)
            return -(y.compareTo(x));

        return x.compareTo(y);

    }
    private LinkedList<E> list = new LinkedList<E>();
    public void enqueue(E item) {
        list.addLast(item);
    }
    public E dequeue() {
        return list.poll();
    }
    public boolean hasItems() {
        return !list.isEmpty();
    }
    public int size() {
        return list.size();
    }
    public void addItems(GenQueue<? extends E> q) {
        while (q.hasItems()) list.addLast(q.dequeue());
    }

    public E findFirstLarge(E k) {
        E val = null;
        for (int i = 0; i < list.size(); i++) {

        }
        return val;
    }

    public E peek() {
        return list.peek();
    }

    public boolean isEmpty() {
        return !(list.size()>0);
    }


}