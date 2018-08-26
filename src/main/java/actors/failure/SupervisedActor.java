package actors.failure;

import akka.actor.AbstractActor;

import java.util.Optional;

public class SupervisedActor extends AbstractActor {

    @Override
    public void postStop() throws Exception {
        System.out.println("Supervised actor stopped");
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("Supervised actor started");
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        System.out.println("Supervised actor restarted by a failure action: " + reason.getMessage());
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        System.out.println("Supervised actor has been restarted");
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder().matchEquals("fail", f -> {
            System.out.println("Supervised actor fails now");
            throw new Exception("I failed!");
        }).build();
    }
}
