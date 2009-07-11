package com.jjhop.helpdesk.dao;

import java.util.Date;
import java.util.List;

import net.sf.hibernate.HibernateException;

import com.jjhop.helpdesk.model.Bug;
import com.jjhop.helpdesk.model.BugCategory;
import com.jjhop.helpdesk.model.BugComment;
import com.jjhop.helpdesk.model.BugPriority;
import com.jjhop.helpdesk.model.BugStatus;
import com.jjhop.helpdesk.model.User;
import com.jjhop.helpdesk.web.form.ShowBugsFilterForm;

/**
 * Definiuje zestaw metod do obslugi trwalosci obiektow Bug
 * 
 * @author jjhop
 */
public interface BugDAO {
	/**
	 * Zwraca Bug o podanym identyfikatorze.
	 * 
	 * @param bugId
	 * @return
	 * @throws HibernateException 
	*/
	public Bug getBugById( Long bugId );
	
	/**
	 * Zwraca osatnio dodany b�ad
	 * 
	 * @return
	*/
	public Bug getLastAddedBug();
	
	/**
	 * Zwraca wszystkie b�edy zgloszone w podanym dniu.
	 *  
	 * @param date
	 * @return
	*/
	public List<Bug> getBugsByDate( Date date );
	
	/**
	 * Zwraca wszystkie b��dy o podanym statusie.
	 * 
	 * @param bugStatus
	 * @return
	*/
	public List<Bug> getBugsByStatus( BugStatus bugStatus );
	
	/**
	 * 
	 * @param bugStatus
	 * @param howMuch
	 * @return
	*/
	public List<Bug> getBugsByStatus( BugStatus bugStatus, int howMuch );
	
	/**
	 * Zwraca wszystkie b��dy o podanej wa�no�ci.
	 * 
	 * @param bugPriority
	 * @return
	*/
	public List<Bug> getBugsByPriority( BugPriority bugPriority );
	
	/**
	 * Zwraca wszystkie b��dy wybranej kategorii.
	 * 
	 * @param bugCategory
	 * @return
	*/
	public List<Bug> getBugsByCategory( BugCategory bugCategory );
	
	/**
	 * 
	 * @param user
	 * @return
	*/
	public List<Bug> getBugsNotifyiedByUser( User user );
	
	/**
	 * 
	 * @param user
	 * @return
	*/
	public List<Bug> getBugsResolvedByUser( User user);
	
	/**
	 * Usuwa wybrany b��d
	 * 
	 * @param bug2Del
	 * @throws Exception 
	*/
	public void removeBug( Bug bug2Del ) throws Exception;
	
	/**
	 * Usuwa b��d o podanym identyfikatorze
	 * 
	 * @param bug2DelIId
	*/
	public void remove( Long bug2DelIId );
	
	/**
	 * Zapisuje podany błąd. Jesli jest to błąd istniejący 
	 * uaktualnie zwi�zane z nim dane.
	 * 
	 * @param bug2Save
	 * @throws HibernateException 
	*/
	public void save( Bug bug2Save ) throws Exception;
	
	/**
	 * Zwraca wszystkie błędy z bazy.
	 * 
	 * @param bug2Save
	 * @throws HibernateException 
	*/
	public List<Bug> getAllBugs();
	
	/**
	 * 
	 * @param filterForm
	*/
	public List<Bug> getBugsWithFilter( ShowBugsFilterForm filterForm, int limit, long offset );

	/**
	 * 
	 * @param filterForm
	 * @return
	*/
	public Integer countBugsWithFilter( ShowBugsFilterForm filterForm );
	
	/**
	 * 
	 * @param comm
	*/
	public void addComment( BugComment comm );
}
