package es.fernandoboluda.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointToPointExample {

  public static final Logger LOG = LoggerFactory.getLogger(PointToPointExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }

  static class Sender extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      LOG.debug("Start {}", getClass().getName());
      startPromise.complete();
      vertx.setPeriodic(1000, id -> {
        //Send a message every second
        vertx.eventBus().send(Sender.class.getName(), "Sending a message...");
      });
    }
  }

  static class Receiver extends AbstractVerticle {

    public static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      LOG.debug("Start {}", getClass().getName());
      startPromise.complete();
      vertx.eventBus().consumer(Sender.class.getName(), message -> LOG.debug("Received: {}", message.body()));
    }

  }

}
