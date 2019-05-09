package GameObj
object Physics {
  def computePotentialLocation(location: PhysicsVector, velocity: PhysicsVector, time: Double): PhysicsVector = {
    val x: Double = location.x + velocity.x * time
    val y: Double = location.y + velocity.y * time

    return new PhysicsVector(x,y)
  }

  def updateWorld(board: Board, deltaTime: Double): Unit = {
    var i = -1

    for (bullet <- board.Bullets) {
      val potentialLocation: Bullet = new Bullet(bullet.id, computePotentialLocation(bullet.location, bullet.velocity, deltaTime), bullet.velocity)
      i += 1

      board.Bullets(i) = potentialLocation
    }
    i = -1
    for(tank <- board.Tanks){
      val potentialLocation: Tank = new Tank(tank.id, computePotentialLocation(tank.location, tank.velocity, deltaTime), tank.velocity)
      i += 1

      board.Tanks(i) = potentialLocation
    }

    checkForPlayerHits(board)
  }

  def checkForPlayerHits(board: Board): Unit = {
    for(bullet <- board.Bullets){
      for(tank <- board.Tanks){
        if(bullet.id != tank.id) {
          val distance: Double = Math.sqrt(Math.pow(tank.location.x - bullet.location.x, 2) + Math.pow(tank.location.y - bullet.location.y, 2))
          if (Math.abs(distance) < .3){
            val id = bullet.id

            for(i <- board.Tanks) if(i.id == id) i.numOfKills += 1
            println("Tank " + tank.id + " was hit by Tank " + id)
            board.respawn(tank)
            tank.numOfDeaths += 1
            board.Bullets -= bullet
          }
        }
      }
    }
  }

}
