let startBtn = document.getElementById("startBtn");
let loadBtn = document.getElementById("loadBtn");

import {chessBoard, gameResultWindow, initChessBoard} from "./initialize.js";
import {addChessBoardEvent, checkIsPlaying, player1, player2} from "./movement.js";
import ChessService from "./ChessService.js";

const apiService = new ChessService();

loadFirstPage();

function loadFirstPage() {
    chessBoard.style.display = "none";
    gameResultWindow.style.display = "none";
    player1.style.display = "none";
    player2.style.display = "none";
}

function startNewGame() {
    apiService.startGame(1)
        .then(data => {
            /*
            if (data.availability === "unavailable") {
                let forceStart = confirm("현재 진행 중인 체스 게임이 있습니다. 삭제 후 새 게임을 시작하시겠습니까?");
                if (forceStart) {
                    forceNewGame();
                }
            }
            */
            if (data.connection === "fail") {
                alert("서버와의 통신에 실패했습니다.");
            } else {
                initializeChessBoard(data);
            }
        })
        .catch(error => {
            alert("서버와의 통신에 실패했습니다.");
        })
}

/*
function forceNewGame() {
    fetch("/forceNewGame")
        .then(response => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        })
        .then(data => {
            initializeChessBoard(data);
        })
        .catch(error => {
            alert("서버와의 통신이 실패하였습니다.");
        })
}
*/
function loadPrevGame() {
    apiService.loadPrevGame(1)
        .then(data => {
            if (data.status === 500) {
                alert("저장된 게임이 없어요.")
                return;
            }
            initializeChessBoard(data);
            checkIsPlaying(data);
        })
        .catch(error => {
            console.log(error)
        })
}

function initializeChessBoard(data) {
    console.log(data);
    initChessBoard(data);
    addChessBoardEvent();
    startBtn.style.display = "none";
    loadBtn.style.display = "none";
    chessBoard.style.display = "flex";
    player1.style.display = "flex";
    player2.style.display = "flex";
}

startBtn.addEventListener("click", startNewGame);
loadBtn.addEventListener("click", loadPrevGame);