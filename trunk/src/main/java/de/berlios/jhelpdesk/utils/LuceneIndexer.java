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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.berlios.jhelpdesk.model.Article;

/**
 * Klasa obsługująca wszystkie operacja na indeksie wyszukiwarki.
 *
 * @author jjhop
 */
@Component
public class LuceneIndexer {

    private final static int DEFAULT_SEARCH_RESULT_LIMIT = 25;

    private @Value("${lucene.dir}") String indexDirectory;
    private QueryParser parser = new QueryParser(Version.LUCENE_30, "body", new SimpleAnalyzer());

    public List<Article> search(String searchQuery) {
        try {
            Query query = parser.parse(searchQuery);
            Directory directory = FSDirectory.open(new File(indexDirectory));
            IndexSearcher indexSearcher = new IndexSearcher(directory);
            TopDocs res = indexSearcher.search(query, DEFAULT_SEARCH_RESULT_LIMIT);
            List<Article> result = new ArrayList<Article>();
            for (ScoreDoc scoreDoc : res.scoreDocs) {
                Document document = indexSearcher.doc(scoreDoc.doc);
                result.add(documentToArticle(document));
            }
            indexSearcher.close();
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public synchronized void addToIndex(Article article) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = getIndexWriter();
            System.out.println("przed: " + indexWriter.numDocs());
            indexWriter.addDocument(articleToDocument(article));
            indexWriter.commit();
            System.out.println("po: " + indexWriter.numDocs());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            closeWriter(indexWriter);
        }
    }

    private IndexWriter getIndexWriter() throws Exception {
        File indexDir = new File(indexDirectory);
        Directory directory = FSDirectory.open(indexDir);
        try {
            return new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_30),
                                   false, IndexWriter.MaxFieldLength.UNLIMITED);
        } catch (IOException ex) {
            // można tutaj zalogować coś...
        }
        // domyslnie tworzymy nowy indeks
        return new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_30),
                               !indexDir.exists(), IndexWriter.MaxFieldLength.UNLIMITED);
    }

    public synchronized void updateIndexedArticle(Article article) {
        IndexWriter indexWriter = null;
        try {
            Document document = articleToDocument(article);
            indexWriter = getIndexWriter();
            indexWriter.updateDocument(
                    new Term("id", String.valueOf(article.getArticleId())), document);
            indexWriter.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            closeWriter(indexWriter);
        }
    }

    public synchronized void removeIndexedArticle(Long articleId) {
        IndexWriter indexWriter = null;
        try {
            indexWriter = getIndexWriter();
            indexWriter.deleteDocuments(new Term("id", String.valueOf(articleId)));
            indexWriter.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            closeWriter(indexWriter);
        }
    }

    private Document articleToDocument(Article article) {
        Document doc = new Document();
        doc.add(new Field("id", String.valueOf(article.getArticleId()),
                          Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("title", article.getTitle(),
                          Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("lead", article.getLead(),
                          Field.Store.NO, Field.Index.ANALYZED));
        doc.add(new Field("body", article.getBody(),
                          Field.Store.NO, Field.Index.ANALYZED));
        doc.add(new NumericField("createdAt", Field.Store.YES, false)
                .setLongValue(article.getCreateDate().getTime()));
        return doc;
    }

    private Article documentToArticle(Document doc) {
        Long articleId = Long.parseLong(doc.get("id"));
        Date createdAt = new Date(Long.parseLong(doc.get("createdAt")));
        return new Article(articleId, doc.get("title"), doc.get("lead"), createdAt);
    }

    private void closeWriter(IndexWriter writer) {
        try {
            writer.close(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
