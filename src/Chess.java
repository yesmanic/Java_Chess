import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * 
 * @author Yesman Choque Mamani
 *
 */
class Chess extends JFrame implements ActionListener {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		new Chess();
	}
	
	static class Square {
		int row, column;
		JButton button = new JButton();

		Square(int row, int column) {
			this.row = row;
			this.column = column;
		}

		void setOriginalColor() {
			if (this.column % 2 == 0)
				if (this.row % 2 == 0)
					this.button.setBackground(Color.WHITE);
				else
					this.button.setBackground(Color.LIGHT_GRAY);
			else if (this.row % 2 == 0)
				this.button.setBackground(Color.LIGHT_GRAY);
			else
				this.button.setBackground(Color.WHITE);
		}
	}

	Square[][] squares = new Square[8][8];
	static final Color VALID_MOVES_COLOR = Color.GREEN;
	static final Color INITIAL_SQUARE_COLOR = Color.YELLOW;

	// imagens das pecas pretas
	HashMap<String, Image> mapImgs = new HashMap<>();

	GridLayout layoutManager = new GridLayout(8, 8);

	static final String WINDOW_TITLE = "Java Chess";

	Chess() {
		mapImgs.put("Pawn-Black", new ImageIcon("../images/pieces/black/pawn.png", "").getImage().getScaledInstance(30,
				45, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Rook-Black", new ImageIcon("../images/pieces/black/rook.png", "").getImage().getScaledInstance(45,
				50, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Knight-Black", new ImageIcon("../images/pieces/black/knight.png", "").getImage()
				.getScaledInstance(40, 55, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Bishop-Black", new ImageIcon("../images/pieces/black/bishop.png", "").getImage().getScaledInstance(40,
				58, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Queen-Black", new ImageIcon("../images/pieces/black/queen.png", "").getImage()
				.getScaledInstance(35, 50, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("King-Black", new ImageIcon("../images/pieces/black/king.png", "").getImage().getScaledInstance(40, 60,
				java.awt.Image.SCALE_SMOOTH));

		mapImgs.put("Pawn-White", new ImageIcon("../images/pieces/white/pawn.png", "").getImage().getScaledInstance(30,
				45, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Rook-White", new ImageIcon("../images/pieces/white/rook.png", "").getImage()
				.getScaledInstance(45, 50, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Knight-White", new ImageIcon("../images/pieces/white/knight.png", "").getImage()
				.getScaledInstance(40, 55, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Bishop-White", new ImageIcon("../images/pieces/white/bishop.png", "").getImage()
				.getScaledInstance(40, 58, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("Queen-White", new ImageIcon("../images/pieces/white/queen.png", "").getImage()
				.getScaledInstance(35, 50, java.awt.Image.SCALE_SMOOTH));
		mapImgs.put("King-White", new ImageIcon("../images/pieces/white/king.png", "").getImage().getScaledInstance(40,
				60, java.awt.Image.SCALE_SMOOTH));
		super.setTitle(WINDOW_TITLE);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setChessboard();
		super.pack();
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] coordinate = e.getActionCommand().split(",");
		int row = Integer.parseInt(coordinate[0]);
		int column = Integer.parseInt(coordinate[1]);
		onClickSquare(row, column);
	}

	void setChessboard() {
		setNewSquare();
		JPanel chessboardPanel = new JPanel();
		chessboardPanel.setLayout(layoutManager);

		// add each square
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				chessboardPanel.add(getSquareButton(row, column));
				getSquareButton(row, column).addActionListener(this);
				getSquareButton(row, column).setActionCommand(row + "," + column);
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessboard[i][j] != null) {
					getSquareButton(i, j).setIcon(new ImageIcon(mapImgs.get(chessboard[i][j].type + "-" + chessboard[i][j].color)));
				}
			}
		}

		super.getContentPane().add(chessboardPanel);
	}

	void setOriginalColor() {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				squares[row][column].setOriginalColor();
			}
		}
	}

	void setNewSquare() {
		// add new squares
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				squares[row][column] = new Square(row, column);
			}
		}

		setOriginalColor();
	}

	JButton getSquareButton(int row, int column) {
		return squares[row][column].button;
	}

	void showMessage(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
	}

	int r, c;
	boolean isFirstClick = true;
	Movements.Piece[][] chessboard = Movements.initialChessboardSetup();
	boolean[][] moves;

	void onClickSquare(int row, int column) {
		if (isFirstClick) {
			r = row;
			c = column;

			if (chessboard[r][c] == null) {
				JOptionPane.showMessageDialog(null, "Select a piece");
			} else {
				isFirstClick = false;
				getSquareButton(row, column).setBackground(INITIAL_SQUARE_COLOR);

				if (chessboard[row][column].type == Movements.Type.Pawn) {
					moves = Movements.movsPawn(chessboard, row, column);
				} else if (chessboard[row][column].type == Movements.Type.Rook) {
					moves = Movements.movsRook(chessboard, row, column);
				} else if (chessboard[row][column].type == Movements.Type.Knight) {
					moves = Movements.movsKnight(chessboard, row, column);
				} else if (chessboard[row][column].type == Movements.Type.Bishop) {
					moves = Movements.movsBishop(chessboard, row, column);
				} else if (chessboard[row][column].type == Movements.Type.King) {
					moves = Movements.movsKing(chessboard, row, column);
				} else { // if (chessboard[row][column].type == Movements.Type.Queen) {
					moves = Movements.movsQueen(chessboard, row, column);
				}

				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (moves[i][j]) {
							getSquareButton(i, j).setBackground(VALID_MOVES_COLOR);
						}
					}
				}
			}
		} else {
			Movements.Color pieceColor;
			
			if(moves[row][column]) {
				getSquareButton(row, column).setIcon(getSquareButton(r, c).getIcon());
				getSquareButton(r, c).setIcon(null);
				chessboard[row][column] = chessboard[r][c];
				chessboard[r][c] = null;
				pieceColor = chessboard[row][column].color;
			} else {
				pieceColor = null;
				JOptionPane.showMessageDialog(null, "Invalid move");
			}
			
			setOriginalColor();
			isFirstClick = true;
			int kingRow = -1, kingColumn = -1;
			
			if(pieceColor != null) {
				for(int ii = 0; ii < 8; ii++) {
					for(int jj = 0; jj < 8; jj++) {
						if(chessboard[ii][jj] != null && chessboard[ii][jj].color != pieceColor && chessboard[ii][jj].type == Movements.Type.King) {
							kingRow = ii;
							kingColumn = jj;
							break;
						}
					}					
				}
				
				if(Movements.isInCheck(chessboard, kingRow, kingColumn)) {
					if(pieceColor == Movements.Color.Black)
						JOptionPane.showMessageDialog(null, "White king is in check");
					else
						JOptionPane.showMessageDialog(null, "Black king is in check");
				}
			}
		}
	}
}
