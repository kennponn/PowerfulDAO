package com.svgteam.page;

import java.util.List;

public interface AbstractPageTool {
	public <W> List<W> split(Integer currPage) throws Exception ;
}
