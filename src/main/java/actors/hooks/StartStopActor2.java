package actors.hooks;

import akka.actor.AbstractActor;

public class StartStopActor2 extends AbstractActor {

    @Override
    public void preStart() throws Exception {
        System.out.println("Second started");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("Second stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
