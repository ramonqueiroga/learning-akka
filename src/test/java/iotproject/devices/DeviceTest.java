package iotproject.devices;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class DeviceTest {

    private static ActorSystem actorSystem;

    @BeforeClass
    public static void setUp() {
        actorSystem = ActorSystem.create("system");
    }

    @AfterClass
    public static void tearDown() {
        TestKit.shutdownActorSystem(actorSystem);
        actorSystem = null;
    }

    @Test
    public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {
        TestKit probe = new TestKit(actorSystem);

        ActorRef deviceActor = actorSystem.actorOf(Device.props("group", "device"), "deviceActor");
        deviceActor.tell(new Device.ReadTemperature(42L), probe.getRef());

        Device.RespondTemperature respondTemperature = probe.expectMsgClass(Device.RespondTemperature.class);

        assertEquals(42L, respondTemperature.getRequestId());
        assertEquals(Optional.empty(), respondTemperature.getValue());
    }

    @Test
    public void testReplyWithLatestTemperatureReading() {
        TestKit probe = new TestKit(actorSystem);

        ActorRef deviceActor = actorSystem.actorOf(Device.props("group", "device"), "deviceActorWithResponse");

        deviceActor.tell(new Device.RecordTemperature(1L, 24.0), probe.getRef());
        Device.TemperatureRecorded temperatureRecorded = probe.expectMsgClass(Device.TemperatureRecorded.class);
        assertEquals(1L, temperatureRecorded.getRequestId());

        deviceActor.tell(new Device.ReadTemperature(2L), probe.getRef());
        Device.RespondTemperature respondTemperature = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(2L, respondTemperature.getRequestId());
        assertEquals(Optional.of(24.0), respondTemperature.getValue());

        deviceActor.tell(new Device.RecordTemperature(3L, 55.0), probe.getRef());
        Device.TemperatureRecorded temperatureRecorded2 = probe.expectMsgClass(Device.TemperatureRecorded.class);
        assertEquals(3L, temperatureRecorded2.getRequestId());

        deviceActor.tell(new Device.ReadTemperature(4L), probe.getRef());
        Device.RespondTemperature respondTemperature2 = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(4L, respondTemperature2.getRequestId());
        assertEquals(Optional.of(55.0), respondTemperature2.getValue());
    }

}