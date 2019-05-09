package GameObj


class Tank(var id: String, var location: PhysicsVector, var velocity: PhysicsVector) {
  var numOfKills: Int = 0
  var numOfDeaths: Int = 0

  def move(direction: PhysicsVector): Unit = {
    val normalDirection = direction.normal2d()
    velocity = new PhysicsVector(normalDirection.x * 4, normalDirection.y * 4)
  }

  def stop(): Unit = {
    this.velocity = new PhysicsVector(0, 0)
  }
}
