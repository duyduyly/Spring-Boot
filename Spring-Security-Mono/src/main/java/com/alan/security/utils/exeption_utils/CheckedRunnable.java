package com.alan.security.utils.exeption_utils;

@FunctionalInterface
public interface CheckedRunnable {
    void run() throws Exception;
}
