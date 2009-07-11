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
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.commons.PagingParamsEncoder;
import de.berlios.jhelpdesk.web.form.ShowBugsAssignedToMeFilterForm;
import de.berlios.jhelpdesk.web.form.ShowBugsFilterForm;

public class BugsAssignedToMeViewController extends SimpleFormController {

	private static Log log = LogFactory.getLog(BugsAssignedToMeViewController.class);
	private static int PAGE_SIZE = 25;
	private SimpleDateFormat dateFormat;
	private ShowBugsAssignedToMeFilterForm filterForm;

	private BugDAO bugDao;
	private BugCategoryDAO bugCategoryDAO;
	private BugPriorityDAO bugPriorityDAO;
	private UserDAO userDAO;
	private BugStatusDAO statusDAO;

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
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws ServletException {
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", bugPriorityDAO.getAllPriorities());

			List<BugStatus> listS = new ArrayList<BugStatus>(4);
			listS.add(BugStatus.ATTACHED);
			listS.add(BugStatus.REJECTED);
			listS.add(BugStatus.RESOLVED);

			refData.put("statuses", listS);
			refData.put("users", userDAO.getAllUser());
			refData.put("bugs", bugDao.getBugsByStatus(BugStatus.NOTIFIED));

			if (filterForm != null) {
				List<BugStatus> statuses = new ArrayList<BugStatus>();
				statuses.add(BugStatus.REJECTED);
				statuses.add(BugStatus.ATTACHED);
				statuses.add(BugStatus.RESOLVED);
				ShowBugsFilterForm ff = new ShowBugsFilterForm();
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				List<User> n = new ArrayList<User>();
				n.add((User) request.getSession().getAttribute("user"));
				ff.setNotifyiers(filterForm.getNotifyiers());
				ff.setPriorities(filterForm.getPriorities());
				ff.setSaviours(n);
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

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse res, Object command,
			BindException errors) throws Exception {
		ModelAndView mav = new ModelAndView("bugsAssignedToMeList");
		ShowBugsFilterForm ff = new ShowBugsFilterForm();

		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", bugPriorityDAO.getAllPriorities());
			refData.put("users", userDAO.getAllUser());

			List<BugStatus> listS = new ArrayList<BugStatus>(4);
			listS.add(BugStatus.ATTACHED);
			listS.add(BugStatus.REJECTED);
			listS.add(BugStatus.RESOLVED);

			refData.put("statuses", listS);

			if (filterForm != null) {
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				ff.setPriorities(filterForm.getPriorities());
				ff.setNotifyiers(filterForm.getNotifyiers());
				List<BugStatus> st = new ArrayList<BugStatus>();
				st.add(BugStatus.ATTACHED);
				st.add(BugStatus.REJECTED);
				st.add(BugStatus.RESOLVED);
				ff.setStatuses(st);

				List<User> saviours = new ArrayList<User>();
				User u = ((User) (request.getSession()).getAttribute("user"));
				saviours.add(u);
				ff.setSaviours(saviours);
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
	protected void onBind(HttpServletRequest req, Object command) {
		log.info("onBind()->start ");
		filterForm = (ShowBugsAssignedToMeFilterForm) command;

		filterForm.setBugCategoryDAO(bugCategoryDAO);
		filterForm.setBugPriorityDAO(bugPriorityDAO);
		filterForm.setUserDAO(userDAO);
		filterForm.setBugStatusDAO(statusDAO);

		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
		filterForm.setStatusesFromRequest(req);

		// prepareDate( req );
		log.info("onBind()->end ");
	}

	/**
	 * 
	 */
	protected Object formBackingObject(HttpServletRequest req) throws ServletException {
		if (filterForm == null)
			filterForm = new ShowBugsAssignedToMeFilterForm();
		// prepareDate( req );
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
	 * @param statusDAO The statusDAO to set.
	 */
	public void setStatusDAO(BugStatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}

}
