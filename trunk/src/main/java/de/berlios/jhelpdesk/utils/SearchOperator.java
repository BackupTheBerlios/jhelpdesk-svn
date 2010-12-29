/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
package de.berlios.jhelpdesk.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.berlios.jhelpdesk.model.Article;

/**
 * Klasa obsługująca wszystkie operacja na indeksie wyszukiwarki.
 *
 * @author jjhop
 */
@Component
public class SearchOperator {

    private final static int DEFAULT_SEARCH_RESULT_LIMIT = 25;

    private IndexWriter indexWriter;
    private IndexSearcher indexSearcher;
    private QueryParser parser;

    @Autowired
    public SearchOperator(String searchDirectory) {
        try {
            Directory directory = FSDirectory.open(new File(searchDirectory));
            indexWriter = new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_30),
                                          true, IndexWriter.MaxFieldLength.UNLIMITED);
            indexSearcher = new IndexSearcher(directory);
            parser = new QueryParser(Version.LUCENE_30, "body", new SimpleAnalyzer());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void search(String searchQuery) {
        try {
            Query query = parser.parse(searchQuery);
            TopDocs res = indexSearcher.search(query, DEFAULT_SEARCH_RESULT_LIMIT);
            // TODO: tutaj coś trzeba zwrócić
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addToIndex(Article article) {
        try {
            synchronized (indexWriter) {
                indexWriter.addDocument(articleToDocument(article));
                // TODO: jakaś optymalizacja indexu
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // TODO: dodatkowa metoda do uaktualniania indeksu

    private Document articleToDocument(Article article) {
        Document doc = new Document();
        doc.add(new Field("title", article.getTitle(),Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("lead", article.getLead(), Field.Store.NO, Field.Index.NOT_ANALYZED));
        doc.add(new Field("body", article.getBody(), Field.Store.NO, Field.Index.NOT_ANALYZED));
        return doc;
    }
}
