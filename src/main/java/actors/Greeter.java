package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.apache.commons.lang3.SerializationUtils;

public class Greeter extends AbstractActor {

    static public Props props(String message, ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }

    public static final class WhoToGreet {
        private final String message;

        public WhoToGreet(String message) {
            this.message = message;
        }

        public String getMessage() {
            return SerializationUtils.clone(this.message);
        }
    }

    public static final class Greet {
    }


    private String message;
    private ActorRef printerActor;
    private String greeting = "";

    public Greeter(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WhoToGreet.class, who -> this.greeting = String.format("%s, %s", this.message, who.getMessage()))
                .match(Greet.class, greet -> this.printerActor.tell(new Printer.Greeting(this.greeting), getSelf()))
                .build();
    }
}
