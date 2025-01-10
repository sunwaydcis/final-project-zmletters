package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import javafx.fxml.FXML
import javafx.event.ActionEvent
import com.zmletters.battleship.model.*
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import scala.jdk.CollectionConverters._
import javafx.scene.Node

@FXML
class PlayerShipPlacementController:

  var currentShip: Option[Ship] = None
  var currentDirection: String = "Right"
  val playerShips: List[Ship] = List(new Carrier, new Battleship, new Destroyer, new Submarine, new Boat)
  var currentShipIndex = 0
  var playerBoard: Board = new Board(10)

  @FXML var placementGrid: GridPane = null
  @FXML var carrierButton: Button = null

  var gridDisabled: Boolean = true

  def initialize(): Unit =
    setupGrid()
    if (playerShips.nonEmpty) {
      currentShip = Some(playerShips(currentShipIndex))
    }



  private def setupGrid(): Unit =
    for (x <- 0 until 10; y <- 0 until 10) {
      val btn: Button = new Button("")
      btn.setMinWidth(50)
      btn.setMinHeight(50)
      btn.setOnAction(_ => {
        println(s"Clicked on $x $y")
        handleGridClick(btn, x, y)
      })
      placementGrid.add(btn, y, x)
    }

  // Function to enable/disable grid clicking
  private def disableGridClicks(disable: Boolean): Unit =
    println("Grid clicking disabled: " + disable)
    placementGrid.getChildren.asScala.collect { case btn: Button => btn }
      .foreach { btn =>
        btn.setDisable(disable)
      }

  // https://stackoverflow.com/questions/57515339/javafx-how-to-locate-a-specific-button-in-a-gridpane reference for getting location of the button
  private def getButtonAt(row: Int, col: Int): Button =
    placementGrid.getChildren.asScala // Convert children to Scala collection
      .collectFirst {
        case btn: Button if GridPane.getRowIndex(btn) == row && GridPane.getColumnIndex(btn) == col => btn
      }
      .getOrElse(throw new NoSuchElementException(s"No button found at position ($row, $col)"))


  private def handleGridClick(btn: Button, x: Int, y: Int): Unit =
    if (currentShip.isDefined) {
      println("Ship is " + currentShip.isDefined.toString)
      val ship = currentShip.get
      println("Placing " + ship.name + "...")
      val start = (x, y)

      // Checking direction of ship
      ship.direction = currentDirection
      println("Ship direction: " + ship.direction)
      val positions = ship.calculatePositions(start)
      println("Positions: " + positions.toString())

      if (playerBoard.isPlacementValid(positions)) {
        playerBoard.placeShip(ship, start)
        btn.setStyle("-fx-background-color: green")
        positions.foreach { case (px, py) =>
          println(s"Getting button positions... at $px, $py")
          val btn1: Button = getButtonAt(px, py)
          if (btn1 != null) {
            println(s"Disable button at: $px, $py")
            btn1.setDisable(true)
            btn1.setStyle("-fx-background-color: green;")
          } else {
            println(s"Button not found at position $px, $py.")
          }
        }
      } else {
        println("Invalid ship placement.")
      }
    }
    // Reset current ship
    currentShip = None


  private def getButtonByRowCol(x: Int, y: Int): Button =
    placementGrid.getChildren.asScala
      .collectFirst { case btn: Button if (GridPane.getRowIndex(btn) == x && GridPane.getColumnIndex(btn) == y) => btn }
      .orNull

  def handleAddCarrier(action: ActionEvent) =
    //disableGridClicks(false)
    currentShip = Some(new Carrier)
    println("Carrier selected.")

  def handleAddBoat(action: ActionEvent) =
    currentShip = Some(new Boat)
    println("Boat Selected")

  def handleStartGame(action: ActionEvent) =
    //move to next scene startgame
    println("")