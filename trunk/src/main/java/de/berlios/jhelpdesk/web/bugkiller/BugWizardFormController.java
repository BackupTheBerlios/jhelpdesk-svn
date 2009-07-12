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
package de.berlios.jhelpdesk.web.bugkiller;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;
import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.BugPriorityDAO;
import de.berlios.jhelpdesk.dao.BugStatusDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.AdditionalFile;
import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.BugCategory;
import de.berlios.jhelpdesk.model.BugPriority;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.UserEditor;

public class BugWizardFormController extends AbstractWizardFormController {
	
	private static Log log = LogFactory.getLog( BugWizardFormController.class );

	private BugDAO bugDao;
	private BugCategoryDAO bugCategoryDAO;
	private BugPriorityDAO bugPriorityDAO;
	private BugStatusDAO bugStatusDAO;
	private UserDAO userDAO;
	
	private String fileRepositoryPath;
	
	@Override
	protected void validatePage(Object command, Errors errors, int page, boolean finish) {
		log.debug("validatePage()->" + page);
		Bug bug = (Bug) command;
		setAllowDirtyBack(true);
		if (page > 0) {
			if (bug.getSubject() == null || bug.getSubject().trim().length() < 1)
				errors.rejectValue("subject", "hdbug.subject.empty");
			if (bug.getDescription() == null || bug.getDescription().trim().length() < 1)
				errors.rejectValue("description", "hdbug.description.empty");
		}
	}
	
	@Override
	protected void onBindAndValidate( HttpServletRequest request, Object command, 
				BindException bindErrors, int page ) throws Exception {
		log.debug( "onBindAndValidate" );
		Bug bug = (Bug) command;
		if ((request.getParameter("checkLogin") != null) && (bug.getNotifier() == null)) {
			bindErrors.rejectValue("notifier", "hdbug.notifier.notExists", 
					new Object[] { request.getParameter("notifier") }, "defaultMessage");
		} else if (bug.getNotifier() == null) {
			bindErrors.rejectValue("notifier", "hdbug.notifier.notEmpty");
		}
		
		if ((bug.getUploadedFile() != null) && (bug.getUploadedFile().getOriginalFilename().length() > 0)) {
			MultipartFile file = bug.getUploadedFile();
			AdditionalFile addFile = new AdditionalFile();
			addFile.setContentType(file.getContentType());
			addFile.setFileDate(file.getBytes());
			addFile.setFileSize(file.getSize());
			addFile.setOriginalFileName(file.getOriginalFilename());
			
			bug.getAddFilesList().add(addFile);
			if (log.isDebugEnabled()) {
				log.debug("Filename => " + file.getName() +
						   "OriginalFilename => " + file.getOriginalFilename() +
				           "ContentType => " + file.getContentType() +
				           "Size => " + file.getSize());
				log.debug("Files num => " + bug.getAddFilesList().size());
			}
		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		
		File repository = new File(fileRepositoryPath);

		try {
			Bug bug = (Bug) command;
			// najpierw zapisujemy zgłoszenie w bazie danych
			bug.setCreateDate(new Date(System.currentTimeMillis()));
			bug.setBugCategory(new BugCategory(10, ""));// TODO
			bug.setBugPriority(BugPriority.fromInt(1)); // TODO
			bug.setBugStatus(BugStatus.NOTIFIED);
			bug.setInputer((User) request.getSession().getAttribute("user"));
			
			bugDao.save(bug);
			File thisBugRepository = 
				new File(
					new StringBuffer( repository.getAbsolutePath() )
						.append( File.separatorChar )
						.append( bug.getBugId() )
						.toString()
			);
			if (thisBugRepository.mkdir()) {
				for (AdditionalFile addFile : bug.getAddFilesList()) {
					FileCopyUtils.copy( 
						addFile.getFileData(), 
						new File(
							new StringBuffer( thisBugRepository.getAbsolutePath() )
								.append( File.separatorChar )
								.append( addFile.getOriginalFileName() )
								.toString()
						) 
					);
				}
			}
		} catch (Exception ex) {
			// TODO: jakas sensowna obsluga wyjątku?
			System.out.println(ex.getMessage());
		}
		
		return new ModelAndView(new RedirectView("/showNewBugs.html", true));
	}
	
	@Override
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, 
			Object command,	BindException errors) throws Exception {
		return super.processCancel(request, response, command, errors);
	}

	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.debug("initBinder()->start");
		NumberFormat nf = NumberFormat.getNumberInstance();
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, nf, true));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		UserEditor userEditor = new UserEditor();
		userEditor.setUserDAO(userDAO);
		binder.registerCustomEditor(User.class, userEditor);
	}
	
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws ServletException {
		Map refData = new HashMap();
		refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
		refData.put("priorities", bugPriorityDAO.getAllPriorities());
		refData.put("statuses", bugStatusDAO.getAllStatuses());
		refData.put("users", userDAO.getAllUser());
		refData.put("saviours", userDAO.getSaviours());
		return refData;
	}

//	@Override
//	protected int getTargetPage( HttpServletRequest request, Object command, Errors errors, int page ) {
//		if( errors.hasErrors() )
//			return page;
//		if( request.getParameter( "checkLogin" ) != null )
//			return 0;
//		//return ( request.getParameter( "back" ) != null ) ? ( page - 1 ) : ( page + 1 );
//		return super.getTargetPage( request, command, errors, page );
//	}
	
	/** @param bugDao The bugDao to set. */
	public void setBugDao(BugDAO bugDao) {
		this.bugDao = bugDao;
	}

	/** @param bugPriorityDAO The bugPriorityDAO to set. */
	public void setBugPriorityDAO(BugPriorityDAO bugPriorityDAO) {
		this.bugPriorityDAO = bugPriorityDAO;
	}

	/** @param bugCategoryDAO The bugCategoryDAO to set. */
	public void setBugCategoryDAO(BugCategoryDAO bugCategoryDAO) {
		this.bugCategoryDAO = bugCategoryDAO;
	}

	/** @param bugStatusDAO The bugStatusDAO to set. */
	public void setBugStatusDAO(BugStatusDAO bugStatusDAO) {
		this.bugStatusDAO = bugStatusDAO;
	}

	/** @param userDAO The userDAO to set. */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/** @param fileRepositoryPath the fileRepositoryPath to set */
	public void setFileRepositoryPath(String fileRepositoryPath) {
		this.fileRepositoryPath = fileRepositoryPath;
	}
}