package net.distributary.tahseen.awis;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import net.distributary.tahseen.awis.enums.Action;
import net.distributary.tahseen.awis.enums.TrafficHistoryResponseGroup;

public class TrafficHistoryRequest extends Request<TrafficHistoryResponseGroup> {
    private static final String DATEFORMAT = "yyyyMMdd";

    /**
     * Any valid URL.
     */
    private String url;
    
    /**
     * Number of days to return. Note that the response document may contain fewer results than this maximum if data is not available. 
     * Default value is '31'. Maximum value is '31'.
     */
    private Integer range;
    
    /**
     * Start date for results. The first start available date is 20070801 (August 1, 2007).
     */
    private String start;
    
    public TrafficHistoryRequest() {
        setAction(Action.TrafficHistory);
        setResponseGroups(Arrays.asList(TrafficHistoryResponseGroup.History));;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	
	public void setStart(Date start) {
        SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT);
        this.start = format.format(start);
	}
}
