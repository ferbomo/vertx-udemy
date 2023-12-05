package es.fernandoboluda.vertx_starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    System.out.println("Start " + getClass().getName() + " on thread " + Thread.currentThread().getName()
      + " with config " + config().toString());
    startPromise.complete();
  }

}
