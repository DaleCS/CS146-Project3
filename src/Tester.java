import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;

public class Tester
{
	public static void main (String [] Args)
	{
		try
		{
			final String POEM = "poem.txt";
			final String DICTIONARY = "dictionary.txt";
			
			RedBlackTree<String> dictionaryRBT = new RedBlackTree<String>();
			
			BufferedReader dictionaryReader = new BufferedReader(new FileReader(DICTIONARY));
			
			String word = "";
			while ((word = dictionaryReader.readLine()) != null)
			{
				dictionaryRBT.insert(word);
			}
			
			dictionaryReader.close();
			
			BufferedReader poemReader = new BufferedReader(new FileReader(POEM));
			
			String poemLine = "";
			int count = 0;
			while ((poemLine = poemReader.readLine()) != null)
			{
				String[] allWords = poemLine.split(" ");
				
				for (int i = 0; i < allWords.length; i++)
				{
					String oneWord = allWords[i].replaceAll("[^a-zA-Z- ]", "").toLowerCase();
					if (dictionaryRBT.lookup(oneWord) == null)
					{
						System.out.println(oneWord);
						count++;
					}
				}
			}
			
			poemReader.close();
			
			System.out.println("\n# of mispelled or unidentified words: " + count);
		}
		catch(Exception fail)
		{
			System.out.println("Failed");
		}
	}
}
