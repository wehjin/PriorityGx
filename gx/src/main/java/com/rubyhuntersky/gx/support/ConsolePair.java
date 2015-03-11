package com.rubyhuntersky.gx.support;

import com.rubyhuntersky.gx.basic.Console;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public class ConsolePair {

    private final ConsoleList consoleList;

    public ConsolePair(Console console) {
        consoleList = new ConsoleList(console, 2);
    }

    public Console getBackConsole() {
        return consoleList.getConsole(0);
    }

    public Console getFrontConsole() {
        return consoleList.getConsole(1);
    }
}
