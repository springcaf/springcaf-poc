package com.springcaf.core.web.widget.alte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.meta.WebAppMeta;

public abstract class BoxDisplayAlteChartWidget extends BoxDisplayAlteBaseWidget {
	
	// ChartJS colors
	public static final String CHARTJS_RED = "rgba(255, 99, 132, 1)";	
	public static final String CHARTJS_ORANGE = "rgb(255, 159, 64, 1)";	
	public static final String CHARTJS_YELLOW = "rgba(255, 205, 86, 1)";	
	public static final String CHARTJS_GREEN = "rgba(75, 192, 192, 1)";	
	public static final String CHARTJS_BLUE = "rgba(54, 162, 235, 1)";	
	public static final String CHARTJS_PURPLE = "rgba(153, 102, 255, 1)";	
	public static final String CHARTJS_GRAY = "rgba(201, 203, 207, 1)";	
	// ALTE colors
	public static final String BLUE = "rgb(0, 116, 217, 1)";		//#0074D9
	public static final String ORANGE = "rgb(255, 133, 27, 1)";		//#FF851B
	public static final String AQUA = "rgb(127, 219, 255, 1)";		//#7FDBFF
	public static final String RED = "rgb(255, 65, 54, 1)";			//#FF4136
	public static final String TEAL = "rgb(57, 204, 204, 1)";		//#39CCCC
	public static final String MAROON = "rgb(133, 20, 75, 1)";		//#85144b
	public static final String OLIVE = "rgb(61, 153, 112, 1)";		//#3D9970
	public static final String FUCHSIA = "rgb(240, 18, 190, 1)";	//#F012BE
	public static final String GREEN = "rgb(46, 204, 64, 1)";		//#2ECC40
	public static final String PURPLE = "rgb(177, 13, 201, 1)";		//#B10DC9
	public static final String LIME = "rgb(1, 255, 112, 1)";		//#01FF70
	public static final String GRAY = "rgb(170, 170, 170, 1)";		//#AAAAAA
	public static final String Navy = "rgb(0, 31, 63, 1)";			//#001f3f
	public static final String SILVER = "rgb(221, 221, 221, 1)";	//#DDDDDD
	public static final String YELLOW = "rgb(255, 220, 0, 1)";		//#FFDC00
	public static final String BLACK = "rgb(17, 17, 17, 1)";		//#111111
	
	// put colors in a list
	public static final String[] COLORS = new String[] {
				CHARTJS_BLUE, CHARTJS_GREEN, CHARTJS_YELLOW, CHARTJS_ORANGE,
				CHARTJS_PURPLE,	CHARTJS_RED, CHARTJS_GRAY,
				BLUE, ORANGE, AQUA,	RED, TEAL, MAROON,
				OLIVE, FUCHSIA,	GREEN, PURPLE, LIME, GRAY,
				Navy, SILVER, YELLOW, BLACK};

	private String boxTitle = null;
	private String rangeDescription = null;
	private String chartAnchorId = null;
	private int chartHeight = 0;

	/**
	 * Constructor
	 * @param webAppMeta
	 * @param boxTitle
	 * @param rangeDescription
	 * @param boxBodyHeight
	 * @param chartAnchorId
	 * @param chartHeight
	 */
	public BoxDisplayAlteChartWidget(WebAppMeta webAppMeta, 
			String boxTitle,
			String rangeDescription,
			int boxBodyHeight,
			String chartAnchorId,
			int chartHeight,
			String boxClass) {
		
		super(webAppMeta, boxClass, true, true, false);
		this.setBodyHeight(boxBodyHeight);
		
		this.boxTitle = boxTitle;
		this.rangeDescription = rangeDescription;
		this.chartAnchorId = chartAnchorId;
		this.chartHeight = chartHeight;
	}

	protected abstract String renderChartData() throws SpringcafException;
	
	@Override
	protected void addBoxHeader(StringBuffer buffer) throws SpringcafException {
		
		this.addBoxTitle(buffer, this.boxTitle, null, this.rangeDescription);

	}

	@Override
	protected void addBoxBody(StringBuffer buffer) throws SpringcafException {
		
		buffer.append("<div class=\"chart\">" + LINE_FEED);
		buffer.append("<canvas id=\"" + this.chartAnchorId + "\"");
		if(this.chartHeight > 0)
		{
			buffer.append(" style=\"height: " + this.chartHeight + "px;\"");
		}
		buffer.append("></canvas>" + LINE_FEED);
		
		// add chart data
		buffer.append("<script>" + LINE_FEED);
		buffer.append(this.renderChartData() + LINE_FEED);
		buffer.append("</script>" + LINE_FEED);
		
		buffer.append("</div>" + LINE_FEED);
	
	}

	protected void addLabels(StringBuffer buffer, Set<String> keys)
	{
		List<String> setToList = new ArrayList<String>();
		for(String key : keys)
		{
			setToList.add(key);
		}
		
		this.addLabels(buffer, setToList);
	}
	
	protected void addLabels(StringBuffer buffer, List<String> keys)
	{
		int counter = 0;
		int setCount = keys.size();
		String labels = "[";

		for(String status : keys)
		{
			if(counter < setCount-1)
			{
				labels += "'" + status + "',";
			}
			else
			{
				labels += "'" + status + "']";
			}
			
			counter++;
		}

		buffer.append("labels: " + labels);

	}

	protected void addDataSet(StringBuffer buffer, String labelType, List<Integer> values, String color)
	{
		this.addDataSet(buffer, labelType, null, null, values, color);
	}
	
	protected void addDataSet(StringBuffer buffer, String labelType, String yAxis, String stack, List<Integer> values, String color)
	{
		int counter = 0;
		int setCount = values.size();
		String countData = "[";

		for(Integer value : values)
		{
			if(counter < setCount-1)
			{
				countData += "'" + value + "',";
			}
			else
			{
				countData += "'" + value + "']";
			}
			
			counter++;
		}

		buffer.append("{" + LINE_FEED);
		buffer.append("label: '" + labelType + "'," + LINE_FEED);
		if(!StringUtils.isNullOrEmpty(yAxis))
		{
			buffer.append("yAxisID: '" + yAxis + "'," + LINE_FEED);
			buffer.append("stack: '" + stack + "'," + LINE_FEED);
		}
		buffer.append("data: " + countData + "," + LINE_FEED);
		buffer.append("backgroundColor: '" + color + "'" + LINE_FEED);
		buffer.append("}");

	}
	
	protected List<Integer> getValuesFromMap(Map<String, Integer> valueMap)
	{
		List<Integer> list = new ArrayList<Integer>();
		for(String key: valueMap.keySet())
		{
			list.add(valueMap.get(key));
		}
		
		return list;
	}

}
