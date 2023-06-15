package com.neilw.postplatform.base.report;

import com.neilw.postplatform.base.bo.ReportParam;
import com.neilw.postplatform.base.db.DBHelper;
import com.neilw.postplatform.base.logger.DefaultLogger;
import com.neilw.postplatform.base.logger.Logger;
import com.neilw.postplatform.base.publish.DefaultPublisher;
import com.neilw.postplatform.base.publish.Publisher;
import org.pf4j.ExtensionPoint;

public abstract class ReportService<T extends ReportParam> implements ExtensionPoint {

     protected final Logger logger = new DefaultLogger();
     protected final Publisher publisher = new DefaultPublisher();

     protected final DBHelper dbHelper = new DBHelper(logger);

     public abstract void doReport(T params);


}
