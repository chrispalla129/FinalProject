package GUI

import GameObj._
import play.api.libs.json.{JsValue, Json}

class Game {
  val windowWidth: Int = 15
  val windowHeight: Int = 15
  var board = new Board
  var lastUpdateTime: Long = System.nanoTime()

  def addPlayer(id: String): Unit ={
    val tank: Tank = new Tank(id, new PhysicsVector(Math.random() * windowWidth, Math.random() * windowHeight), new PhysicsVector(0,0))
    board.Tanks += tank
  }

  def removePlayer(id: String): Unit = {
    for(i <- board.Tanks) if(i.id == id) board.Tanks -= i
  }
  def load(): Unit = {
    blockTile(0, 0, windowWidth, windowHeight)
  }

  def update(): Unit = {
    val time: Long = System.nanoTime()
    val dt = (time - this.lastUpdateTime) / 1000000000.0
    Physics.updateWorld(board, dt)
    this.lastUpdateTime = time
  }

  def gameState(): String = {
    val gameState: Map[String, JsValue] = Map(
      "gridSize" -> Json.toJson(Map("x" -> this.windowWidth, "y" -> this.windowHeight)),

      "tanks" -> Json.toJson(board.Tanks.map({v => Json.toJson(Map(
        "x" -> Json.toJson(v.location.x),
        "y" -> Json.toJson(v.location.y),
        "numOfKills" -> Json.toJson(v.numOfKills),
        "numOfDeaths" -> Json.toJson(v.numOfDeaths),
        "id" -> Json.toJson(v.id))) })),

      "bullets" -> Json.toJson(board.Bullets.map({v => Json.toJson(Map(
        "x" -> Json.toJson(v.location.x),
        "y" -> Json.toJson(v.location.y),
        "v_x" -> Json.toJson(v.velocity.x),
        "v_y" -> Json.toJson(v.velocity.y),
        "id" -> Json.toJson(v.id))) })),
      )


    Json.stringify(Json.toJson(gameState))
  }

  def shoot(mouseX: Double, mouseY: Double, id: String): Unit = {
    var tank: Tank = null
    for(i <- board.Tanks) if(i.id == id) tank = i
    val mX = (mouseX - 7) / 30
    val mY = (mouseY - 7) / 30
    println(mX + " " + mY)
    println(tank.location.x + " " + tank.location.y)
    val velocity = new PhysicsVector(mX - tank.location.x, mY - tank.location.y)

    board.Bullets += new Bullet(id, new PhysicsVector(tank.location.x, tank.location.y, 3), velocity)
  }

  def blockTile(x: Int, y: Int, width: Int = 1, height: Int = 1): Unit = {
    val ul = new PhysicsVector(x, y)
    val ur = new PhysicsVector(x + width, y)
    val lr = new PhysicsVector(x + width, y + height)
    val ll = new PhysicsVector(x, y + height)

    board.boundaries ::= new Boundary(ul, ur)
    board.boundaries ::= new Boundary(ur, lr)
    board.boundaries ::= new Boundary(lr, ll)
    board.boundaries ::= new Boundary(ll, ul)
  }


}
