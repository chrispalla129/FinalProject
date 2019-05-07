package towers.model

import GUI._
import GameObj.PhysicsVector
import akka.actor.{Actor, ActorRef}


class GameActor extends Actor {
  var players: Map[String, ActorRef] = Map()
  var towers: List[ActorRef] = List()
  val game: Game = new Game()
  var levelNumber = 0


  def loadLevel(levelNumber: Int): Unit ={
    game.load()
  }

  override def receive: Receive = {
    case message: AddPlayer => game.addPlayer(message.username)
    case message: RemovePlayer => game.removePlayer(message.username)
    case message: MovePlayer =>
      for(i <- game.board.Tanks) if(i.id == message.username) i.move(new PhysicsVector(message.x, message.y))
    case message: StopPlayer => for(i <- game.board.Tanks) if(i.id == message.username) i.stop()
    case UpdateGame => game.update()
    case SendGameState => sender() ! GameState(game.gameState())
    case projectile: AddProjectile => game.shoot(projectile.x, projectile.y, projectile.username)

  }
}
