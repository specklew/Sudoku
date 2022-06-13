module pl.cp.model {
    requires org.apache.commons.lang3;
    requires org.apache.logging.log4j;
    requires org.slf4j;
    requires java.sql;

    exports pl.cp.model;
    exports pl.cp.model.dao;
    exports pl.cp.model.parts;
    exports pl.cp.model.solver;
    exports pl.cp.model.exceptions;
}