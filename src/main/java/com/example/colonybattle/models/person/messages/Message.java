package com.example.colonybattle.models.person.messages;

import com.example.colonybattle.models.person.Person;

public class Message {
    private String msg;
    private Person person;

    public Message(String str,Person person){
        this.msg=str;
        this.person=person;
    }

    public String getMsg() {
        return msg;
    }

    public Person getPerson() {
        return person;
    }

    //to string override
    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", person=" + person +
                '}';
    }
    @Override //equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message that = (Message) o;
        return this.msg.equals(that.msg) && this.person.equals(that.person);
    }
    @Override //hashcode
    public int hashCode() {
        return this.msg.hashCode()*31 + this.person.hashCode();
    }
}
