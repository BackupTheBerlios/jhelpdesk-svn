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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.User;

@Component("full-list-pdf")
public class TicketsPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document doc, PdfWriter writer,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        response.setHeader("Content-Disposition",
            "attachment; filename=ListaZgloszen.pdf");

        Paragraph p1 = new Paragraph("Lista zgloszen");
        p1.font().setSize(24);
        p1.font().setStyle(Font.BOLD);
        p1.setSpacingAfter(20f);
        doc.add(p1);

        List<Ticket> tickets = (List<Ticket>) model.get("tickets");

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{5f, 35f, 14f, 14f, 8f, 8f, 15f});

        table.addCell("L.p.");
        table.addCell("Przyczyna zgloszenia");
        table.addCell("Kategoria");
        table.addCell("Data");
        table.addCell("Status");
        table.addCell("Waznosc");
        table.addCell("Osoba zglaszajaca");

        int currentRow = 1;
        for (Ticket ticket : tickets) {
            table.addCell(String.valueOf(currentRow++));
            table.addCell(ticket.getSubject());
            table.addCell(ticket.getTicketCategory().getCategoryName());
            table.addCell(ticket.getCreateDate().toString());
            table.addCell(ticket.getTicketStatus().getStatusName());
            table.addCell(ticket.getTicketPriority().getPriorityName());
            table.addCell(ticket.getNotifier().getFullName());
        }
        doc.add(table);
    }

    @Override
    protected void buildPdfMetadata(Map model, Document doc, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        doc.addAuthor(user.getFullName());
        doc.addCreator("kreator");
        doc.addSubject("subject");
        doc.addTitle("title");
    }

    @Override
    protected Document newDocument() {
        Document document = new Document(PageSize.A4.rotate());
        return document;
    }
}