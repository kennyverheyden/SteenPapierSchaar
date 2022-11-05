package SteenPapierSchaar;

import java.util.Random;
import java.util.Scanner;

public class SteenPapierSchaar {

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args)
	{
		String[] keuzes = {"STEEN ","SCHAAR","PAPIER"}; // Benamingen, Let op: spatie achter steen voor uitlijning woordbreedtes 6 characters
		int scoreSpeler=0, scoreComputer=0, scoreGelijkspel=0, aantalRondes=0, maxScore=3;

		char spelStand; //Houdt bij per ronde: '0' voor gelijkspel, 'g' voor gewonnen, 'v' voor verloren
		boolean wilSpelen = true; //Speler blijft een sessie van rondes spelen
		Welkom();

		do //Spelpgrogramma, zolang speler wil spelen. (boolean wilspelen = true)
		{
			do //Spelsessie van rondes, zolang scoreSpeler en scoreComputer kleiner is dan maxScore
			{
				int spelerKeuze=SpelerInput(keuzes, aantalRondes); //Geef keuzes weer en speler input
				int computerKeuze=ComputerKiest(keuzes); //Computer genereert een keuze
				spelStand=SpelSpelen(spelerKeuze,computerKeuze,keuzes);//Resultaat van ronde, return char '0' voor gelijkspel, 'g' voor gewonnen, 'v' voor verloren

				switch(spelStand) { //Verhoog de score
				case 'g': scoreSpeler++;
				break;
				case 'v': scoreComputer++;
				break;
				case '0' : scoreGelijkspel++;
				}
				aantalRondes++;

				System.out.println("\n  RONDE "+ aantalRondes + " | Score speler: "+scoreSpeler+" | Score computer: "+scoreComputer+" | Gelijkspel: "+ scoreGelijkspel+" |\n");
			}
			while(scoreSpeler< maxScore && scoreComputer<maxScore); //Spelsessie zolang speler of computer onder maxScore blijft
			// Einde spelsessie van rondes

			if(scoreSpeler==maxScore) //Wie heeft maxScore bereikt
			{
				System.out.println("  *************************************************************************");
				System.out.println("  *****                           JOEPIE!!                            *****");
				System.out.println("  *****                 Je hebt de wedstijd gewonnen!                 *****");
				System.out.println("  *************************************************************************");
			}
			else
			{
				System.out.println("  *************************************************************************");
				System.out.println("  *****                            SPIJTIG                            *****");
				System.out.println("  *****                 Je hebt de wedstijd verloren!                 *****");
				System.out.println("  *************************************************************************");
			}

			// Vragen of speler nog een sessie van rondes wilt spelen
			System.out.println("\n  "+ "Wil je nog een sessie spelen? Voer keuze in [j] = ja | [n] = nee");
			String antwoord = input.next().toLowerCase();
			wilSpelen=antwoord.equals("j");
			scoreSpeler = scoreComputer =scoreGelijkspel= aantalRondes=0; // Zet spel stats op nul
		}
		while(wilSpelen); 
		//Einde spel programma

		System.out.println("\n  Fijn dat je gespeeld hebt, tot ziens");
		input.close();
	}

	public static void Welkom()
	{
		System.out.println("\n\n  *************************************************************************");
		System.out.println("  *****                  Steen, schaar, papier spel                   *****");
		System.out.println("  *****   Speel tegen de computer en bereik als eerste drie punten!   *****");
		System.out.println("  *************************************************************************");
	}

	// Kkeuze van de speler vragen en uitschrijven
	public static Integer SpelerInput(String[] Keuzes, int rondes)
	{
		//lees de lijst met keuzes om weer te geven

		Integer spelerKeuze=0;
		boolean geenGeldigInput= true;

		while(geenGeldigInput) //lus blijft actief tot wanneer speler geldige invoer geeft
		{	
			try
			{
				if(rondes<1) { // Geef boodschap alleen maar voor de 1ste ronde weer
				System.out.println("\n  Bevestig je keuze met een getal en druk op [ENTER]\n");
				}
				for(int i=0;i<Keuzes.length;i++)
				{
					System.out.print("  ["+ (i+1) +"] voor "+Keuzes[i] +" ");
				}
				System.out.println("\n  Mijn keuze: ");		
				spelerKeuze = input.nextInt();	
				//controleer invoer
				if(spelerKeuze > 0 && spelerKeuze < 4)
				{
					spelerKeuze--; //speler invoer gelijk zetten met arrayindex die telt vanaf nul
					printLijn();
					legeLijn();
					System.out.println("       |                 Jij "+Keuzes[spelerKeuze] +"               |");
					geenGeldigInput=false; 
				}
				else
				{
					FoutMelding();
				}
			}
			catch(Exception e)
			{
				FoutMelding();
				input.nextLine();
			}
		}
		return spelerKeuze;
	}

	//De computer kiest een nummer en schrijft zijn keuze neer
	public static Integer ComputerKiest(String[] keuzes) 
	{
		Random rn = new Random();
		int computerKeuze=rn.nextInt(keuzes.length);
		System.out.println("       |            Computer "+ keuzes[computerKeuze] +"               |"); // Geeft computerkeuze weer op scherm
		legeLijn();
		return computerKeuze;
	}

	//Keuzes worden vergeleken en de winner wordt bekend gemaakt
	public static char SpelSpelen(int spelerKeuze,int computerKeuze,String keuzelijst[])
	{
		char spelStand='0'; //'g' = gewonnen, 'v' = verloren, '0' = gelijk
		if(spelerKeuze==computerKeuze)
		{

			System.out.println("       |                 Gelijkspel!              |");
			legeLijn();
			printLijn();
			spelStand='0';
		}
		else
		{
			if(keuzelijst[spelerKeuze].equals(keuzelijst[0])) //speler heeft steen
			{
				if(keuzelijst[computerKeuze].equals(keuzelijst[1])) //computer heeft schaar
				{
					SchrijfGewonnen();
					spelStand='g';
				}
				else
				{
					SchrijfVerloren();
					spelStand='v';
				}
			}
			if(keuzelijst[spelerKeuze].equals(keuzelijst[1])) //speler heeft schaar
			{
				if(keuzelijst[computerKeuze].equals(keuzelijst[2])) //computer heeft papier
				{
					SchrijfGewonnen();
					spelStand='g';
				}
				else
				{
					SchrijfVerloren();
					spelStand='v';
				}	
			}
			if(keuzelijst[spelerKeuze].equals(keuzelijst[2])) // speler heeft papier
			{
				if(keuzelijst[computerKeuze].equals(keuzelijst[0])) // computer heeft steen
				{
					SchrijfGewonnen();
					spelStand='g';
				}
				else
				{
					SchrijfVerloren();
					spelStand='v';
				}
			}
		}
		return spelStand;
	}

	public static void FoutMelding()
	{
		System.out.println("  Gelieve een getal tussen 1 en 3 in te voeren.");
	}

	public static void SchrijfGewonnen()
	{
		System.out.println("       |                  :-) YES!                |");
		legeLijn();
		printLijn();
	}

	public static void SchrijfVerloren()
	{
		System.out.println("       |                 :-( Jammer               |");
		legeLijn();
		printLijn();
	}

	public static void printLijn()
	{
		System.out.println("       --------------------------------------------");
	}
	public static void legeLijn()
	{
		System.out.println("       |                                          |");
	}
}
