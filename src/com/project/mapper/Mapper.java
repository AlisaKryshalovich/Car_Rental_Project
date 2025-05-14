package com.project.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
