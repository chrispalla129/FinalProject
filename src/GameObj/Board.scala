package GameObj

import scala.collection.mutable.ListBuffer

class Board{
  var Tanks: ListBuffer[Tank] = ListBuffer()
  var Bullets: ListBuffer[Bullet] = ListBuffer()
  var boundaries: List[Boundary] = List()

  def respawn(tank: Tank): Unit ={
    this.Tanks -= tank
    val newTank: Tank = new Tank(tank.id, new PhysicsVector(Math.random() * 15, Math.random() * 15), new PhysicsVector(0,0))
    this.Tanks += newTank
  }

}
