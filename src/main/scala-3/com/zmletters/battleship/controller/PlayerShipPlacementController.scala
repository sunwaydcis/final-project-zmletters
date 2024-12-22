package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import javafx.fxml.FXML
import javafx.event.ActionEvent
import com.zmletters.battleship.model.*
import javafx.scene.control.Button
import javafx.scene.layout.GridPane

@FXML
class PlayerShipPlacementController:

  var currentShip: Option[Ship] = None
  var currentDirection: String = "Right"
  val playerShips: List[Ship] = List(new Carrier, new Battleship, new Destroyer, new Submarine, new Boat)
  var currentShipIndex = 0
  var playerBoard: Board = new Board(10)

  @FXML var placementGrid: GridPane = null
  @FXML var carrierButton: Button = null

  def initialize(): Unit =
    setupGrid()
    if (playerShips.nonEmpty) {
      currentShip = Some(playerShips(currentShipIndex))
    }

  private def setupGrid(): Unit =
    for (x <- 0 until 10; y <- 0 until 10) {
      var btn = new Button("") {
        setMinWidth(50)
        setMinHeight(50)
        setOnAction(_ => println(s"Clicked on $x $y"))
      }
      placementGrid.add(btn, y, x) // GridPane uses column, row indexing
    }

  // this function comes from chatgpt. because i dont know how to do it.
  // function to get button position for ship position
//  def getButtonAt(x: Int, y: Int): Button =
//    placementGrid.getChildren


  def handleAddCarrier(action: ActionEvent) =
    println("")

  def handleStartGame(action: ActionEvent) =
    //move to next scene startgame
    println("")