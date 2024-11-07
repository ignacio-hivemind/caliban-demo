package com.hivemind.app

import com.hivemind.app.api.BookApi
import caliban._
import caliban.quick._
import zio._

object BookApp extends ZIOAppDefault {

  // Only as a convenience of shutting down the server via CLI, don't add to production code!
  private val cliShutdownSignal =
    Console.printLine("Server online at http://localhost:8090/") *>
      Console.printLine("Press RETURN to stop...") *>
      Console.readLine.unit

  private val serve =
    ZIO
      .serviceWithZIO[GraphQL[Any]] {
        _.runServer(
          port = 8090,
          apiPath = "/api/graphql",
          graphiqlPath = Some("/graphiql"),
          webSocketPath = Some("/ws/graphql")
        )
      }
      .provide(BookApi.live, BookRepository.live)

  override def run: ZIO[Any, Throwable, Unit] =
    serve race cliShutdownSignal
}
