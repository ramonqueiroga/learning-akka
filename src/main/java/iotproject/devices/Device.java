package iotproject.devices;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Optional;

public class Device extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);
    private final String groupId;
    private final String deviceId;
    private Optional<Double> lastTemperatureReading = Optional.empty();

    public Device(String groupId, String deviceId) {
        this.groupId = groupId;
        this.deviceId = deviceId;
    }

    public static Props props(String groupId, String deviceId) {
        return Props.create(Device.class, () -> new Device(groupId, deviceId));
    }

    public static final class ReadTemperature {
        private long requestId;
        public ReadTemperature(long requestId) {
            this.requestId = requestId;
        }
    }

    public static final class RecordTemperature {
        private final long requestId;
        private final Double value;

        public RecordTemperature(long requestId, Double value) {
            this.requestId = requestId;
            this.value = value;
        }
    }

    public static final class TemperatureRecorded {
        private final long requestId;

        public TemperatureRecorded(long requestId) {
            this.requestId = requestId;
        }

        public long getRequestId() {
            return requestId;
        }
    }

    public static final class RespondTemperature {
        private long requestId;
        private Optional<Double> value;
        public RespondTemperature(long requestId, Optional<Double> value) {
            this.requestId = requestId;
            this.value = value;
        }

        public long getRequestId() {
            return requestId;
        }

        public Optional<Double> getValue() {
            return value;
        }
    }

    @Override
    public void preStart() throws Exception {
        log.info("Device actor {}-{} started",this.groupId, this.deviceId);
    }

    @Override
    public void postStop() throws Exception {
        log.info("Device actor {}-{} stopped", this.groupId, this.deviceId);
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(RecordTemperature.class, r -> {
                    log.info("Recorded temperature reading {} with {}", r.value, r.requestId);
                    this.lastTemperatureReading = Optional.of(r.value);
                    this.getSender().tell(new TemperatureRecorded(r.requestId), this.getSelf());
                })
                .match(ReadTemperature.class, r -> this.getSender().tell(new RespondTemperature(r.requestId, this.lastTemperatureReading), this.getSelf()))
                .build();
    }
}
