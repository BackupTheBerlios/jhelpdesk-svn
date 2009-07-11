package de.berlios.jhelpdesk.web.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.User;

public class BugsPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument( Map model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response )
			throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=ListaZgloszen.pdf");
		
		Paragraph p1 = new Paragraph("Lista zgloszen");
		p1.font().setSize( 24 );
		p1.font().setStyle( Font.BOLD );
		p1.setSpacingAfter( 20f );
		doc.add( p1 );
		
		List<Bug> bugs = ( List<Bug> ) model.get( "bugs" );
		
		
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage( 100f );
		table.setWidths( new float[] {5f,35f,14f,14f,8f,8f,15f} );
		
		table.addCell("L.p.");
		table.addCell("Przyczyna zgloszenia");
		table.addCell("Kategoria");
		table.addCell("Data");
		table.addCell("Status");
		table.addCell("Waznosc");
		table.addCell("Osoba zglaszajaca");
		
//		for( Object ob : model.keySet() ) {
//			PdfPCell cell = 
//				new PdfPCell(new Paragraph(">>" + ob.toString() + "<<"));
//			cell.setColspan(7);
//			table.addCell(cell);
//		}
		int currentRow = 1;
		for( Bug bug : bugs ) {
			table.addCell( String.valueOf( currentRow++ ) );
			table.addCell(bug.getSubject());
			table.addCell(bug.getBugCategory().getCategoryName());
			table.addCell(bug.getCreateDate().toString());
			table.addCell(bug.getBugStatus().getStatusName());
			table.addCell(bug.getBugPriority().getPriorityName());
			table.addCell(bug.getNotifier().getFullName());
		}
		
//		for( int i = 0; bugs.size() < 100; ++i ) {
//			table.addCell("5671");
//			table.addCell("Cos tam... cos tam...");
//			table.addCell("MS Office");
//			table.addCell("2006-05-23 15:45");
//			table.addCell("zgloszony");
//			table.addCell("krytyczny");
//			table.addCell("Jan Kowalski");
//		}
		
//		cell = new PdfPCell(new Paragraph("cell test1"));
//		cell.setBorderColor(new Color(255, 0, 0));
//		table.addCell(cell);
//		cell = new PdfPCell(new Paragraph("cell test2"));
//		cell.setColspan(2);
//		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
//		table.addCell(cell);
		//content.add( table);
		doc.add( table );
		Paragraph footer = new Paragraph( "Pro Spring Chapter 18" );
		footer.setAlignment( Paragraph.ALIGN_BOTTOM );
		doc.add( footer );

	}
	
	@Override
	protected void buildPdfMetadata( Map model, Document doc, HttpServletRequest request ) {
		User user = ( User)request.getSession().getAttribute( "user" );
		doc.addAuthor( user.getFullName() );
		doc.addCreator( "kreator" );
		doc.addSubject( "subject" );
		doc.addTitle( "title" );
	}
	
	@Override
	protected Document newDocument() {
        Document document = new Document(PageSize.A4.rotate());
        
		return document;
	}
}
