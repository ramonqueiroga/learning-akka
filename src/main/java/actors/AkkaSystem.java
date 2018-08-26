package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class AkkaSystem {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("helloAkka");
        try {
            ActorRef printer = actorSystem.actorOf(Printer.props(), "printer");

            ActorRef goodNightActor = actorSystem.actorOf(Greeter.props("Good Night", printer), "goodNight");
            ActorRef goodMorningActor = actorSystem.actorOf(Greeter.props("Good Morning!!!", printer), "goodMorning");

            goodNightActor.tell(new Greeter.WhoToGreet("Ramon"), ActorRef.noSender());
            goodNightActor.tell(new Greeter.Greet(), ActorRef.noSender());

            goodMorningActor.tell(new Greeter.WhoToGreet("Fernanda"), ActorRef.noSender());
            goodMorningActor.tell(new Greeter.Greet(), ActorRef.noSender());

            System.in.read();

        } catch (IOException ex) {

        } finally {
            actorSystem.terminate();
        }
    }

}
