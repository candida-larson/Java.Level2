package com.gb.clientchat.clientchat.model;

import com.gb.clientchat.co.Command;

public interface ReadMessageListener {
    void processReceivedCommand(Command command);
}
