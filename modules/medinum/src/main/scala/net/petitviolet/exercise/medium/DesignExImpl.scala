package net.petitviolet.exercise.medium

import scala.concurrent.{ ExecutionContext, Future }

case class Id[A](value: Long) extends AnyVal

trait Entity[A] { self =>
  def id: Id[A]

  override def equals(obj: Any): Boolean = {
    obj match {
      case e: Entity[_] => e.id == self.id
      case _ => false
    }
  }
}

case class Description(value: String) extends AnyVal
case class Task(
    id: Id[Task],
    status: Status,
    description: Description,
    assign: Option[Id[User]] = None
) extends Entity[Task] {
  def assignUser(userId: Id[User]): Task = this.copy(assign = Some(userId))
  def updateStatus(newStatus: Status): Task = this.copy(status = newStatus)
}

object Tasks {
  var all: Set[Task] =
    Set(
      Task(Id(1L), Status.Todo, Description("minagine"), None)
    )
}

case class UserName(value: String) extends AnyVal
case class User(id: Id[User], name: UserName) extends Entity[User]

object Users {
  var all: Set[User] =
    Set(
      User(Id(1L), UserName("alice")),
      User(Id(2L), UserName("bob")),
      User(Id(3L), UserName("charlie")),
      User(Id(4L), UserName("dave")),
      User(Id(5L), UserName("eve"))
    )
}

sealed abstract class Status(val value: Int)
object Status {
  case object Todo extends Status(0)
  case object Doing extends Status(1)
  case object Done extends Status(2)

  private val values: Seq[Status] = Todo :: Doing :: Done :: Nil
  def valueOf(value: Int): Status = values.find { _.value == value }.get
}

trait Repository[A <: Entity[A]] {
  def store(entity: A)(implicit ec: ExecutionContext): Future[Id[A]]
  def findById(id: Id[A])(implicit ec: ExecutionContext): Future[Option[A]]
  def delete(id: Id[A])(implicit ec: ExecutionContext): Future[Boolean]
}

trait UserRepository extends Repository[User] {
  override def store(entity: User)(implicit ec: ExecutionContext): Future[Id[User]] = Future {
    Users.all = Users.all + entity
    entity.id
  }

  override def findById(id: Id[User])(implicit ec: ExecutionContext): Future[Option[User]] = Future {
    Users.all find { _.id == id }
  }

  override def delete(id: Id[User])(implicit ec: ExecutionContext): Future[Boolean] =
    findById(id) map {
      _.fold(false) { user =>
        Users.all - user
        true
      }
    }
}

trait TaskRepository extends Repository[Task] {
  override def store(entity: Task)(implicit ec: ExecutionContext): Future[Id[Task]] = Future {
    Tasks.all = Tasks.all + entity
    entity.id
  }

  override def findById(id: Id[Task])(implicit ec: ExecutionContext): Future[Option[Task]] = Future {
    Tasks.all find { _.id == id }
  }

  override def delete(id: Id[Task])(implicit ec: ExecutionContext): Future[Boolean] =
    findById(id) map {
      _.fold(false) { task =>
        Tasks.all - task
        true
      }
    }

  def assign(task: Task, userId: Id[User])(implicit ec: ExecutionContext): Future[Task] = {
    val newTask = task.assignUser(userId)
    store(newTask) map { _ => newTask }
  }

  private def updateStatus(task: Task, status: Status)(implicit ec: ExecutionContext): Future[Task] = {
    val newTask = task.updateStatus(status)
    store(newTask) map { _ => newTask }
  }

  def done(task: Task)(implicit ec: ExecutionContext): Future[Task] = updateStatus(task, Status.Done)

  def undo(task: Task)(implicit ec: ExecutionContext): Future[Task] = updateStatus(task, Status.Todo)

  def doing(task: Task)(implicit ec: ExecutionContext): Future[Task] = updateStatus(task, Status.Doing)
}

trait UsesUserRepository {
  val userRepository: UserRepository
}

trait MixInUserRepository {
  val userRepository: UserRepository = UserRepositoryImpl
}

private object UserRepositoryImpl extends UserRepository

trait UsesTaskRepository {
  val taskRepository: TaskRepository
}

trait MixInTaskRepository {
  val taskRepository: TaskRepository = TaskRepositoryImpl
}

private object TaskRepositoryImpl extends TaskRepository

trait DesignExImpl extends DesignEx {

}
