import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class RBTDictionaryTest {

	@Test
	void test()
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
			while ((poemLine = poemReader.readLine()) != null)
			{
				String[] allWords = poemLine.split(" ");
				
				String spelledWord = "";
				Node<String> node = dictionaryRBT.lookup(poemWord);
				if (node != null)
				{
					spelledWord = node.key;
				}
				else
				{
					Assert.fail();
				}
				
				assertEquals(spelledWord, poemWord);
			}
		}
		catch(Exception fail)
		{
			Assert.fail();
		}
	}

}
