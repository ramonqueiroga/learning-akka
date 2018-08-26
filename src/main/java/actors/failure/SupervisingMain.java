package actors.failure;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;

public class SupervisingMain {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("supervisingSytem");
        ActorRef supervising = actorSystem.actorOf(Props.create(SupervisingActor.class), "supervising");
        supervising.tell("failChild", ActorRef.noSender());

        System.out.println(">>> Press any key to end <<<");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            actorSystem.terminate();
        }
    }

}
