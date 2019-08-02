package com.ovlesser.message.db;

import com.ovlesser.message.model.Message;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestData {

    static final Message MESSAGE = new Message("name", true, new Date(), "1111", 0);
    static final Message MESSAGE2 = new Message("name2", false, new Date(), "1111", 0);
    static final List<Message> MESSAGES = Arrays.asList(MESSAGE, MESSAGE2);
}
