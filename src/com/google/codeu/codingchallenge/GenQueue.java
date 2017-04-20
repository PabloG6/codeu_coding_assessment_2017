package com.google.codeu.codingchallenge;

import java.util.*;

/**
 * Created by Pablo on 4/19/2017.
 */


class GenQueue {

    public GenQueue(GenQueue commas) {
        this.list = new LinkedList<Integer>(commas.list);
    }

    public GenQueue() {

    }

    public static <Integer extends Comparable<Integer>> int compareTo(Integer x, Integer y) {

        if (x == null)
            return (y.compareTo(x));

        return x.compareTo(y);

    }

    private LinkedList<Integer> list = new LinkedList<Integer>();

    public void enqueue(Integer item) {
        list.addLast(item);
    }

    public synchronized Integer dequeue() {
        return list.poll();
    }

    public boolean hasItems() {
        return !list.isEmpty();
    }

    public int size() {
        return list.size();
    }


    public Integer findFirstLarge(Integer k) {
        for (int i = 0; i < list.size(); i++) {
            if(k > list.get(i)) {
                return list.get(i);
            }
        }
        return -1;
    }

    public Integer peek() {
        return list.peek();
    }

    public Integer findLessThan(int k) {
        for (int i = 0; i < list.size(); i++) {
        }
        return -1;
    }

    public boolean isEmpty() {
        return !(list.size() > 0);
    }


}