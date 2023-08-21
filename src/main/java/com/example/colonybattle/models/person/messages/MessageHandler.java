package com.example.colonybattle.models.person.messages;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.messages.Message;
import com.example.colonybattle.models.person.messages.DestinationMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.*;

@Getter
public class MessageHandler {
    private final BlockingQueue<Message> queue;
    private final BlockingQueue<DestinationMessage> destinationMessages;

    public MessageHandler() {
        this.queue = new LinkedBlockingQueue<>();
        this.destinationMessages = new LinkedBlockingQueue<>();
    }

    public void send(Person person, Message message) {
        person.getMessageHandler().receive(message);
    }

    public void receive(Message message) {
        this.queue.add(message);
    }

    public void setNewTarget(DestinationMessage message) {
        this.destinationMessages.add(message);
    }

    public void handleReceivedMessages(Person person) {
        while (!queue.isEmpty()) {
            Message message = queue.poll();
            if (!person.getKills().hasKilled(message.getPerson())) {
                person.getKills().addKill(message.getPerson());
                person.addPoints(30);
                System.out.println(message.getPerson() + " was killed by " + person);
                message.getPerson().getBoardRef().removePersonFromFrame(); // making sure that person is removed from frame
            }
        }
    }

    public Point2d processMessage() {
        BlockingQueue<DestinationMessage> messages = getDestinationMessages();
        DestinationMessage message = null;
        while(!messages.isEmpty()) {
            message = messages.poll();
        }
        return (message == null) ? null : message.getDestination();
    }
}
