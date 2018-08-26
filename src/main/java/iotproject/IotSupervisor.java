package iotproject;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class IotSupervisor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    public static Props props() {
        return Props.create(IotSupervisor.class);
    }

    @Override
    public void preStart() throws Exception {
        this.log.info("IoT Application  started!");
    }

    @Override
    public void postStop() throws Exception {
        this.log.info("IoT Application stopped!");
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder().build();
    }
}
