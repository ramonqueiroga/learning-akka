package actors.hierarchy;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;

class PrintMyActorRefActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("printit", p-> {
            ActorRef secondRef = this.getContext().actorOf(Props.empty(), "secondRef");
            System.out.println("Second ref: " + secondRef);
        }).build();
    }
}


public class ActorHierarchyExperiments {

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create("hierarchyTest");
        ActorRef firstRef = actorSystem.actorOf(Props.create(PrintMyActorRefActor.class), "firstRef");

        System.out.println("First ref: " + firstRef);
        firstRef.tell("printit", ActorRef.noSender());

        System.out.println(">>> Press ENTER to exit <<<");
        try {
            System.in.read();
        } finally {
            actorSystem.terminate();
        }



    }


}
