package com.hivemind.app

import com.hivemind.app.model.Book
import zio.*

trait BookRepository {
  def getBooks: UIO[List[Book]]
  def addBook(book: Book): UIO[Book]
}

object BookRepository extends BookRepository {
  // Sample repository
  private var books: List[Book] = List(
    Book(1, "1984", "George Orwell"),
    Book(2, "Brave New World", "Aldous Huxley"),
  )

  // Operations
  override def getBooks: UIO[List[Book]]      = ZIO.succeed(books)
  override def addBook(book: Book): UIO[Book] = ZIO.succeed {
    books = books :+ book
    book
  }

  val live: ZLayer[Any, Nothing, BookRepository] =
    ZLayer.succeed(BookRepository)
}
