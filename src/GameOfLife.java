import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

public class GameOfLife {
	public static void main(String[] args){
	Life zycie = new Life();
	zycie.wczytajPlik();
	zycie.wyswietlTablice();
	zycie.ustalReguly();
	
	Scanner wejscie = new Scanner(System.in);
	System.out.println("Podaj ile iteracji");
	String decyzja="0";
	while(decyzja!="k"){
		decyzja = wejscie.nextLine();	
		zycie.zycieCzySmierc();
		zycie.wyswietlTablice();
	}
	wejscie.close();
//  we.close();
  }
}
/*
 * 1. zadeklarowaæ tablicê 0 1
 * 2. Stworzyæ dla niej ramkê 0
 * 3. Odczytaæ z pliku tablicê 01 do tablicy
 * 3. Wymyœleæ algorytm przesuwaj¹cy siê po 8 s¹siadach i zliczaj¹cy ile ¿ywych ile martwych
 * 4. Decyzja czy ¿yje i zapis do nowe tablicy
 */
class Life{
	int[][] komorki;
	private int dl_pion;
	private int dl_poziom;
	private int ile_nowa; //ile sasiadow do porodu
	private int dol_zyje; //ile dolne by zyla
	private int gora_zyje; //ile gorna by zyla
	public void ustalRozmiar(){
		System.out.println("Podaj szerokoœæ tablicy pozioma");
		Scanner wejscie = new Scanner(System.in);
		dl_poziom = wejscie.nextInt();
		dl_poziom+=2; // na ramki;
		System.out.println("Podaj szerokoœæ tablicy pozioma");
		dl_pion = wejscie.nextInt();
		dl_pion+=2; //na ramki;
	//	wejscie.close();		
	}
	public void ustalReguly(){
		System.out.println("Podaj ile s¹siadów trzeba by komórka o¿y³a (U Conawaya - 3");
		Scanner wejscie = new Scanner(System.in);
		ile_nowa = wejscie.nextInt();
		System.out.println("Podaj zakres komórek, które prze¿ywaj¹ (U Conwaya - 2 -3)");
		System.out.println("Dolna liczba:");
		dol_zyje = wejscie.nextInt();
		System.out.println("górna liczba liczba:");
		gora_zyje=wejscie.nextInt();
	//	wejscie.close();
	}
	
	public void wczytajPlik(){
		int wartosc = 0;
	    FileReader fr= null;

	    //OTWIERANIE PLIKU:
	    try {
	        fr = new FileReader("life.txt");
	     } catch (FileNotFoundException e) {
	           System.out.println("B£¥D PRZY OTWIERANIU PLIKU!");
	           System.exit(1);
	     }
	    int licznik=0, poziom=1, pion=1;
	     StreamTokenizer st = new StreamTokenizer(fr);
	     //ODCZYT KOLEJNYCH "TOKENÓW" Z PLIKU:
	     try {
	    	 
	        while( (wartosc = st.nextToken()) != StreamTokenizer.TT_EOF ){
	        	switch (licznik){
	        	case 0:
	        		dl_pion = (int)st.nval;
	        		licznik++;
	        		break;
	        	case 1:
	        		dl_poziom = (int)st.nval;
	        		komorki = new int[dl_pion+2][dl_poziom+2];
	        		licznik++;	        		
	        		break;
	        	default:	        		
	        		licznik++;
	        		if(poziom<=dl_poziom){
	        		  komorki[pion][poziom]=(int)st.nval;
	        //		   System.out.println(komorki[pion][poziom]);
	        		  poziom++;
	        		}else{
	        			poziom=1;
       				   pion++;
	        		   komorki[pion][poziom]=(int)st.nval;
	        		   poziom++;
	       // 		   System.out.println(komorki[pion][poziom]);
	        		}
	        		
	        		break;	        		
	        	}
	        	  
	              
	         }
	      } catch (IOException e) {
	            System.out.println("B£¥D ODCZYTU Z PLIKU!");
	            System.exit(2);
	      }
	     
	      //ZAMYKANIE PLIKU:
	      try {
	         fr.close();
	      } catch (IOException e) {
	           System.out.println("B£¥D PRZY ZAMYKANIU PLIKU!");
	           System.exit(3);
	      }
		
	}
	public void wyswietlTablice(){
		for(int i=0;i<dl_pion+2;i++){
			for(int j=0;j<dl_poziom+2;j++){
				if(komorki[i][j]==1){
				System.out.print("[]");
				}else{
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	//zwraca ilosc zywych komorek sasiednich
	public int zywe(int i, int j){
		int licznik=0;
		if(komorki[i-1][j-1]==1)
			licznik++;
		if(komorki[i-1][j]==1)
			licznik++;
		if(komorki[i-1][j+1]==1)
			licznik++;
		if(komorki[i][j+1]==1)
			licznik++;
		if(komorki[i+1][j+1]==1)
			licznik++;
		if(komorki[i+1][j]==1)
			licznik++;
		if(komorki[i+1][j-1]==1)
			licznik++;
		if(komorki[i][j-1]==1)
			licznik++;
		
		return licznik;
	}
	public void zycieCzySmierc(){
		int ile_zywych;
		int[][] komorki_nowe= new int [dl_pion+2][dl_poziom+2];
		for(int i=1;i<dl_pion+1;i++){
			for(int j=1;j<dl_poziom+1;j++){
				//czy martwa
				ile_zywych=zywe(i,j);
			
				if(komorki[i][j]==0){
					if(ile_zywych==ile_nowa)
					       komorki_nowe[i][j]=1;
				}else if ((ile_zywych<dol_zyje)||(ile_zywych>gora_zyje)){
					
					    komorki_nowe[i][j]=0;
				}else {
					komorki_nowe[i][j]=komorki[i][j];
				}
			}
		}
		//przepisanie tablic
		for(int i=0;i<dl_pion+2;i++)
			for(int j=0;j<dl_poziom+2;j++)
					komorki[i][j]=komorki_nowe[i][j];
		
		

	}
}