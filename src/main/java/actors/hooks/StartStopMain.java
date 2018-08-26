package actors.hooks;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;

public class StartStopMain {

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create("startStopSystem");

        ActorRef first = actorSystem.actorOf(Props.create(StartStopActor1.class), "first");
        first.tell("stop", ActorRef.noSender());

        System.out.println(">>> Press any key to end <<<");
        try {
            System.in.read();
        } finally {
            actorSystem.terminate();
        }

    }

}
