package com.viewnext.solrproject2.reader;

//@Component  LO QUE FALLA ERA EL TIPO DE LA LISTA (Path)
//public class JsonReader implements ItemReader<List<Path>> {
//
//	@Bean
//	@Override
//	public List<Path> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//		List<Path> jsonFiles = new ArrayList<>();
//		try {
//			jsonFiles = Files.list(Paths.get("C:\\Users\\6002977\\Downloads\\solr-8.11.3\\ejemplo"))
//					.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".json")).toList();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out
//				.println(jsonFiles.toString() + " - - - - - - - - - - - - -PASA  - - - - - - - - - - -  - - - -  - -");
//		return jsonFiles;
//	}
//}
