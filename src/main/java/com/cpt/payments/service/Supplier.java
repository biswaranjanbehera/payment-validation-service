package com.cpt.payments.service;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
//functional inteface is that interface which have only one abstract method