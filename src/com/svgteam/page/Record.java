package com.svgteam.page;

import java.util.List;

public interface Record {
	public <W> List<W> getRecord(W obj) throws Exception;
}
