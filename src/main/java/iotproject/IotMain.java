package iotproject;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class IotMain {

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create("iot-system");

        try {
            ActorRef supervisor = actorSystem.actorOf(IotSupervisor.props(), "iot-supervisor");
            System.out.println("Press ENTER to exit the system");
            System.in.read();
        } finally {
            actorSystem.terminate();
        }
    }
}
