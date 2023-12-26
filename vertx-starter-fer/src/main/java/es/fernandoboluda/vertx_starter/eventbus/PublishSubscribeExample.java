package es.fernandoboluda.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublishSubscribeExample {

  public static final String START = "Start {}";
  public static final Logger LOG = LoggerFactory.getLogger(PublishSubscribeExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(Subscriber2.class.getName(), new DeploymentOptions().setInstances(2));
  }

  public static class Publish extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      LOG.debug(START, getClass().getName());
      startPromise.complete();
      vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), id ->
        vertx.eventBus().publish(Publish.class.getName(), "A message for everyone!" + id));
    }
  }

  public static class Subscriber1 extends AbstractVerticle {

    public static final Logger LOG = LoggerFactory.getLogger(Subscriber1.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      LOG.debug(START, getClass().getName());
      startPromise.complete();
      vertx.eventBus().consumer(Publish.class.getName(), message -> {
        LOG.debug("Received: {}", message.body());
      });
    }

  }

  public static class Subscriber2 extends AbstractVerticle {

    public static final Logger LOG = LoggerFactory.getLogger(Subscriber2.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      LOG.debug(START, getClass().getName());
      startPromise.complete();
      vertx.eventBus().consumer(Publish.class.getName(), message -> {
        LOG.debug("Received: {}", message.body());
      });
    }

  }

}
