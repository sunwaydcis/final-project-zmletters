package com.zmletters.battleship.controller

import com.zmletters.battleship.game.*
import com.zmletters.battleship.model.*
import com.zmletters.battleship.Battleship
import javafx.animation.PauseTransition
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}
import javafx.scene.layout.{GridPane, Pane}
import javafx.event.ActionEvent
import javafx.util.Duration

import scala.util.Random

@FXML
class GameplayController:

  @FXML private var enemyGrid: GridPane = null
  @FXML private var playerGrid: GridPane = null
  @FXML private var enemyDialog: Label = null
  @FXML private var playerDialog: Label = null
  @FXML private var blockPane: Pane = null

  private var gameLogic: GameLogic = null
  private var playerBoard: Board = null
  private var enemyBoard: Board = Board(10)
  private val playerGridCells: Array[Label] = Array.ofDim[Label](10 * 10)
  private val buttonGrid: Array[Array[Button]] = Array.ofDim[Button](10, 10)
  private val enemyShips: List[Ship] = List(new Carrier, new Battleship, new Destroyer, new Submarine, new Boat)

  def initialize(): Unit =

    // Get the game state
    playerBoard = GameState.playerBoard
    gameLogic = new GameLogic(playerBoard, enemyBoard, GameState.difficulty)

    setUpPlayerGrid()

    // Initialize enemy board
    generateEnemyShips()
    setUpEnemyGrid()
    println(enemyBoard.shipList)


  private def setUpPlayerGrid(): Unit =
    for (x <- 0 until 10; y <- 0 until 10) {
      val cell = new Label("")
      cell.setMinWidth(25)
      cell.setMinHeight(25)
      cell.setStyle("-fx-border-color: black; -fx-background-color: lightblue;")
      playerGrid.add(cell, y, x)
      playerGridCells(x * 10 + y) = cell
    }

    // Color all ship positions green
    playerBoard.shipList.foreach { ship =>
      ship.position.foreach { case (row, col) =>
        val index = row * 10 + col
        val cell = playerGridCells(index)
        cell.setStyle("-fx-background-color: green; -fx-border-color: black;")
      }
    }

  private def setUpEnemyGrid(): Unit =
    for (x <- 0 until 10; y <- 0 until 10) {
      val btn = new Button("")
      btn.setMinWidth(50)
      btn.setMinHeight(50)

      // Store the button in array for future use when clicked
      buttonGrid(x)(y) = btn

      btn.setOnAction(_ => {
        println(s"Clicked on $x, $y")
        handleGridClick(btn, x, y)
      })
      enemyGrid.add(btn, y, x)
    }

  private def generateEnemyShips(): Unit =
    val random = new Random
    enemyShips.foreach { ship =>
      var placed = false

      while !placed do
        // Randomly select a starting position and direction
        val startX = random.nextInt(10)
        val startY = random.nextInt(10)
        val direction = if random.nextBoolean() then "Right" else "Down"
        ship.direction = direction

        // Calculate positions and check validity
        val positions = ship.calculatePositions((startX, startY))
        if enemyBoard.isPlacementValid(positions) then
          enemyBoard.placeShip(ship, (startX, startY))
          placed = true
    }

  private def handleGridClick(btn: Button, x: Int, y: Int): Unit =
    println(s"Player attacked enemy at $x, $y")

    // make pane block clicking during ai turn
    blockPane.setDisable(false)
    blockPane.setVisible(true)

    val result = gameLogic.playerAttack(x, y)
    btn.setDisable(true)

    // Update button based on result
    result match
      case "Hit" =>
        btn.setStyle("-fx-background-color: red;")
        btn.setText("Hit")
        playerDialog.setText("That's a Hit!")
      case "Miss" =>
        btn.setStyle("-fx-background-color: gray;")
        btn.setText("Miss")
        playerDialog.setText("That's a Missed!")
      case "Hit and Sunk" =>
        btn.setStyle("-fx-background-color: red;")
        btn.setText("Hit")
        playerDialog.setText("Ship SUNK! Let's GO!")
      case "Player wins!" =>
        btn.setStyle("-fx-background-color: red;")
        btn.setText("Hit")
        playerDialog.setText("Well done Captain! You won!")
        GameState.gameOverText = "You win!"
        transitionNextScene()
        //statusLabel.setText("Game Over! Player 1 wins!")
      case _ =>
        println(s"Unexpected result: $result")
        //statusLabel.setText("Error occurred during attack.")


    // If game is not over, proceed to AI's turn
    if (!gameLogic.isGameOver) {
      handleAiTurn()
    }

  private def handleAiTurn(): Unit =
    // Pause for 1 second
    val pause = new PauseTransition(Duration.seconds(1))

    pause.setOnFinished(_=> {
      val (x, y, result) = gameLogic.aiAttack
      println(s"AI attacked ($x, $y): $result")

      // Update the player grid based on AI's attack
      val index = x * 10 + y
      val cell = playerGridCells(index)

      result match
        case "Hit" =>
          cell.setStyle("-fx-background-color: red;")
          cell.setText("Hit")
          enemyDialog.setText("Heh heh.")
          playerDialog.setText("Oh no!")
        case "Miss" =>
          cell.setStyle("-fx-background-color: gray;")
          cell.setText("Miss")
          enemyDialog.setText("You won't get away with this!")
        case "Hit and Sunk" =>
          cell.setStyle("-fx-background-color: red;")
          cell.setText("Hit")
          enemyDialog.setText("I am winning!!!")
          playerDialog.setText("Brace yourself captain!")
        case "Enemy wins!" =>
          cell.setStyle("-fx-background-color: red;")
          cell.setText("Hit")
          GameState.gameOverText = "You lost!"
          enemyDialog.setText("I guess I am the superior ones after all.")
          playerDialog.setText("You lost... Better luck next time...")
          transitionNextScene()
        //statusLabel.setText("Game Over! AI wins!")
        case _ =>
          println(s"Unexpected result: $result")
      //statusLabel.setText("Error occurred during AI attack.")

      // Enable interactions after AI finishes turn
      if (!gameLogic.isGameOver) {
        blockPane.setDisable(true)
        blockPane.setVisible(false)
      }

    })

    pause.play()

  private def transitionNextScene(): Unit =
    println("Transitioning ot next scene.")

    // Disable all grid buttons
    disableAllGridButtons()

    val pause = new PauseTransition(Duration.seconds(2))
    pause.setOnFinished(_ => {
      // Call your scene transition method here
      Battleship.showGameOver()
    })
    pause.play()

  private def disableAllGridButtons(): Unit =
    // Disable all buttons in the enemy grid
    buttonGrid.flatten.foreach { btn =>
      if (btn != null) btn.setDisable(true)
    }

    // Disable player grid cells (if required)
    playerGridCells.foreach { cell =>
      if (cell != null) cell.setDisable(true) // Assuming Label supports disabling
    }

  def handleBackButton(actionEvent: ActionEvent): Unit =
    val backClicked = Battleship.showDifficultySelection()