package com.hivemind.app.api

import caliban.*
import caliban.schema.Schema
import caliban.schema.ArgBuilder.auto.*
import caliban.schema.Schema.auto.*
import caliban.wrappers.Wrappers.*
import com.hivemind.app.BookRepository
import com.hivemind.app.model.Book
import zio.*

import scala.language.postfixOps

class BookApi(bookRepo: BookRepository) {

  // Case classes for Queries and Mutations
  private case class Queries(getBooks: UIO[List[Book]])
  private case class Mutations(addBook: Book => UIO[Book])

  // Instances of Queries and Mutations
  private val queries = Queries(getBooks = bookRepo.getBooks)
  private val mutations = Mutations(addBook = bookRepo.addBook)

  // Build the GraphQL API
  val api: GraphQL[Any] =
    graphQL(RootResolver(queries, mutations)) @@
      maxFields(300) @@ // query analyzer that limit query fields
      maxDepth(30) @@ // query analyzer that limit query depth
      timeout(3 seconds) @@ // wrapper that fails slow queries
      printErrors // wrapper that logs errors
}

object BookApi {
  // Provide the API as a ZLayer
  val live: ZLayer[BookRepository, Nothing, GraphQL[Any]] =
    ZLayer {
      for {
        repo <- ZIO.service[BookRepository]
      } yield new BookApi(repo).api
    }
}
