package org.demo;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;





@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class SolrTest {
	
	private CloudSolrClient solrClient;
	private static final String BUCKET_VAL = "val";
	private static final String JSON_FACET = "json.facet";
	private static final String BUCKETS = "buckets";
	public static final String FACETS = "facets";
	public static final String ZKHOST = "zkhost";
	public static final String COLLECTION_NAME = "techproducts.collection";
	public static final String TECHPRODUCTS_SOLR_QUERY = "techproducts.solr.query";
	public static final String TECHPRODUCTS_JSON_FACET = "techproducts.json.facet";
	public static final String MANU_FACET = "manu_facet";
	
	
    @Resource
    private Environment environment;
	
	@Before
	public void createSolrCloudClient() {
		if (solrClient == null) {
			solrClient = new CloudSolrClient.Builder().withZkHost(environment.getProperty(ZKHOST)).build();
			solrClient.setDefaultCollection(environment.getProperty(COLLECTION_NAME));
		}
	}
	 
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
        try {
        	SolrQuery query = new SolrQuery();
        	query.set(CommonParams.Q, environment.getProperty(TECHPRODUCTS_SOLR_QUERY));
        	query.set(JSON_FACET, environment.getProperty(TECHPRODUCTS_JSON_FACET));
        	query.setRows(0);
            QueryResponse response = solrClient.query(query, METHOD.POST);
            NamedList<Object> bucketList = response.getResponse();
            // notice "findRecursive" usage to get the buckets list
            List<SimpleOrderedMap<Object>> buckets = (List<SimpleOrderedMap<Object>>) bucketList.findRecursive(FACETS, MANU_FACET, BUCKETS);
            if (buckets != null) {
                for (SimpleOrderedMap<Object> bucket : buckets) {
                    String manufacturerLevel = (String) bucket.get(BUCKET_VAL);
                    // manufacturer's name and total of all prices for this manufacturer 
                    // Notice "manu_level_price" that was specified in json.facet
                    System.out.println("manufacturerLevel:"+manufacturerLevel+", manu_level_price:"+bucket.get("manu_level_price"));
                }
                // Total prices of all manufacturers
                // notice "total_price" that was specified in json.facet and "findRecursive" usage
                System.out.println("total_price_all_manufacturers:"+bucketList.findRecursive(FACETS, "total_price"));
            }
        } catch (Exception solrExc) {
        		solrExc.printStackTrace();
        }
	}
}
