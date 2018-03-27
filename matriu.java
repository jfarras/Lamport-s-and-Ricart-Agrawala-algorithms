package exercici2;

import java.util.LinkedList;

//tema broadcast
public class matriu {
	static LinkedList<String>[][] matrix;
	//cal new per cada
	public matriu(){
		matrix = new LinkedList[8][8];

		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				matrix[i][j] =new LinkedList<String>();
				//matrix[i][j].add("-1");
			}
		}

	}
	public synchronized void broadcastLam(int emisor,String trama){

		for(int j=2;j<5;j++){
			matrix[emisor][j].add(trama);
		}

	}

	public synchronized void broadcastRic(int emisor, String trama){
		for(int j=5;j<7;j++){
			matrix[emisor][j].add(trama);
		}

	}
	public synchronized String getString(int i,int j){
		if(!(matrix[i][j].isEmpty())){
			String aux= matrix[i][j].pop();


			return aux;
		}
		//matrix[i][j].removeFirst();
		else  return "BUIT";
	}
	public synchronized void setString(int i,int j,String trama){

		matrix[i][j].add(trama);


	}
	public synchronized void neteja(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				matrix[i][j].clear();
			}
		}
	
	}

}
