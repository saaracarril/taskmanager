package com.example.taskmanager.exception;

import com.example.taskmanager.domain.Status;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(Status from, Status to) {
        super("Invalid status transition from " + from + " to " + to);
    }
}