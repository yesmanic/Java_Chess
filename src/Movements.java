/**
 * 
 * @author Yesman Choque Mamani
 *
 */
public class Movements {

	enum Type {
		Pawn, Bishop, Knight, Rook, King, Queen;
	}

	enum Color {
		White, Black;
	}

	static class Piece {
		Type type;
		Color color;

		Piece() {
		}

		Piece(Type type_, Color color_) {
			type = type_;
			color = color_;
		}
	}

	static Piece[][] initialChessboardSetup() {
		Piece[][] board = new Piece[8][8];

		Type[] types = {Type.Rook, Type.Knight, Type.Bishop, Type.Queen, Type.King, Type.Bishop, Type.Knight, Type.Rook};

		for (int i = 0; i < types.length; i++) {
			board[0][i] = new Piece(types[i], Color.Black);
			board[1][i] = new Piece(Type.Pawn, Color.Black);

			board[6][i] = new Piece(Type.Pawn, Color.White);
			board[7][i] = new Piece(types[i], Color.White);
		}

		return board;
	}
	/**
	 * Verify if a position is inside of the chessboard
	 * @param row row of the piece
	 * @param column column of the piece
	 * 
	 * @return if the position is inside it returns true, otherwise false
	 */
	static boolean isInsideChessboard(int row, int column) {
		if (
			row >= 0 && 
			row < 8 && 
			column >= 0 && 
			column < 8
		) {
			return true;
		}

		return false;
	}

	/**
	 * Movement of the pawn
	 * 
	 * @param board 	represents the chessboard on which the pieces are
	 * @param row 		of the pawn
	 * @param column 	of the pawn
	 * @return 			return a 8x8 boolean matrix with the valid movements of the pawn. True for valid and false for invalid movements
	 */
	static boolean[][] movsPawn(Piece[][] board, int row, int column) {

		boolean[][] mov = new boolean[8][8];

		int i = board[row][column].color == Color.Black ? 1 : -1;
		int ini = board[row][column].color == Color.Black ? 1 : 6;

		if (row + i >= 0 && row + i < 8) {
			int[] dir = {-1, 1};

			// capture enemy piece
			for (int j : dir) {	
				if (column + j >= 0 && column + j < 8) {
					if (
						board[row + i][column + j] != null && 
						board[row + i][column + j].color != board[row][column].color
					) {
						mov[row + i][column + j] = true;
					}	
				}
			}
			
			if (board[row + i][column] != null) return mov;

			// move 2 squares straight forward
			if (row == ini) {
				if (board[row + i*2][column] == null) {
					mov[row + i*2][column] = true;
				}
			}

			mov[row + i][column] = true;
		}

		return mov;
	}

	static boolean[][] movsRook(Piece[][] board, int row, int column) {
		boolean[][] mov = new boolean[8][8];

		int[] dir = {-1, 0, 1, 0, -1};
		for (int k = 0; k + 1 < dir.length; k++) {
			for (
				int i = 1;
				isInsideChessboard((row + dir[k] * i), (column + dir[k + 1] * i)); 
				i++
			) {
				int dirR = row + dir[k] * i;
				int dirC = column + dir[k + 1] * i;

				if (board[dirR][dirC] != null) {			
					if (board[dirR][dirC].color != board[row][column].color) {
						mov[dirR][dirC] = true;
					}
					break;
				}
				mov[dirR][dirC] = true;
			}
		}
		
		return mov;
	}

	static boolean[][] movsBishop(Piece[][] board, int row, int column) {
		boolean[][] mov = new boolean[8][8];

		int[] dir = {-1, +1, +1, -1, -1};
		for (int k = 0; k + 1 < dir.length; k++) {
			for (
				int i = 1; 
				isInsideChessboard((row + dir[k] * i), (column + dir[k + 1] * i)); 
				i++
			) {
				int dirR = row + dir[k] * i;
				int dirC = column + dir[k + 1] * i;

				if (board[dirR][dirC] != null) {			
					if (board[dirR][dirC].color != board[row][column].color) {
						mov[dirR][dirC] = true;
					}
					break;
				}
				mov[dirR][dirC] = true;
			}
		}

		return mov;
	}

	static boolean[][] movsKnight(Piece[][] board, int row, int column) {
		boolean[][] mov = new boolean[8][8];

		int[] nums = {-2, -1, 1, 2};

		for (int i : nums) {
			for (int j : nums) {
				if (Math.abs(i) == Math.abs(j)) continue;
				if (isInsideChessboard(row + i, column + j)) {	
					if (
						board[row + i][column + j] != null && 
						board[row + i][column + j].color == board[row][column].color
					) continue;

					mov[row + i][column + j] = true;
				}
			}
		}

		return mov;
	}

	static boolean[][] movsKing(Piece[][] board, int row, int column) {
		boolean[][] mov = new boolean[8][8];

		int[] dir = {-1, 0, 1};
		for (int i : dir) {
			for (int j : dir) {

				if (i == 0 && j == 0) continue;

				if (isInsideChessboard(row + i, column + j)) {
					if (
						board[row + i][column + j] != null && 
						board[row + i][column + j].color == board[row][column].color
					) continue;

					mov[row + i][column + j] = true;
				}
			}
		}

		return mov;
	}

	static boolean[][] movsQueen(Piece[][] board, int row, int column) {
		boolean[][] mov = new boolean[8][8];

		int[] dir = {-1, 0, +1, 0, -1, +1, +1, -1, -1};
		for (int k = 0; k + 1 < dir.length; k++) {

			for (
				int i = 1; 
				isInsideChessboard((row + dir[k] * i), (column + dir[k + 1] * i));
				i++
			) {
				int dirR = row + dir[k] * i;
				int dirC = column + dir[k + 1] * i;

				if (board[dirR][dirC] != null) {			
					if (board[dirR][dirC].color != board[row][column].color) {
						mov[dirR][dirC] = true;
					}
					break;
				}
				mov[dirR][dirC] = true;
			}
		}

		return mov;
	}

	/**
	 * Verify if the king is in check.
	 * @param board 	represents the chessboard on which the pieces are.
	 * @param row 		row of king.
	 * @param column  column of king.
	 * @return If king is in check it returns true, otherwise false.
	 */
	static boolean isInCheck(Piece[][] board, int row, int column) {

		if (row < 0) return false;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != null && board[i][j].color != board[row][column].color) {
	
					boolean[][] movs = new boolean[8][8];
					
					if (board[i][j].type == Type.Rook) {
						movs = movsRook(board, i, j);
					}

					if (board[i][j].type == Type.Knight) {
						movs = movsKnight(board, i, j);
					}

					if (board[i][j].type == Type.Bishop) {
						movs = movsBishop(board, i, j);
					}

					if (board[i][j].type == Type.King) {
						movs = movsKing(board, i, j);
					}

					if (board[i][j].type == Type.Queen) {
						movs = movsQueen(board, i, j);
					}

					if (board[i][j].type == Type.Pawn) {
						movs = movsPawn(board, i, j);
					}

					if (movs[row][column]) {
						return true;
					}
				}
			}
		}

		return false;
	}
}