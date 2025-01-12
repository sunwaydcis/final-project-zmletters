//package com.zmletters.battleship
//
//import com.zmletters.battleship.game.GameLogic
//import com.zmletters.battleship.model.Board
//import scalafx.application.JFXApp3
//import scalafx.scene.Scene
//import scalafx.scene.control.Button
//import scalafx.scene.layout.{GridPane, VBox}
//
//object BattleshipGUI extends JFXApp3:
//
//  override def start(): Unit =
//    val playerBoard = new Board(10)
//    val aiBoard = new Board(10)
//
//    val controller = new GameLogic(playerBoard, aiBoard)
//
//    // Player Board UI
//    val playerGrid = new GridPane
//    for (x <- 0 until 10; y <- 0 until 10) {
//      val btn = new Button {
//        text = "-"
//        onAction = _ => {
//          // Handle player click for attacks or placements
//        }
//      }
//      playerGrid.add(btn, x, y)
//    }
//
//    // AI Board UI
//    val aiGrid = new GridPane
//    for (x <- 0 until 10; y <- 0 until 10) {
//      val btn = new Button {
//        text = "-"
//        onAction = _ => {
//          // Handle player attacks
//        }
//      }
//      aiGrid.add(btn, x, y)
//    }
//
//    stage = new JFXApp3.PrimaryStage {
//      title = "Battleship Game"
//      scene = new Scene {
//        root = new VBox {
//          children = Seq(playerGrid, aiGrid)
//        }
//      }
//    }
