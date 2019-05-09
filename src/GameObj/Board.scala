package GameObj

import scala.collection.mutable.ListBuffer

class Board{
  var Tanks: ListBuffer[Tank] = ListBuffer()
  var Bullets: ListBuffer[Bullet] = ListBuffer()
  var boundaries: List[Boundary] = List()

  def respawn(tank: Tank): Unit ={
    val width = Math.random() * 15
    val height = Math.random() * 15

    this.Tanks -= tank
    this.Tanks += new Tank(tank.id, new PhysicsVector(width, height), new PhysicsVector(0,0))
    println("Tank " + tank.id + " respawned at " + width + ", " + height)
  }
}
