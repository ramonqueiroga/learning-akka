package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class Printer extends AbstractActor {

    static public Props props() {
        return Props.create(Printer.class, Printer::new);
    }

    public static final class Greeting implements Serializable {
        private final String message;

        public Greeting(String message) {
            this.message = message;
        }

        public String getMessage() {
            return SerializationUtils.clone(this.message);
        }
    }

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Greeting.class, greeting -> log.info(greeting.getMessage()))
                .build();
    }
}
