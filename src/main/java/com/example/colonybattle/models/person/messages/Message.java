package com.example.colonybattle.models.person.messages;

import com.example.colonybattle.models.person.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {
    private String msg;
    private Person person;
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
