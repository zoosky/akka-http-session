package com.softwaremill.session.javadsl

import java.util.function.Supplier

import akka.http.javadsl.server.Route
import akka.http.javadsl.server.directives.RouteAdapter
import com.softwaremill.session._

/**
  * Java alternative for  com.softwaremill.session.SessionDirectives
  */
trait SessionDirectives extends OneOffSessionDirectives with RefreshableSessionDirectives {

  def setSession[T](sc: SessionContinuity[T], st: SetSessionTransport, v: T, inner: Supplier[Route]): Route = RouteAdapter {
    com.softwaremill.session.SessionDirectives.setSession(sc, st, v) {
      inner.get.asInstanceOf[RouteAdapter].delegate
    }
  }

  def invalidateSession[T](sc: SessionContinuity[T], st: GetSessionTransport, inner: Supplier[Route]): Route = RouteAdapter {
    com.softwaremill.session.SessionDirectives.invalidateSession(sc, st) {
      inner.get.asInstanceOf[RouteAdapter].delegate
    }
  }

  def optionalSession[T](sc: SessionContinuity[T], st: GetSessionTransport, inner: java.util.function.Function[Option[T], Route]): Route = RouteAdapter {
    com.softwaremill.session.SessionDirectives.optionalSession(sc, st) { session =>
      inner.apply(session).asInstanceOf[RouteAdapter].delegate
    }
  }

  def requiredSession[T](sc: SessionContinuity[T], st: GetSessionTransport, inner: java.util.function.Function[T, Route]): Route = RouteAdapter {
    com.softwaremill.session.SessionDirectives.requiredSession(sc, st) { session =>
      inner.apply(session).asInstanceOf[RouteAdapter].delegate
    }
  }

  def touchRequiredSession[T](sc: SessionContinuity[T], st: GetSessionTransport, inner: java.util.function.Function[T, Route]): Route = RouteAdapter {
    com.softwaremill.session.SessionDirectives.touchRequiredSession(sc, st) { session =>
      inner.apply(session).asInstanceOf[RouteAdapter].delegate
    }
  }

}

object SessionDirectives extends SessionDirectives

