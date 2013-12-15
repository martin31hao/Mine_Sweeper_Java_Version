package mineSweeper;

public class CreateMine {
	
	private int row;
	private int col;
	private int mine[][];
	int dirX[] = new int[8];
	int dirY[] = new int[8];
	
	public CreateMine(int notBombRowNum, int notBombColNum){
		setRow(9);
		setCol(9);
		setMine(notBombRowNum, notBombColNum);
		getMine();
	}
	
	public void setRow(int inputRow){
		row = inputRow;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setCol(int inputCol){
		col = inputCol;
	}
	
	public int getNum(int rowNum, int colNum){
		return mine[rowNum][colNum];
	}
	
	// judge whether the point is valid in the grid
	public boolean isValid(int positionX, int positionY){
		if(positionX >= 0 && positionY >= 0 && positionX < row && positionY < col){
			return true;
		}
		return false;
	}
	
	public void initDir(){
		//direction of four directions
		dirX[0] = 1;
		dirX[1] = 0;
		dirX[2] = -1;
		dirX[3] = 0;
		dirX[4] = 1;
		dirX[5] = 1;
		dirX[6] = -1;
		dirX[7] = -1;
				
		dirY[0] = 0;
		dirY[1] = 1;
		dirY[2] = 0;
		dirY[3] = -1;
		dirY[4] = 1;
		dirY[5] = -1;
		dirY[6] = 1;
		dirY[7] = -1;
	}
	
	public void setMine(int notBombRowNum, int notBombColNum){
		
		mine = new int[row][col];
		
		initDir();
				
		// initiate the mine
		for(int i = 0; i < row; i ++){
			for(int j = 0; j < col; j ++){
				mine[i][j] = 0;
			}
		}
		
		//set the bomb
		for(int i = 0; i < 10; i ++){
			int bombRow = (int) (10 * Math.random()) % 9;
			int bombCol = (int) (10 * Math.random()) % 9;
			if(mine[bombRow][bombCol] == -1 || (bombRow == notBombRowNum && bombCol == notBombColNum)){
				i --;
			}
			else{
				mine[bombRow][bombCol] = -1;
			}
		}
		
		//set other grid
		for(int i = 0; i < row; i ++){
			for(int j = 0; j < col; j ++){
				if(mine[i][j] != -1){
					int bombNum = 0;
					for(int k = 0; k < 8; k ++){
						// neighbour points
						int px = dirX[k] + i;
						int py = dirY[k] + j;
						if(isValid(px, py)){
							if(mine[px][py] == -1){
								bombNum ++;
							}
						}
					mine[i][j] = bombNum; 
					}
				}
			}
		}
	}
	
	public void getMine(){
		for(int i = 0; i < row; i ++){
			for(int j = 0; j < col; j ++){
				System.out.print(mine[i][j]);
			}
			System.out.println();
		}
	}
	
}
