package de.berlios.jhelpdesk.web.charts;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class BugsStatsByCategoryFullDatasetProducer implements DatasetProducer {

	private static final long serialVersionUID = -5846059145088380422L;
//	private Map<String,Long> chartData;
	private SortedSet<Entry<String,Long>> ss;
    
    @SuppressWarnings("unchecked")
	public BugsStatsByCategoryFullDatasetProducer( Map chartData ) {
//    	this.chartData = chartData;
    	ss = 
    		new TreeSet<Entry<String,Long>>(
    			new Comparator<Entry<String,Long>>() {
					public int compare( Entry<String, Long> o1, Entry<String, Long> o2 ) {
						return o2.getValue().compareTo( o1.getValue() );
					}
    			}
    		);
    	ss.addAll( chartData.entrySet() );
    }

	public String getProducerId() {
		return String.valueOf( serialVersionUID );
	}

	public boolean hasExpired( Map arg0, Date date ) {
		// TODO: należy zaimplemetowac wygasanie 
		return true;
	}

	public Object produceDataset( Map arg0 ) throws DatasetProduceException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int count = 0;
		long otherSum = 0;
		for( Iterator<Entry<String,Long>> it = ss.iterator(); it.hasNext(); count++ ) {
			Entry<String,Long> entry = it.next();
			if( count < 10 )
				dataset.addValue( entry.getValue(), "", entry.getKey() );
			else
				otherSum += entry.getValue();
		}
		if( count > 10 )
			dataset.addValue( otherSum, "", "Pozostałe..." );
		return dataset;
	}
}
