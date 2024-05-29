package com.viewnext.solrproject2.writer;

//@Component  LO QUE FALLA ERA EL TIPO DE LA LISTA (Path)
//public class SolrWriter implements ItemWriter<List<Path>> {
//
//	@Override
//	public void write(Chunk<? extends List<Path>> chunk) throws Exception {
//		SolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/Tarifa").build();
//
//		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update");
//		for (List<Path> list : chunk) {
//			for (Path path : list) {
//				System.out.println("WRITER SOUT : " + path.toString());
//
//				request.addFile(path.toFile(), "application/json");
//
//			}
//		}
//		solrClient.request(request);
//		solrClient.commit();
//		solrClient.close();
//	}
//
//}
