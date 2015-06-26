package com.agilemaster.form.service

class  InitCql {
	public  static String INIT_DEV_KEYSPACE="""
CREATE KEYSPACE IF NOT EXISTS junjie_form WITH replication
= {'class':'SimpleStrategy', 'replication_factor':2};
"""
	public static String INIT_PRODUCT_KEYSPACE="""
CREATE KEYSPACE IF NOT EXISTS junjie_form WITH replication
= {'class':'SimpleStrategy', 'replication_factor':2};
"""
	public static String INIT_CQL="""
CREATE TABLE IF NOT EXISTS junjie_form.HtmlInput 
(id uuid PRIMARY KEY,
inputAttrs map<text,text>,
inputType varchar,
labelAfter text,
labelBefore text,
listShow boolean,
saasId uuid,
selectInfo map<text,text>,
sequence int,
showToUser boolean,
valueType varchar
);

"""

}
