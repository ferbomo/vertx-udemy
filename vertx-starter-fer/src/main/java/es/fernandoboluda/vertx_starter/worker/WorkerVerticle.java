package es.fernandoboluda.vertx_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {

  public static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Deployed as worker verticle {}", getClass().getName());
    startPromise.complete();
    Thread.sleep(5000);
    LOG.debug("Blocking operation done.");
  }

}
