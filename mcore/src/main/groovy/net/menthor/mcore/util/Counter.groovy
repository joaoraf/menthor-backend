package net.menthor.mcore.util

class Counter {

    int value = 1; // note that we start at 1 since we're counting

    public void increment() { ++value; }
    public void decrease() { --value; }
    public int count() { return value; }
}