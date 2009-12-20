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
package de.berlios.jhelpdesk.model;

/**
 * Pliki w systmie, które są dołączane do zgłoszenia . Do każdego zgłoszenia można
 * dołączyć dowolną (ograniczoną przestrzenią dyskową) ilość plików. Pliki z założenia
 * mają pomóc osobie zajmującej się zgłoszeniem rozwiązać problem.
 *
 * @author jjhop
 */
public class AdditionalFile {

    /**
     * Identyfikator pliku
     */
    private Long fileId;

    /**
     * Nazwa pliku.
     */
    private String originalFileName;

    /**
     * Skrót z nazwy pliku i identyfikatora. Pozwala przechowywać wiele plików
     * o tej samej nazwie w jednym miejscu nie generując przy tym problemów.
     */
    private String hashedFileName;

    /**
     * Rozmiar pliku w bajtach.
     */
    private Long fileSize;

    /**
     * Typ pliku (np. image/x-png, application/x-word).
     */
    private String contentType;
    
    /**
     * Zawartość pliku.
     */
    private byte[] fileData;

    /**
     * Zwraca identyfikator pliku.
     * 
     * @return identyfikator pliku
     *
     * @see #fileId
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * Ustawia identyfikator pliku. Metoda powinna być wykorzystywana
     * tylko podczas przepisywania danych o pliku z bazy danych.
     * 
     * @param fileId identyfikator pliku
     *
     * @see #fileId
     * @see #getFileId() 
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * Zwraca typ pliku. Jest to łańcuch w postaci podobnej do "image/gif".
     *
     * @return typ pliku
     *
     * @see #contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Ustawia informację o typie pliku. Należy ostrożnie korzystać z tej metody,
     * lub najlepiej po pierwszym ustawieniu (właściwego typu) nie wykorzystywać
     * jej już w ogóle. Od tego czasu powinna być wykorzystywana tylko podczas
     * przepisywania danych z bazy.
     *
     * @param contentType typ plik
     *
     * @see #contentType
     * @see #getContentType() 
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Zwraca zawartość pliku w postaci tablicy surowych bajtów.
     * 
     * @return zawartość pliku
     *
     * @see #fileData
     */
    public byte[] getFileData() {
        return fileData;
    }

    /**
     * Zapisuje w obiekcie zawartość pliku. Plik z założenia jest niezmienny więc
     * po zapisaniu go po raz pierwszy z tej metody powinnie korzystać tylko mechanizm
     * przepisywania informacji z bazy danych.
     * 
     * @param fileDate zawartość pliku
     *
     * @see #fileData
     * @see #getFileData() 
     */
    public void setFileDate(byte[] fileData) {
        this.fileData = fileData;
    }

    /**
     * Zwraca rozmiar pliku w bajtach. Jest to właściwie rozmiar tablicy z danymi<br/>
     * {@code getFileSize().toInt() == getFileData().length}.
     *
     * @return rozmiar pliku w bajtach
     *
     * @see #fileSize
     * @see #fileData
     * @see #getFileData()
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Ustawia rozmiar pliku (wartość w bajtach). Po pierwszym użyciu rozmiar nie powinien być
     * zmieniany i od tego momentu metoda powinna być wykorzystaywan tylko przez mechanizm
     * przepisujący informacje z bazy danych.
     *
     * @param fileSize rozmiar pliku w bajtach
     *
     * @see #fileSize
     * @see #getFileSize()
     * @see #fileData
     * @see #getFileData() 
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Zwraca skrót nazwy pliku i identyfikatora.
     *
     * @return skrót nazwy pliku i identyfikatora
     *
     * @see #hashedFileName
     */
    public String getHashedFileName() {
        return hashedFileName;
    }

    /**
     * Ustawia skrót z nazwy pliku i identyfikatora. Metoda powinna być używana tylko
     * przez mechanizm przepisywania danych z bazy danych.
     *
     * @param hashedFileName skrót nazwy pliku i identyfikatora
     *
     * @see #hashedFileName
     * @see #getHashedFileName() 
     */
    public void setHashedFileName(String hashedFileName) {
        this.hashedFileName = hashedFileName;
    }

    /**
     * Zwraca oryginalną nazwę pliku. Jest to nazwa pliku, pod jaką będzie on pokazywany
     * w szczegółach zgłoszenia.
     * 
     * @return oryginalna nazwa pliku
     *
     * @see #originalFileName
     */
    public String getOriginalFileName() {
        return originalFileName;
    }

    /**
     * Ustawia nazwę pliku. Metoda powinna być używana tylko
     * przez mechanizm przepisywania danych z bazy danych.
     * 
     * @param originalFileName nazwa pliku
     *
     * @see #originalFileName
     * @see #getOriginalFileName() 
     */
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
