package de.berlios.jhelpdesk.web.bugkiller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;
import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.BugPriorityDAO;
import de.berlios.jhelpdesk.dao.BugStatusDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.commons.PagingParamsEncoder;
import de.berlios.jhelpdesk.web.form.ShowBugsFilterForm;
import de.berlios.jhelpdesk.web.form.ShowBugsNotifiedByMeFilterForm;

public class BugsNotifiedByMeViewController extends SimpleFormController {
	
	private static Log log = LogFactory.getLog(BugsNotifiedByMeViewController.class);
	private static int PAGE_SIZE = 25;

	private SimpleDateFormat dateFormat;
	private ShowBugsNotifiedByMeFilterForm filterForm;

	private BugDAO bugDao;
	private BugStatusDAO bugStatusDAO;
	private BugCategoryDAO bugCategoryDAO;
	private BugPriorityDAO bugPriorityDAO;
	private UserDAO userDAO;

	private Map<String, Object> refData;

	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.info("initBinder()->start");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		log.info(" initBinder()->end");
	}

	/**
	 *
	 */
	protected void onBind(HttpServletRequest req, Object command) {
		log.info("onBind()->start ");
		filterForm = (ShowBugsNotifiedByMeFilterForm) command;

		filterForm.setBugCategoryDAO(bugCategoryDAO);
		filterForm.setBugPriorityDAO(bugPriorityDAO);
		filterForm.setBugStatusDAO(bugStatusDAO);
		filterForm.setUserDAO(userDAO);

		// prepareDate( req );

		filterForm.setStatusesFromRequest(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setSavioursFromRequest(req);

		log.info("onBind()->end ");
	}

	/**
	 * tutaj chyba trzeba zapakowa� do view zestaw wszystkich interesujacych zg�osze� przynajmniej na poczatek bo potem
	 * w onSubmit bedziemy robic to jeszcze raz
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws ServletException {
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", bugPriorityDAO.getAllPriorities());
			refData.put("statuses", bugStatusDAO.getAllStatuses());
			refData.put("users", userDAO.getAllUser());
			refData.put("saviours", userDAO.getSaviours());

			if (filterForm != null) {
				ShowBugsFilterForm ff = new ShowBugsFilterForm();
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				List<User> n = new ArrayList<User>();
				n.add((User) request.getSession().getAttribute("user"));
				ff.setNotifyiers(n);
				ff.setPriorities(filterForm.getPriorities());
				ff.setSaviours(filterForm.getSaviours());
				ff.setStatuses(filterForm.getStatuses());
				PagingParamsEncoder enc = new PagingParamsEncoder("bugsIterator", "p_id", request, PAGE_SIZE);
				refData.put("bugsListSize", bugDao.countBugsWithFilter(ff));
				refData.put("bugs", bugDao.getBugsWithFilter(ff, PAGE_SIZE, enc.getOffset()));
			} else {
				refData.put("bugs", bugDao.getAllBugs());
			}
		}
		return refData;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse res, Object command,
			BindException errors) throws Exception {
		log.info("onSubmit()->start");
		ModelAndView mav = new ModelAndView("bugsNotifiedByMeList");
		ShowBugsFilterForm ff = new ShowBugsFilterForm();
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", bugPriorityDAO.getAllPriorities());
			refData.put("statuses", bugStatusDAO.getAllStatuses());
			refData.put("users", userDAO.getAllUser());
			refData.put("saviours", userDAO.getSaviours());
			if (filterForm != null) {
				// refData.put( "bugs", bugDao.getBugsWithFilter( filterForm ) );
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				List<User> n = new ArrayList<User>();
				n.add((User) request.getSession().getAttribute("user"));
				ff.setNotifyiers(n);
				ff.setPriorities(filterForm.getPriorities());
				// ff.setSaviours( filterForm.getSaviours() );
				ff.setStatuses(filterForm.getStatuses());
				PagingParamsEncoder enc = new PagingParamsEncoder("bugsIterator", "p_id", request, PAGE_SIZE);
				refData.put("bugsListSize", bugDao.countBugsWithFilter(ff));
				refData.put("bugs", bugDao.getBugsWithFilter(ff, PAGE_SIZE, enc.getOffset()));
			} else {
				refData.put("bugs", bugDao.getAllBugs());
			}
		}
		refData.put("filterForm", command);
		mav.addAllObjects(refData);
		return mav;
	}

	/**
	 * 
	 */
	protected Object formBackingObject(HttpServletRequest req) throws ServletException {
		if (filterForm == null) {
			filterForm = new ShowBugsNotifiedByMeFilterForm();
			filterForm.setBugCategoryDAO(bugCategoryDAO);
			filterForm.setBugPriorityDAO(bugPriorityDAO);
			filterForm.setBugStatusDAO(bugStatusDAO);
			filterForm.setUserDAO(userDAO);
		}
		// prepareDate( req );
		filterForm.setStatusesFromRequest(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setSavioursFromRequest(req);
		return filterForm;
	}

	/**
	 * @param bugCategoryDAO The bugCategoryDAO to set.
	 */
	public void setBugCategoryDAO(BugCategoryDAO bugCategoryDAO) {
		this.bugCategoryDAO = bugCategoryDAO;
	}

	/**
	 * @param bugDao The bugDao to set.
	 */
	public void setBugDao(BugDAO bugDao) {
		this.bugDao = bugDao;
	}

	/**
	 * @param bugPriorityDAO The bugPriorityDAO to set.
	 */
	public void setBugPriorityDAO(BugPriorityDAO bugPriorityDAO) {
		this.bugPriorityDAO = bugPriorityDAO;
	}

	/**
	 * @param userDAO The userDAO to set.
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param bugStatusDAO The bugStatusDAO to set.
	 */
	public void setBugStatusDAO(BugStatusDAO bugStatusDAO) {
		this.bugStatusDAO = bugStatusDAO;
	}

}
