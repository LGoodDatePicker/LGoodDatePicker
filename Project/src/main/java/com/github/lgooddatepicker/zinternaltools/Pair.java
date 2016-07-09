package com.github.lgooddatepicker.zinternaltools;

/**
 * Pair, This class allows storing and editing of pairs of data. Since this class uses generics, the
 * data may be any java class type.
 */
public class Pair<A, B> {

    public A first;
    public B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}
