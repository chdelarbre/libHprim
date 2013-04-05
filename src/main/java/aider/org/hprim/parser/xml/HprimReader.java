package aider.org.hprim.parser.xml;

import java.io.IOException;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import aider.org.hprim.parser.HPRIMSInputStreamReader;
import aider.org.hprim.parser.HPRIMSTokenSource;
import aider.org.hprim.parser.antlr.HPRIMSParser;

/**
 * @author delabre
 *
 */
public class HprimReader extends XMLFilterImpl {

	@Override
	public void parse(InputSource input) throws IOException, SAXException {

		// Définition des flux matériels (à fermer en fin d'utilisation)
		HPRIMSInputStreamReader inputreader = null;
		
		try {
			// Création de la source des tokens
			inputreader = new HPRIMSInputStreamReader(input.getCharacterStream());
			HPRIMSTokenSource toksce = new HPRIMSTokenSource(inputreader);
	
			// Création du flux de tokens
			TokenStream tokenstream = new CommonTokenStream (toksce);
	
			// Création du parser de tokens provenant de input et réalisant dans
			// la classe collecteur l'export des données
			HPRIMSParser parser = new HPRIMSParser(tokenstream, getContentHandler());
	
			// Parsing hprim oru
			parser.hprim();
		} catch (RecognitionException e) {
			throw new SAXException(e);
		} catch (IllegalArgumentException e) {
			throw new SAXException(e);
		} finally {
			// Fermeture propre des ressources allouées
			if (inputreader != null)
				inputreader.close();
		}
	}

}