package com.jjhop.helpdesk.web.bugkiller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jjhop.helpdesk.dao.BugDAO;
import com.jjhop.helpdesk.model.AdditionalFile;
import com.jjhop.helpdesk.model.Bug;
import com.jjhop.helpdesk.model.BugComment;
import com.jjhop.helpdesk.model.User;
import javax.activation.MimetypesFileTypeMap;

public class BugViewController implements Controller {
	private static Log log = LogFactory.getLog( BugViewController.class );
	private BugDAO  bugDao;
	private String fileRepositoryPath;

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		log.info( "BugViewController.handleRequest()" );
		
		ModelAndView mav = null;
		
		if( req.getParameter( "bugId" ) == null )
			throw new NullArgumentException( "Argument \"bugId\" nie moze byc null'em." );
		
		if( req.getParameter( "format" ) != null && req.getParameter( "format" ).equalsIgnoreCase( "pdf" ) ) {
			mav = new ModelAndView( "one-pdf" );
		} else {
			mav = new ModelAndView( "bugDetails" );
		}
		
		if( req.getMethod().equalsIgnoreCase( "post" ) ) {
			// dorzucamy komentarz do błędu
			BugComment comm = new BugComment();
			comm.setBugId( Long.parseLong( req.getParameter( "bugId" ) ) );
			comm.setCommentDate( new Date( System.currentTimeMillis() ) );
			comm.setNotForPlainUser( false );
			comm.setCommentAuthor( ( User) req.getSession().getAttribute( "user" ) );
			comm.setCommentText( req.getParameter( "addComm" ) );
			bugDao.addComment( comm );
			res.sendRedirect( req.getContextPath() + "/bugDetails.html?bugId=" + req.getParameter( "bugId" ) );
			res.flushBuffer();
		}
			
		Bug bug = bugDao.getBugById( Long.parseLong( req.getParameter( "bugId" ).toString() ) );
//		for( Object evt : bug.getEvents() ) {
//			evt = ( HDBugEvent ) evt;
//			log.info( evt );
//		}
		List<AdditionalFile> addFiles = new ArrayList<AdditionalFile>();
		File repDir = new File( 
			new StringBuffer( fileRepositoryPath )
			.append( File.separatorChar )
			.append( bug.getBugId() )
			.toString()
		);
		
		if( repDir.exists() && repDir.isDirectory() ) {
			for( File f : repDir.listFiles() ) {
				AdditionalFile addFile = new AdditionalFile();
				addFile.setOriginalFileName( f.getName() );
                String mimeType = new MimetypesFileTypeMap().getContentType(f.getName());
	            addFile.setContentType((mimeType != null) ? mimeType : "application/octet-strem");
				addFile.setFileSize( f.length() );
				addFile.setHashedFileName( FileUtils.byteCountToDisplaySize( f.length() ) );
				addFiles.add( addFile );
			}
		}
		
		mav.addObject( "bug", bug );
		mav.addObject( "files", addFiles );
		return mav;
	}
	
	/** @param bugDao The bugDao to set. */
	public void setBugDao(BugDAO bugDao) {
		this.bugDao = bugDao;
	}

	/** @param fileRepositoryPath the fileRepositoryPath to set */
	public void setFileRepositoryPath( String fileRepositoryPath ) {
		this.fileRepositoryPath = fileRepositoryPath;
	}
}
