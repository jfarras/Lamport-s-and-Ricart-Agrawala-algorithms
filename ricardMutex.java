package exercici2;


import java.util.LinkedList;


public class ricardMutex  {
	int myts;
	int timeIntern=0;
	matriu mat;
	int id;

	//int[] q  = new int[2];

	LinkedList<Integer> q = new LinkedList<Integer>();
	int numOkay = 0;

	public ricardMutex(int idInit,matriu matActu) {
		id=idInit;
		mat=matActu;
		myts = -1;

	}


	public synchronized void requestCS() {

		timeIntern++;
		myts = timeIntern;
		mat.broadcastRic(id+5,"rq"+(id+5)+"t:"+myts);
		numOkay = 0;
		while (numOkay < 1){
			for(int i=5;i<7;i++){
				String frase=mat.getString(i,id+5);
				if(frase.equals("BUIT"));//System.out.println("dins");
				else{
					//	System.out.println("Soc "+(id+5)+" i he rebut un missatge de "+frase);
					processaMissatge(frase);
				}
			}
		} 
	}
	public synchronized void releaseCS() {
		myts = -1;
		while (!q.isEmpty()) {


			int pid= q.getFirst();
			q.removeFirst();
			//     System.out.println("buidan"+ pid +id);
			mat.setString(id+5, pid,"ok"+(pid)+"t:"+timeIntern);
			//  mat.setString(src, id+5,"ok"+(src)+"t:"+timeIntern);
		}
		// System.out.println("dew");
	}
	public static int max(int a, int b) {
		if (a > b) return a;
		return b;
	}


	public synchronized void processaMissatge(String missatge) {
		//System.out.println("miss:"+missatge);

		int timeStamp= Integer.parseInt(missatge.substring(5,missatge.length()));
		int src = Integer.parseInt(missatge.substring(2,3));
		timeIntern = max(timeIntern,timeStamp);
		//System.out.println("miss:"+missatge+" s:"+src +"i:"+(id+5));


		if (missatge.substring(0,2).equals("rq")) {
			//System.out.println("dinsrq");
			if ((myts == -1) || (timeStamp < myts)|| ((timeStamp == myts) && (src < id+5))){
				mat.setString(id+5,src,"ok"+(src)+"t:"+timeIntern);
				//   System.out.println("ok");
			}
			else {
				//     	System.out.println("encuat:"+(id+5));
				q.add(src);
			}
		} else if (missatge.substring(0,2).equals("ok")) {
			// 	System.out.println("dinsOk"+id);
			numOkay++;

		}
	}


}