package actors;

import actors.Greeter;
import actors.Printer;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AkkaSystemTest {

    private static ActorSystem actorSystem;

    @BeforeClass
    public static void setUp() {
        actorSystem = ActorSystem.create();
    }

    @AfterClass
    public static void tearDown() {
        TestKit.shutdownActorSystem(actorSystem);
        actorSystem = null;
    }

    @Test
    public void testGreetingActor() {
        final TestKit testProbe = new TestKit(actorSystem);
        ActorRef helloGreeter = actorSystem.actorOf(Greeter.props("Olá meu camarada", testProbe.getRef()));
        helloGreeter.tell(new Greeter.WhoToGreet("Ramon"), ActorRef.noSender());
        helloGreeter.tell(new Greeter.Greet(), ActorRef.noSender());
        Printer.Greeting greeting = testProbe.expectMsgClass(Printer.Greeting.class);
        assertEquals("Olá meu camarada, Ramon", greeting.getMessage());
    }

    @Test
    public void testPrinterActor() {
        final TestKit testProbe = new TestKit(actorSystem);
        ActorRef printer = actorSystem.actorOf(Printer.props());
        printer.tell(new Printer.Greeting("Hello, Akka!"), testProbe.getRef());
        testProbe.expectNoMessage();
    }

}
