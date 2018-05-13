import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;

public class Tester
{
	static final String POEM = "poem.txt";						// Name of .txt file containing poem
	static final String DICTIONARY = "dictionary.txt";			// Name of .txt file containing the dictionary
	
	/**
	 * Main method of Tester
	 * @param Args
	 */
	public static void main (String [] Args)
	{
		try
		{
			RedBlackTree<String> dictionaryRBT = new RedBlackTree<String>();
			
			long timeBeforeInserting = System.currentTimeMillis();
			dictionaryToRBT(dictionaryRBT);
			long timeAfterInserting = System.currentTimeMillis();
			
			long timeBeforeChecking = System.currentTimeMillis();
			int count = spellCheck(dictionaryRBT);
			long timeAfterChecking = System.currentTimeMillis();
			
			System.out.println("\nTime to insert words to RBT (in milliseconds): " + (timeAfterInserting - timeBeforeInserting));
			System.out.println("Time to spell check poem with RBT (in milliseconds): " + (timeAfterChecking - timeBeforeChecking));
			System.out.println("\n# of mispelled or unidentified words: " + count);
		}
		catch(Exception fail)
		{
			System.out.println("Failed");
		}
	}
	
	/**
	 * Reads the dictionary .txt file and inserts all words into the given RedBlackTree
	 * @param rbt RedBlackTree to be filled with words
	 */
	private static void dictionaryToRBT(RedBlackTree rbt)
	{
		try
		{
			BufferedReader dictionaryReader = new BufferedReader(new FileReader(DICTIONARY));
			
			String word = "";
			while ((word = dictionaryReader.readLine()) != null)
			{
				rbt.insert(word);
			}
			
			dictionaryReader.close();
		}
		catch(Exception fail)
		{
			System.out.println("ERROR: " + DICTIONARY + " FILE NOT FOUND");
		}
	}
	
	/**
	 * Uses given RedBlackTree to spell check the words of the poem .txt file and returns the number of mispelled or unidentified words
	 * @param rbt The RedBlackTree that contains all words of the dictionary
	 * @return Number of words that were mispelled
	 */
	private static int spellCheck(RedBlackTree rbt)
	{
		try
		{
			BufferedReader poemReader = new BufferedReader(new FileReader(POEM));
			
			String poemLine = "";
			int count = 0;
			
			System.out.println("Mispelled/Unidentified words: ");
			while ((poemLine = poemReader.readLine()) != null)
			{
				String[] allWords = poemLine.split(" ");
				
				for (int i = 0; i < allWords.length; i++)
				{
					String oneWord = allWords[i].replaceAll("[^a-zA-Z- ]", "").toLowerCase();
					if (rbt.lookup(oneWord) == null)
					{
						System.out.println("   " + oneWord);
						count++;
					}
				}
			}
			
			poemReader.close();
			return count;
		}
		catch(Exception fail)
		{
			System.out.println("ERROR: " + POEM + " FILE NOT FOUND");
			return -1;
		}
	}
}
