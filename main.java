package exercici2;

import java.util.LinkedList;

public class main {


	static matriu mat;
	private static Thread lightweightA(int id){
		return new Thread()
		{

			LamportMutex lamp;
			public void run()
			{
				while(1==1){
					if(mat.getString(0, id+2).equals("1")){
						lamp =new LamportMutex(id,mat);
						lamp.requestCS();
						for(int i=0;i<10;i++){
							System.out.println("Sóc el proces lightweight A"+(id+1));
						}
						System.out.println("");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lamp.releaseCS();
						//notificaPare
						
						mat.setString(id+2, 0, "FI");
						;
					}
				}
			}
		};

	}
	private static Thread lightweightB(int id){

		return new Thread()
		{
			ricardMutex ric ;
			@Override
			public void run()
			{
				while(1==1){
					if(mat.getString(1, id+5).equals("1")){
						ric=new ricardMutex(id,mat);
						ric.requestCS();
						for(int i=0;i<10;i++){
							System.out.println("Sóc el proces lightweight B"+(id+1));
						}System.out.println("");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ric.releaseCS();
						//notificaPare
						
						mat.setString(id+5, 1, "FI");
					}
				}



			}
		};

	}
	private static Thread ProcessA()
	{

		return new Thread()
		{

			int numFi=0;
			@Override
			public void run()
			{
				while(true){
					while(!(mat.getString(1,0).equals("1")));
					mat.neteja();
					
					//while(!(matrix[1][0].equals("1")));
					mat.setString(0, 2, "1");
					mat.setString(0, 3, "1");
					mat.setString(0, 4, "1");


					//consultar a1,a2,a3
					while(numFi<3){
						if(mat.getString(2,0).equals("FI")) numFi++;
						if(mat.getString(3,0).equals("FI") )numFi++;
						if(mat.getString(4,0).equals("FI")) numFi++;
					}	
					numFi=0;
					mat.setString(0, 1, "1");


				}
			}
		};
	}
	private static Thread ProcessB()
	{
		return new Thread()
		{
			int numFi=0;
			@Override

			public void run()
			{
				while(true){
					while(!(mat.getString(0,1).equals("1")));
					//while(!(matrix[1][0].equals("1")));
					mat.setString(1, 5, "1");
					mat.setString(1, 6, "1");



					//consultar a1,a2,a3
					while(numFi<2){
						if(mat.getString(5,1).equals("FI")) numFi++;
						if(mat.getString(6,1).equals("FI") )numFi++;

					}	
					numFi=0;
					mat.setString(1, 0, "1");
					//System.out.println("fora");

				}
			}
		};
	}

	public static void main(String[] args)
	{
		//matrix[1][0]="1";
		//matrix[0][1]="0";
		mat=new matriu();
		mat.setString(1, 0,"1");
		mat.setString(0, 1,"0");
		Thread t = ProcessA();
		Thread t2 =  ProcessB();
		t.start();
		t2.start();
		System.out.println("Generan fills A");
		Thread la1 = lightweightA(0);
		Thread la2 = lightweightA(1) ;
		Thread la3 = lightweightA(2) ;
		la1.start();
		la2.start();
		la3.start();
		System.out.println("Generan fills B");
		Thread lb1 = lightweightB(0);
		Thread lb2 = lightweightB(1) ;

		lb1.start();
		lb2.start();
	}

}
