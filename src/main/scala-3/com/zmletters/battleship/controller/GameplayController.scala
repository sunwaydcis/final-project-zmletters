package com.zmletters.battleship.controller

import com.zmletters.battleship.game.*
import com.zmletters.battleship.model.*
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}
import javafx.scene.layout.GridPane

import scala.util.Random

@FXML
class GameplayController:

  @FXML private var enemyGrid: GridPane = null
  @FXML private var playerGrid: GridPane = null

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
    //btn.setText("Hit or Miss")

    val result = gameLogic.playerAttack(x, y)
    btn.setDisable(true)

    // Update button based on result
    result match {
      case "Hit" =>
        btn.setStyle("-fx-background-color: red;")
        btn.setText("Hit")
      case "Miss" =>
        btn.setStyle("-fx-background-color: gray;")
        btn.setText("Miss")
      case "Hit and Sunk" =>
        btn.setStyle("-fx-background-color: red;")
        btn.setText("Hit and Sunk")
      case "Player 1 wins!" =>
        btn.setStyle("-fx-background-color: red;")
        btn.setText("Hit")
        //statusLabel.setText("Game Over! Player 1 wins!")
      case _ =>
        println(s"Unexpected result: $result")
        //statusLabel.setText("Error occurred during attack.")
    }


    // If game is not over, proceed to AI's turn
    if (!gameLogic.isGameOver) {
      handleAiTurn()
    }

  private def handleAiTurn(): Unit =
    val (x, y, result) = gameLogic.aiAttack
    println(s"AI attacked ($x, $y): $result")

    // Update the player grid based on AI's attack
    val index = x * 10 + y
    val cell = playerGridCells(index)

    result match {
      case "Hit" =>
        cell.setStyle("-fx-background-color: red;")
        cell.setText("Hit")
      case "Miss" =>
        cell.setStyle("-fx-background-color: gray;")
        cell.setText("Miss")
      case "Hit and Sunk" =>
        cell.setStyle("-fx-background-color: red;")
        cell.setText("Hit and Sunk")
      case "Player 2 wins!" =>
        cell.setStyle("-fx-background-color: red;")
        cell.setText("Hit")
        //statusLabel.setText("Game Over! AI wins!")
      case _ =>
        println(s"Unexpected result: $result")
        //statusLabel.setText("Error occurred during AI attack.")
    }