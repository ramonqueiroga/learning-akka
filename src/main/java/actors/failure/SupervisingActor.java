package actors.failure;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class SupervisingActor extends AbstractActor {

    private ActorRef child;

    @Override
    public void preStart() throws Exception {
        this.child = this.getContext().actorOf(Props.create(SupervisedActor.class), "supervided-actor");
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder().matchEquals("failChild", f -> {
            child.tell("fail", this.getSelf());
        }).build();
    }
}
