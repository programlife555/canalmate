package com.ppdai.canalmate.api.core;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BaseService {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public BaseDao baseDao;
    public static final DecimalFormat DECIMAL_FORMAT=new DecimalFormat("######0.00");
    public int topN=40;
	
	
}
