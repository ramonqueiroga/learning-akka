package actors.hooks;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class StartStopActor1 extends AbstractActor {

    @Override
    public void preStart() throws Exception {
        System.out.println("First started");
        this.getContext().actorOf(Props.create(StartStopActor2.class), "second");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("First stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("stop", s -> this.getContext().stop(this.getSelf())).build();
    }
}
