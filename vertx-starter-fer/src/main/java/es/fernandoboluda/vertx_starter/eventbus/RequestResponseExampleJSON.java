package es.fernandoboluda.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExampleJSON {

  public static final Logger LOG = LoggerFactory.getLogger(RequestResponseExampleJSON.class);
  public static final String ADDRESS = "my.request.address";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      LOG.debug("Start {}", getClass().getName());
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final JsonObject message = new JsonObject()
        .put("message", "Hello World!")
        .put("version", 1);
      LOG.debug("Sending: {}", message);
      eventBus.<JsonArray>request(ADDRESS, message, reply -> {
        LOG.debug("Response: {}", reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      LOG.debug("Start {}", getClass().getName());
      startPromise.complete();
      vertx.eventBus().<JsonObject>consumer(ADDRESS, message -> {
        LOG.debug("Received message {}", message.body());
        message.reply(new JsonArray().add("one").add("two").add("three"));
      });
    }

  }

}
