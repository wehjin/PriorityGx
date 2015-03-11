package com.rubyhuntersky.gx.basic;

/**
 * @author wehjin
 * @since 2/11/15.
 */

public interface Interactor<T> {
    Units getUnits();
    Console getConsole();
    T getSettings();
    Monitor<? super T> getMonitor();
}
