package com.agilemaster.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class JunjieForm {
	private static final Logger log = LoggerFactory
			.getLogger(JunjieForm.class);
	private Cluster cluster;
	private Session session;
	public JunjieForm(){
		
	}
	/**
	 * 创建cluster,并且初始化session.
	 * @param node
	 */
	public JunjieForm(String node){
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			log.info("Datatacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect();
	}
	
	
	public Session getSession() {
		return session;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	

}
