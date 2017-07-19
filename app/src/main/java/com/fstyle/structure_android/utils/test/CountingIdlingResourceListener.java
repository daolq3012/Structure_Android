package com.fstyle.structure_android.utils.test;

/**
 * Interface to notify of resources being busy or idle
 */
public interface CountingIdlingResourceListener {
    void increment();

    void decrement();
}
