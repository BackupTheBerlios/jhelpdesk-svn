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
package de.berlios.jhelpdesk.web.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.User;

@Component("one-pdf")
public class BugPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document doc, PdfWriter writer,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        response.setHeader("Content-Disposition",
            "attachment; filename=ZgloszenieNr" + request.getParameter("bugId") + ".pdf");

        Bug bug = (Bug) model.get("bug");

        Paragraph p1 = new Paragraph("Szczegóły zgloszenia nr " + request.getParameter("bugId"));
        p1.font().setSize(24);
        p1.font().setStyle(Font.BOLD);
        p1.setSpacingAfter(20f);
        doc.add(p1);
        doc.add(new Paragraph(bug.getSubject()));
    }

    @Override
    protected Document newDocument() {
        return new Document(PageSize.A4);
    }

    @Override
    protected void buildPdfMetadata(Map model, Document doc, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        doc.addAuthor(user.getFullName());
        doc.addCreator("kreator");
        doc.addSubject("subject");
        doc.addTitle("title");
    }
}
