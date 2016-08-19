package com.finale.phiser.myapplication;

/**
 * Created by phiser on 05/04/2016.
 */
public class ChatMessage {
    public String message;
    public boolean left;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}
