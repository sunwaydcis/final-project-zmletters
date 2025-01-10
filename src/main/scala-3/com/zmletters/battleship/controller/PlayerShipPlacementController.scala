package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import javafx.fxml.FXML
import javafx.event.ActionEvent
import com.zmletters.battleship.model.*
import javafx.scene.control.Button
import javafx.scene.layout.GridPane

import scala.jdk.CollectionConverters.*
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.input.KeyEvent
import javafx.scene.layout.AnchorPane

@FXML
class PlayerShipPlacementController:

  var currentShip: Option[Ship] = None
  var currentDirection: String = "Right"
  val playerShips: List[Ship] = List(new Carrier, new Battleship, new Destroyer, new Submarine, new Boat)
  var currentShipIndex = 0
  var playerBoard: Board = new Board(10)
  val buttonGrid: Array[Array[Button]] = Array.ofDim[Button](10, 10)

  @FXML var placementRoot: AnchorPane = null
  @FXML var placementGrid: GridPane = null
  @FXML var carrierButton: Button = null
  @FXML var submarineButton: Button = null
  @FXML var destroyerButton: Button = null
  @FXML var boatButton: Button = null
  @FXML var dialogText: Label = null

  var gridDisabled: Boolean = true

  def initialize(): Unit =

    // Setting up the grid 10 x 10
    for (x <- 0 until 10; y <- 0 until 10) {
      val btn: Button = new Button("")
      btn.setMinWidth(50)
      btn.setMinHeight(50)

      // Store the button in array for future use when clicked
      buttonGrid(x)(y) = btn

      // Button for action listener
      btn.setOnAction(_ => {
        println(s"Clicked on $x $y")
        handleGridClick(btn, x, y)
      })
      placementGrid.add(btn, y, x)
    }

    // Add key listener if user pressed "R"
    placementRoot.setOnKeyPressed(event => {
      if (event.getCode.getName == "R") {
        if (currentDirection == "Right") {
          currentDirection = "Down"
          dialogText.setText("Ship direction set to Vertical.")
          println("Ship direction set to Down.")
        } else {
          currentDirection = "Right"
          dialogText.setText("Ship direction set to Horizontal.")
          println("Ship direction set to Right.")
        }
      }
    })

    // request focus : reference https://stackoverflow.com/questions/34654943/how-to-request-focus-on-dialog-ok-button-in-javafx
    placementRoot.requestFocus()

    // Dialog to welcome
    dialogText.setText("Welcome to the game!")

  // https://stackoverflow.com/questions/57515339/javafx-how-to-locate-a-specific-button-in-a-gridpane reference for getting location of the button
  private def getButtonAt(row: Int, col: Int): Button = buttonGrid(row)(col)

  // Function to enable/disable grid clicking
  private def disableGridClicks(disable: Boolean): Unit =
    println("Grid clicking disabled: " + disable)
    placementGrid.getChildren.asScala.collect { case btn: Button => btn }
      .foreach { btn =>
        btn.setDisable(disable)
      }

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
        dialogText.setText("Invalid ship placement. Please reselect the ship.")
      }
    }
    // Reset current ship
    currentShip = None

  // function to check if ship already placed
  private def isShipTypeAlreadyPlaced(shipType: String): Boolean =
    playerBoard.shipList.exists(_.name == shipType)

  def handleAddCarrier(action: ActionEvent) =
    if (!isShipTypeAlreadyPlaced("Carrier")) {
      currentShip = Some(new Carrier)
      println("Carrier selected.")
      dialogText.setText("Carrier selected.")
//      carrierButton.setDisable(true) // Disable the button once selected
    } else {
      dialogText.setText("Carrier has already been placed.")
    }

  def handleAddDestroyer(action: ActionEvent) =
    if (!isShipTypeAlreadyPlaced("Battleship")) {
      currentShip = Some(new Destroyer)
      println("Battleship selected.")
      dialogText.setText("Battleship selected.")
    } else {
      dialogText.setText("Battleship has already been placed.")
    }


  def handleAddBoat(action: ActionEvent) =
    if (!isShipTypeAlreadyPlaced("Boat")) {
      currentShip = Some(new Boat)
      println("Boat selected.")
      dialogText.setText("Boat selected.")
    } else {
      dialogText.setText("Boat has already been placed.")
    }

  def handleAddSubmarine(action: ActionEvent) =
    if (!isShipTypeAlreadyPlaced("Submarine")) {
      currentShip = Some(new Submarine)
      println("Submarine selected.")
      dialogText.setText("Submarine selected.")
    } else {
      dialogText.setText("Submarine has already been placed.")
    }

  def handleStartGame(action: ActionEvent): Unit =
    if (playerBoard.shipList.nonEmpty) {
      println("List of placed ships and their positions:")
      playerBoard.shipList.foreach { ship =>
        val positions = ship.position // Assuming `positions` is a list of coordinates (e.g., List[(Int, Int)])
        println(s"${ship.name}: ${positions.mkString(", ")}")
      }
    } else {
      println("No ships have been placed yet.")
    }