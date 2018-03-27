package exercici2;


//incrementes al fer request

public  class LamportMutex   {

	int timeIntern=0;
	int myId;
	int comptadorAck=0;
	int numAck=0;
	matriu matActu;
	int[] q; //cua de requests
	public LamportMutex(int id,matriu mat) {
		myId=id;
		matActu=mat;


		q = new int[3];
		for (int j = 0; j < 3; j++){
			q[j] = -1;
		}
	}
	public synchronized void requestCS() {
		timeIntern++;
		q[myId] =timeIntern;
		matActu.broadcastLam(myId+2,"rq"+myId+"t:"+timeIntern);


		while (!okayCS()){
			//System.out.println("dins");
			for(int i=2;i<5;i++){
				String frase=matActu.getString(i,myId+2);
				if(frase.equals("BUIT"));//System.out.println("dins");
				else processaMissatge(frase);
			}
		}//System.out.println("okay");

	}
	public synchronized void releaseCS() {
		q[myId] = -1;
		matActu.broadcastLam(myId+2,"rl"+myId+"t:"+timeIntern);

	}
	boolean okayCS() {
		if (numAck<3)return false;
		for (int j = 0; j < 3; j++){
			if (compara(q[myId], myId, q[j], j)) return false;


		}

		return true;
	}
	boolean compara(int num1, int pid1, int num2, int pid2) {
		if (num2 == -1) return false;
		return ((num1 > num2)
				|| ((num1 == num2) && (pid1 > pid2)));
	}

	public static int max(int a, int b) {
		if (a > b) return a;
		return b;
	}



	public synchronized void processaMissatge(String missatge) {


		//System.out.println("miss:"+missatge);
		int timeStamp= Integer.parseInt(missatge.substring(5,missatge.length()));
		//System.out.println("miss:"+timeStamp);
		int src = Integer.parseInt(missatge.substring(2,3));
		//System.out.println("miss:"+missatge+" s:"+src);
		timeIntern = max(timeIntern,timeStamp);

		//   System.out.println("missatge rebut"+missatge.substring(0,2));
		if (missatge.substring(0,2).equals("ac")) {
			// System.out.println("ack rebut");
			numAck++;
		}
		else if (missatge.substring(0,2).equals("rq")) {
			// System.out.println("rq rebut");
			q[src] = timeStamp;
			matActu.setString(myId+2, 2+src, "ac"+(myId+2)+"t:"+timeIntern);
		} else if (missatge.substring(0,2).equals("rl"))  {
			// System.out.println("rl rebut");

			q[src] = -1;
		}
	}

}
