package com.zmletters.battleship.controller

import com.zmletters.battleship.BattleshipGame
import com.zmletters.battleship.game.GameState
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

  private var currentShip: Option[Ship] = None
  private var currentDirection: String = "Right"
  private val playerShips: List[Ship] = List(new Carrier, new Battleship, new Destroyer, new Submarine, new Boat)
  private var currentShipIndex = 0
  private var playerBoard: Board = new Board(10)
  private val buttonGrid: Array[Array[Button]] = Array.ofDim[Button](10, 10)
  private var gridDisabled: Boolean = true
  @FXML private var placementRoot: AnchorPane = null
  @FXML private var placementGrid: GridPane = null
  @FXML private var carrierButton: Button = null
  @FXML private var submarineButton: Button = null
  @FXML private var destroyerButton: Button = null
  @FXML private var battleshipButton: Button = null
  @FXML private var boatButton: Button = null
  @FXML private var dialogText: Label = null
  @FXML private var rotationText: Label = null
  @FXML private var startGameButton: Button = null

  def initialize(): Unit =

    // disable game start button
    startGameButton.setDisable(true)

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
      if event.getCode.getName == "R" then
        if currentDirection == "Right" then
          rotationText.setText("Current Rotation: Vertical")
          currentDirection = "Down"
          dialogText.setText("Ship direction set to Vertical.")
          println("Ship direction set to Down.")
        else
          rotationText.setText("Current Rotation: Horizontal")
          currentDirection = "Right"
          dialogText.setText("Ship direction set to Horizontal.")
          println("Ship direction set to Right.")

    })

    // request focus : reference https://stackoverflow.com/questions/34654943/how-to-request-focus-on-dialog-ok-button-in-javafx
    placementRoot.requestFocus()

    // Dialog to welcome
    dialogText.setText("Welcome to the game! Please select the ships to place.")

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

        // Update button to reflect placement
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
        disableShipButton(ship.name)

        // if all ship has been place
        if (playerBoard.shipList.size == playerShips.size) {
          startGameButton.setDisable(false) // Enable the Start Game button
          dialogText.setText("All ships placed! Click 'Start Game' to begin.")
        } else {
          dialogText.setText(s"${ship.name} placed! Place the remaining ships.")
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

  private def disableShipButton(shipName: String): Unit =
    shipName match
      case "Carrier" => carrierButton.setDisable(true)
      case "Battleship" => battleshipButton.setDisable(true)
      case "Destroyer" => destroyerButton.setDisable(true)
      case "Submarine" => submarineButton.setDisable(true)
      case "Boat" => boatButton.setDisable(true)
      case _ => println(s"No button found for $shipName.")

  def handleAddCarrier(action: ActionEvent): Unit =
    if !isShipTypeAlreadyPlaced("Carrier") then
      currentShip = Some(new Carrier)
      println("Carrier selected.")
      dialogText.setText("Carrier selected. 5 Tiles.")
    else
      dialogText.setText("Carrier has already been placed.")

  def handleAddBattleship(action: ActionEvent): Unit =
    if !isShipTypeAlreadyPlaced("Battleship") then
      currentShip = Some(new Battleship)
      println("Battleship selected.")
      dialogText.setText("Battleship selected. 4 Tiles.")
    else
      dialogText.setText("Battleship has already been placed.")


  def handleAddDestroyer(action: ActionEvent): Unit =
    if !isShipTypeAlreadyPlaced("Destroyer") then
      currentShip = Some(new Destroyer)
      println("Destroyer selected.")
      dialogText.setText("Destroyer selected. 3 Tiles.")
    else
      dialogText.setText("Destroyer has already been placed.")



  def handleAddBoat(action: ActionEvent) =
    if !isShipTypeAlreadyPlaced("Boat") then
      currentShip = Some(new Boat)
      println("Boat selected.")
      dialogText.setText("Boat selected. 2 Tiles.")
    else
      dialogText.setText("Boat has already been placed.")


  def handleAddSubmarine(action: ActionEvent) =
    if !isShipTypeAlreadyPlaced("Submarine") then
      currentShip = Some(new Submarine)
      println("Submarine selected. 3 Tiles")
      dialogText.setText("Submarine selected.")
    else
      dialogText.setText("Submarine has already been placed.")

  def handleStartGame(action: ActionEvent): Unit =
    if playerBoard.shipList.size == playerShips.size then
      println("All ships placed. Starting the game...")
      println("List of placed ships and their positions:")
      playerBoard.shipList.foreach { ship =>
        val positions = ship.position
        println(s"${ship.name}: ${positions.mkString(", ")}")
      }

      GameState.playerBoard = playerBoard
      BattleshipGame.showGameplay()
    else
      println("Some ships are still unplaced.")
      dialogText.setText("Please place all ships before starting the game.")

  def handleBackButton(actionEvent: ActionEvent) =
    val backClicked = BattleshipGame.showDifficultySelection()