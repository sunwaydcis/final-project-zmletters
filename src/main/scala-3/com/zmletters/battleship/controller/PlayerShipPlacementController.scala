package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import javafx.fxml.FXML
import javafx.event.ActionEvent
import com.zmletters.battleship.model.*
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import scala.jdk.CollectionConverters._

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
      val btn = new Button("") {
        setMinWidth(50)
        setMinHeight(50)
        setOnAction(_ =>
          println(s"Clicked on $x $y") // https://stackoverflow.com/questions/57515339/javafx-how-to-locate-a-specific-button-in-a-gridpane reference for getting location of the button
          handleGridClick(x, y)
        )
      }
      placementGrid.add(btn, y, x) // GridPane uses column, row indexing
    }

  // https://stackoverflow.com/questions/57515339/javafx-how-to-locate-a-specific-button-in-a-gridpane reference for getting location of the button
  def getButtonAt(x: Int, y: Int): Button =
    val buttonOpt = placementGrid.getChildren
      .filtered(_.isInstanceOf[Button])
      .asScala  // Convert to Scala collection
      .map(_.asInstanceOf[Button])  // Now you can use map
      .find(btn => GridPane.getColumnIndex(btn) == y && GridPane.getRowIndex(btn) == x)

    buttonOpt.getOrElse(throw new NoSuchElementException("Button not found"))


  private def handleGridClick(x: Int, y: Int): Unit =
    if (currentShip.isDefined) {
      val ship = currentShip.get
      println(ship)
      val start = (x, y)

      ship.direction = currentDirection
      val positions = ship.calculatePositions(start)

      if (playerBoard.isPlacementValid(positions)) {
        playerBoard.placeShip(ship, start)

        positions.foreach { case (px, py) =>
          val btn = getButtonAt(px, py)
          println(s"$px $py")
          println(btn)
          btn.setStyle("-fx-background-color: green;")
          btn.setDisable(true)
        }
      }


    }

  def handleAddCarrier(action: ActionEvent) =
    currentShip = Some(new Carrier)
    println("Carrier selected.")

  def handleStartGame(action: ActionEvent) =
    //move to next scene startgame
    println("")