package com.sparta.teamssc.domain.team.exception;

public class TeamCreationFailedException extends RuntimeException {
    public TeamCreationFailedException(String message) {
        super(message);
    }
}